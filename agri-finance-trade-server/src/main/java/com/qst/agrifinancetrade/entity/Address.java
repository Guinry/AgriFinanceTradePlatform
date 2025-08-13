package com.qst.agrifinancetrade.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;

// 添加MyBatis-Plus相关导入
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.springframework.data.annotation.Id;

// 继承Model类以支持ActiveRecord模式
@TableName("address") // 指定表名
public class Address extends Model<Address> {
    @Id
    @TableId // 标识主键
    private Integer id;
    private String ownName;
    @NotBlank(message = "收货人不能为空")
    @Length(min = 2,max = 10,message = "收货人长度在2-10之间")
    private String consignee;
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3|4|5|7|8][0-9]{9}$",message = "请输入正确的手机号")
    private String phone;
    @NotBlank(message = "地址不能为空")
    @Length(min = 2,max = 40,message = "地址长度在2-40之间")
    private String addressDetail;
    private Boolean isDefault;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwnName() {
        return ownName;
    }

    public void setOwnName(String ownName) {
        this.ownName = ownName;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}