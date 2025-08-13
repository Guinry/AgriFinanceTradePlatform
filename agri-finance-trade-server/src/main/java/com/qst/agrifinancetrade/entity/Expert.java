package com.qst.agrifinancetrade.entity;

import org.hibernate.validator.constraints.NotBlank;

// 添加MyBatis-Plus相关导入
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

// 继承Model类以支持ActiveRecord模式
@TableName("expert") // 指定表名
public class Expert extends Model<Expert> {
    @TableId // 标识主键
    private String userName;
    @NotBlank(message = "真实姓名不能为空")
    private String realName;
    @NotBlank(message = "电话不能为空")
    private String phone;
    @NotBlank(message = "从事专业不能为空")
    private String profession;
    @NotBlank(message = "职位不能为空")
    private String position;
    @NotBlank(message = "所属单位不能为空")
    private String belong;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession == null ? null : profession.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong == null ? null : belong.trim();
    }
}