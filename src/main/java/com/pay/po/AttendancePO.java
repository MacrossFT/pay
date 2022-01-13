package com.pay.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("attendance")
@Data
public class AttendancePO extends BasePO{

    /**
     * 用户id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 用户名
     */
    @TableField("userName")
    private String userName;

    /**
     * 早上打卡时间
     */
    private String morning;

    /**
     * 下午打卡时间
     */
    private String night;

    /**
     * 打卡日期
     */
    private String day;

    /**
     * 记录
     */
    private String record;
}