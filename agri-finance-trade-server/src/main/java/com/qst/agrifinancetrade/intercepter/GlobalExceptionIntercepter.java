package com.qst.agrifinancetrade.intercepter;

import com.qst.agrifinancetrade.common.Result;
import com.qst.agrifinancetrade.common.StatusCode;
import com.qst.agrifinancetrade.exception.FileFormatException;
import com.qst.agrifinancetrade.exception.InvalidateTokenException;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;
import java.io.IOException;

@Component
@ControllerAdvice
@ResponseBody
public class GlobalExceptionIntercepter {
    //主键重复
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> sqlIntegrityConstraintViolationException(){
        return new Result<String>(false, StatusCode.ERROR,"账号已被注册，请重新输入","账号已被注册");
    }
    //文件格式不正确，
    @ExceptionHandler(FileFormatException.class)
    public Result<String> FileFormatException(){
        return new Result<String>(false, StatusCode.ERROR,"格式不正确","请传入 jpg、 png、 jpeg、 gif、webp格式的图片");
    }
//    请求方式不正确
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<String> httpRequestMethodNotSupportedException(){
        System.out.println("请求方式异常，httpRequestMethodNotSupportedException");
        return new Result<String>(false, StatusCode.ERROR,"请求方式不正确","请求方式不正确，HttpRequestMethodNotSupportedException");
    }
    //    类转换异常
    @ExceptionHandler(ClassCastException.class)
    public Result<String> classCastException(){
        System.out.println("类转换异常，这个项目多半发生在请求头的token问题");
        return new Result<String>(false, StatusCode.ERROR,"请您先登录","请您先登录");
    }
    //账号登陆失效，异常拦截不到，还是会报错
    @ExceptionHandler(InvalidateTokenException.class)
    public Result<String> InvalidateTokenException(){
        return new Result<String>(false, StatusCode.ERROR,"账号登陆失效，请重新登录","账号登陆失效，请重新登录");
    }
    
    // 文件操作异常处理，专门处理路径不存在等IOException
    @ExceptionHandler(IOException.class)
    public Result<String> ioException(IOException ex){
        // 添加日志输出，方便定位问题
        System.err.println("文件操作异常捕获: " + ex.getClass().getName() + " - " + ex.getMessage());
        ex.printStackTrace();
        // 检查是否是文件路径不存在的问题
        if(ex instanceof java.io.FileNotFoundException) {
            return new Result<String>(false, StatusCode.ERROR,"文件路径不存在或无法访问: " + ex.getMessage(),"请检查文件上传配置路径是否正确");
        }
        return new Result<String>(false, StatusCode.ERROR,"文件操作失败: " + ex.getMessage(),"文件操作失败，请检查文件路径或权限");
    }
    
//    全部异常，
    @ExceptionHandler(Exception.class)
    public Result<String> DAException(Exception ex){
        // 添加日志输出，方便定位问题
        System.err.println("全局异常捕获: " + ex.getClass().getName() + " - " + ex.getMessage());
        ex.printStackTrace();
        return new Result<String>(false, StatusCode.ERROR,"有异常待解决，具体异常待看控制台: " + ex.getClass().getSimpleName() + " - " + ex.getMessage(),"有异常待解决，具体异常待看控制台");
    }
}