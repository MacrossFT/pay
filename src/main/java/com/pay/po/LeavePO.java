package com.pay.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("leave")
public class LeavePO extends BasePO {

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
     * 请假理由
     */
    private String reason;

    /**
     * 请假日期
     * yyyy-MM-dd
     */
    private String day;
}