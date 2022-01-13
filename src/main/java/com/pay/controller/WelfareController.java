package com.pay.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pay.common.PackResult;
import com.pay.mapper.WelfareMapper;
import com.pay.po.WelfarePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @menu 福利管理
 */
@RestController
@RequestMapping("pay/welfare")
public class WelfareController {

    @Autowired
    private WelfareMapper welfareMapper;

    /**
     * 新增福利
     * 只穿festival和welfare
     *
     * @param welfarePO
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public PackResult<Boolean> add(WelfarePO welfarePO) {
        welfareMapper.insert(welfarePO);
        return new PackResult<>();
    }

    /**
     * 删除福利
     * 只传id
     *
     * @param welfarePO
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public PackResult<Boolean> delete(WelfarePO welfarePO) {
        welfareMapper.deleteById(welfarePO.getId());
        return new PackResult<>();
    }

    /**
     * 更新福利
     * 传id，festival，welfare
     *
     * @param welfarePO
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public PackResult<Boolean> update(WelfarePO welfarePO) {
        welfareMapper.updateById(welfarePO);
        return new PackResult<>();
    }

    /**
     * 查找福利
     *
     * @return
     */
    @PostMapping("select")
    @ResponseBody
    public PackResult<WelfarePO> select() {
        List<WelfarePO> welfarePOS = welfareMapper.selectList(new LambdaQueryWrapper<>());
        PackResult<WelfarePO> result = new PackResult<>();
        result.setDataList(welfarePOS);
        return result;
    }

}