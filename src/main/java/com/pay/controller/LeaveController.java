package com.pay.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pay.common.BizException;
import com.pay.common.PackResult;
import com.pay.common.UserContextInfo;
import com.pay.mapper.LeaveMapper;
import com.pay.po.LeavePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @menu 请假管理
 */
@RestController
@RequestMapping("pay/leave")
public class LeaveController {

    @Autowired
    private LeaveMapper leaveMapper;

    /**
     * 添加请假
     * 传 reason day
     * @param
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public PackResult<Boolean> add(LeavePO leavePO) {
        LambdaQueryWrapper<LeavePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LeavePO::getUserId, UserContextInfo.getInstance().getUserId());
        queryWrapper.eq(LeavePO::getDay, leavePO.getDay());
        List<LeavePO> leavePOS = leaveMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(leavePOS)) {
            throw new BizException("今天你已请假，无需重复提交");
        }

        leavePO.setUserId(UserContextInfo.getInstance().getUserId());
        leavePO.setUserName(UserContextInfo.getInstance().getUserName());
        leaveMapper.insert(leavePO);
        return new PackResult<>();
    }


    /**
     * 查看自己的请假条
     * @param
     * @return
     */
    @PostMapping("selectByUser")
    @ResponseBody
    public PackResult<LeavePO> selectByUser() {
        LambdaQueryWrapper<LeavePO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LeavePO::getUserId, UserContextInfo.getInstance().getUserId());
        List<LeavePO> leavePOS = leaveMapper.selectList(queryWrapper);
        PackResult<LeavePO> result = new PackResult<>();
        result.setDataList(leavePOS);

        return result;
    }

    /**
     * 管理员查看所有人的请假条
     * @param
     * @return
     */
    @PostMapping("selectByAll")
    @ResponseBody
    public PackResult<LeavePO> selectByAll() {
        LambdaQueryWrapper<LeavePO> queryWrapper = new LambdaQueryWrapper<>();
        List<LeavePO> leavePOS = leaveMapper.selectList(queryWrapper);
        PackResult<LeavePO> result = new PackResult<>();
        result.setDataList(leavePOS);

        return result;
    }
}