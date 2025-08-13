package com.qst.agrifinancetrade.security.config;


import com.qst.agrifinancetrade.security.filter.CustomAuthenticationFilter;
import com.qst.agrifinancetrade.security.filter.JwtAuthenticationTokenFilter;
import com.qst.agrifinancetrade.security.filter.WebSecurityCorsFilter;
import com.qst.agrifinancetrade.security.handler.EntryPointUnauthorizedHandler;
import com.qst.agrifinancetrade.security.handler.MyAuthenticationFailureHandler;
import com.qst.agrifinancetrade.security.handler.MyAuthenticationSuccessHandler;
import com.qst.agrifinancetrade.security.handler.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;

import  jakarta.annotation.Resource;

/**
 * Spring Security 配置类
 *
 * @author QST
 * @EnableGlobalMethodSecurity 开启注解的权限控制，默认是关闭的。
 * prePostEnabled：使用表达式实现方法级别的控制，如：@PreAuthorize("hasRole('ADMIN')")
 * securedEnabled: 开启 @Secured 注解过滤权限，如：@Secured("ROLE_ADMIN")
 * jsr250Enabled: 开启 @RolesAllowed 注解过滤权限，如：@RolesAllowed("ROLE_ADMIN")
 * @create 2019/1/11
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig {
    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    /**
     * 从容器中取出 AuthenticationManagerBuilder，执行方法里面的逻辑之后，放回容器
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //加密
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        /**
         * 在 UsernamePasswordAuthenticationFilter 之前添加 JwtAuthenticationTokenFilter
         */
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)//用登录之后的认证信息，再次访问时可以直接访问了，UsernamePasswordAuthenticationFilter之前添加拦截器
                .addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class);//自定义拦截器解决跨域问题，在security过滤链之前拦截


        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/swagger-ui.html#/**","/swagger-ui.html/**","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",//"/**",
                                "/user/add/**",
                                "/order/goods/**","/order/All/**","/order/needs/**","/order/selectById/**","/order/searchGoodsByKeys/**","/order/searchNeedsByKeys/**","/order/searchAllByKeys/**",
                                "/knowledge/**",
                                "/paySuccessful/**","/alipay/**",
                                "/file/**").permitAll()
                        .anyRequest().authenticated())   // 任何请求,登录后可以访问
                .addFilterAt(customAuthenticationFilter(http),//JSON登陆实现，在 UsernamePasswordAuthenticationFilter，之前添加JSON格式
                        UsernamePasswordAuthenticationFilter.class)
                //.addFilter(corsFilter())
                .formLogin(form -> form.loginProcessingUrl("/user/login")
                        .successHandler(myAuthenticationSuccessHandler)
                        .failureHandler(myAuthenticationFailureHandler))
                .logout(logout -> logout.logoutUrl("/logout").permitAll())
                .headers(headers -> headers.cacheControl(cc -> cc.disable()));

//        // 处理异常情况：认证失败和权限不足
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(entryPointUnauthorizedHandler)
                .accessDeniedHandler(restAccessDeniedHandler));

        return http.build();
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/user/login");
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean(http));
        return filter;
    }

}
