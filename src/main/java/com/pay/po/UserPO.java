package com.pay.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class UserPO extends BasePO {

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色权限
     * 1：代表管理员
     * 2：代表普通用户
     */
    private String permission;

    /**
     * 注册时间
     */
    @TableField("entryTime")
    private String entryTime;

    @TableField("departmentId")
    private Long departmentId;

    /**
     * 部门名称
     */
    @TableField("departmentName")
    private String departmentName;

    /**
     * 是否在职
     */
    private String status;

    /**
     * 备注
     */
    private String remark;
}