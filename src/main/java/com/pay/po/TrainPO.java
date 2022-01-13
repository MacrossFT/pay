package com.pay.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("train")
@Data
public class TrainPO extends BasePO {
    /**
     * 用户名称
     */
    @TableField("userName")
    private String userName;

    /**
     * 用户id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 培养计划
     */
    private String plan;

    /**
     * 时间
     */
    private String time;
}