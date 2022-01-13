package com.pay.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pay.common.PackResult;
import com.pay.mapper.DepartmentMapper;
import com.pay.po.DepartmentPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @menu 部门管理
 */
@RestController
@RequestMapping("pay/department")
public class DepartmentController {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 添加部门
     * 只传 remark name
     * @param
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public PackResult<Boolean> add(DepartmentPO departmentPO) {
        departmentPO.setNumber(0);
        int insert = departmentMapper.insert(departmentPO);
        return new PackResult<>();
    }


    /**
     * 部门信息修改
     * 根据id修改name及remark
     * @param
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public PackResult<Boolean> update(DepartmentPO departmentPO) {
        DepartmentPO departmentPO1 = departmentMapper.selectById(departmentPO.getId());
        departmentPO1.setName(departmentPO.getName());
        departmentPO1.setRemark(departmentPO.getRemark());
        departmentMapper.updateById(departmentPO1);
        return new PackResult<>();
    }

    /**
     * 部门删除 根据id删除
     * @param
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public PackResult<Boolean> delete(Long id) {
        departmentMapper.deleteById(id);
        return new PackResult<>();
    }

    /**
     * 部门查询 根据id删除
     * @param
     * @return
     */
    @PostMapping("select")
    @ResponseBody
    public PackResult<DepartmentPO> select() {
        List<DepartmentPO> departmentPOS = departmentMapper.selectList(new QueryWrapper<>());
        PackResult<DepartmentPO> result = new PackResult<>();
        result.setDataList(departmentPOS);
        return result;
    }

}