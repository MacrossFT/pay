package com.pay.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("salary")
public class SalaryPO extends BasePO {

    /**
     * 用户ID
     */
    @TableField("userId")
    private Long userId;

    /**
     * 用户名称
     */
    @TableField("userName")
    private String userName;

    /**
     * 应发工资
     */
    private String payable;

    /**
     * 实际工资
     */
    @TableField("realWages")
    private String realWages;

    /**
     * 月份
     */
    private String month;
}