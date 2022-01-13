package com.pay.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("welfare")
public class WelfarePO extends BasePO {

    /**
     * 节日
     */
    private String festival;

    /**
     * 节日福利
     */
    private String welfare;

    /**
     * 备注
     */
    private String remark;
}