package com.pay.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.pay.common.BizException;
import com.pay.common.DateUtil;
import com.pay.common.PackResult;
import com.pay.common.UserContextInfo;
import com.pay.mapper.SalaryMapper;
import com.pay.mapper.UserMapper;
import com.pay.po.SalaryPO;
import com.pay.po.TrainPO;
import com.pay.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @menu 薪酬管理
 */
@RestController
@RequestMapping("pay/salary")
public class SalaryController {

    @Autowired
    private SalaryMapper salaryMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询自己的工资
     *
     * @return
     */
    @PostMapping("selectByUser")
    @ResponseBody
    public PackResult<SalaryPO> selectByUser() {
        List<SalaryPO> salaryPO = salaryMapper.selectList(new LambdaQueryWrapper<SalaryPO>().eq(SalaryPO::getUserId, UserContextInfo.getInstance().getUserId()));
        return new PackResult<>(salaryPO);
    }

    /**
     * 管理员 查询所有人的工资
     *
     * @return
     */
    @PostMapping("select")
    @ResponseBody
    public PackResult<SalaryPO> selectByAll() {
        List<SalaryPO> salaryPO = salaryMapper.selectList(new QueryWrapper<>());
        return new PackResult<>(salaryPO);
    }

    /**
     * 管理员 发放工资
     * 传userId, month，payable，realWages
     *
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public PackResult<Boolean> add(SalaryPO salaryPO) {
        check(salaryPO);
        LambdaQueryWrapper<SalaryPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalaryPO::getUserId, salaryPO.getUserId());
        queryWrapper.eq(SalaryPO::getMonth, salaryPO.getMonth());
        List<SalaryPO> salaryPOList = salaryMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(salaryPOList)) {
            throw new BizException("本月工资已发放，无需再次发放");
        }

        UserPO userPO = userMapper.selectById(salaryPO.getUserId());
        salaryPO.setUserName(userPO.getName());
        salaryMapper.insert(salaryPO);
        return new PackResult<>();
    }

    /**
     * 管理员 查询所有人的工资
     *
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public PackResult<Boolean> delete(SalaryPO po) {
        salaryMapper.deleteById(po.getId());
        return new PackResult<>();
    }

    private void check(SalaryPO po) {
        if (StringUtils.isEmpty(po.getPayable())) {
            throw new BizException("应发工资不允许为空");
        }
        if (StringUtils.isEmpty(po.getRealWages())) {
            throw new BizException("实际工资不允许为空");
        }
        DateUtil.isValidDate(po.getMonth(), "yyyy-MM-dd");
    }

}