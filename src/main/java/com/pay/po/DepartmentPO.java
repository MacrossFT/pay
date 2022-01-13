package com.pay.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("department")
public class DepartmentPO extends BasePO{

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门描述
     */
    private String remark;

    /**
     * 部门人数
     */
    private Integer number;
}
