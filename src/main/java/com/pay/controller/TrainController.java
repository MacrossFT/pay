package com.pay.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.pay.common.BizException;
import com.pay.common.DateUtil;
import com.pay.common.PackResult;
import com.pay.mapper.TrainMapper;
import com.pay.mapper.UserMapper;
import com.pay.po.TrainPO;
import com.pay.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @menu 人事培养
 */
@RestController
@RequestMapping("pay/train")
public class TrainController {

    @Autowired
    private TrainMapper trainMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 新增培养计划
     * 只穿userId和plan
     * @param trainPO
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public PackResult<Boolean> add(TrainPO trainPO) {
        UserPO userPO = userMapper.selectById(trainPO.getUserId());
        trainPO.setUserName(userPO.getName());
        check(trainPO);
        trainMapper.insert(trainPO);
        return new PackResult<>();
    }

    /**
     * 修改培养计划
     * 传id和plan
     * @param trainPO
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public PackResult<Boolean> update(TrainPO trainPO) {
//        UserPO userPO = userMapper.selectById(trainPO.getUserId());
//        trainPO.setUserName(userPO.getName());
        check(trainPO);
        trainMapper.updateById(trainPO);
        return new PackResult<>();
    }

    /**
     * 查询培养计划
     * @return
     */
    @PostMapping("select")
    @ResponseBody
    public PackResult<TrainPO> select() {
        List<TrainPO> trainPOS = trainMapper.selectList(new QueryWrapper<>());
        for (TrainPO trainPO : trainPOS) {
            UserPO userPO = userMapper.selectById(trainPO.getUserId());
            trainPO.setUserName(userPO.getName());
        }
        PackResult<TrainPO> result = new PackResult<>();
        result.setDataList(trainPOS);
        return result;
    }

    /**
     * 删除培养计划
     * @param trainPO
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public PackResult<Boolean> delete(TrainPO trainPO) {
        trainMapper.deleteById(trainPO.getId());
        return new PackResult<>();
    }

    private void check(TrainPO trainPO) {
        if (StringUtils.isEmpty(trainPO.getPlan())) {
            throw new BizException("培养计划不允许为空");
        }
        if (StringUtils.isEmpty(trainPO.getTime())) {
            throw new BizException("时间不允许为空");
        }
        DateUtil.isValidDate(trainPO.getTime(), "yyyy-MM-dd");
    }
}