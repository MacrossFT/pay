package com.pay.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.pay.common.BizException;
import com.pay.common.DateUtil;
import com.pay.common.PackResult;
import com.pay.common.UserContextInfo;
import com.pay.dto.UserRespDTO;
import com.pay.mapper.DepartmentMapper;
import com.pay.mapper.UserMapper;
import com.pay.po.DepartmentPO;
import com.pay.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @menu 用户管理
 */
@RestController
@RequestMapping("pay/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 登陆接口 只传name和password
     * 返回值权限取 permission
     * @param userPO
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public PackResult<UserPO> login(HttpSession session, UserPO userPO) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UserPO::getName, userPO.getName());
        queryWrapper.eq(UserPO::getPassword, userPO.getPassword());
        UserPO user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return new PackResult<>(user);
        } else {
//            throw new BizException("用户名或密码错误，请稍后再试");
            return new PackResult<>(false, "用户名或密码错误，请稍后再试");
        }
    }

    /**
     * 获取用户信息接口
     *
     * @return
     */
    @RequestMapping("getUserInfo")
    @ResponseBody
    private PackResult<UserRespDTO> getUserInfo() {
        UserPO user = UserContextInfo.getInstance().getUser();
        UserRespDTO dto = new UserRespDTO();
        dto.setName(user.getName());
        dto.setRoles(Collections.singletonList(user.getPermission()));
        return new PackResult<>(dto);
    }


    /**
     * 退出登陆
     *
     * @param session
     * @return
     */
    @RequestMapping("logout")
    public PackResult<Boolean> logout(HttpSession session) {
        session.removeAttribute("user");
        return new PackResult<>();
    }

    /**
     * 注册接口 只传name password departmentId
     * @param userPO
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public PackResult<Boolean> add(UserPO userPO) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userPO.getName()), UserPO::getName, userPO.getName());
        Integer count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BizException("用户名已存在");
        }

        // 1 管理员 2普通用户
        userPO.setPermission("2");
        userPO.setStatus("在职");
        userPO.setEntryTime(DateUtil.yyyyMMdd(new Date()));
        int insert = userMapper.insert(userPO);
        return new PackResult<>();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public PackResult<Boolean> delete(Long id) {
        userMapper.deleteById(id);
        return new PackResult<>();
    }

    /**
     * 更新密码 只传id，password
     * @param userPO
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public PackResult<Boolean> update(UserPO userPO) {
        UserPO userPO1 = userMapper.selectById(userPO.getId());

        userPO1.setPassword(userPO.getPassword());
        userPO1.setRemark(userPO.getRemark());
        userMapper.updateById(userPO1);

        return new PackResult<>();
    }

    /**
     * 更新部门
     * @param po
     * @return
     */
    @PostMapping("departUpdate")
    @ResponseBody
    @Transactional(rollbackFor = Throwable.class)
    public PackResult<Boolean> departUpdate(UserPO po) {
        UserPO userPO1 = userMapper.selectById(po.getId());
        if (userPO1.getDepartmentId() != null) {
            DepartmentPO departmentPO = departmentMapper.selectById(userPO1.getDepartmentId());
            if (departmentPO != null && departmentPO.getNumber() != null && departmentPO.getNumber() > 0) {
                departmentPO.setNumber(departmentPO.getNumber() - 1);
                departmentMapper.updateById(departmentPO);
            }
        }
        DepartmentPO departmentPO1 = departmentMapper.selectById(po.getDepartmentId());
        departmentPO1.setNumber(departmentPO1.getNumber() + 1);
        departmentMapper.updateById(departmentPO1);

        userPO1.setDepartmentId(po.getDepartmentId());
        userPO1.setDepartmentName(departmentPO1.getName());
        userMapper.updateById(userPO1);

        return new PackResult<>();
    }

    /**
     * 在职员工展示
     * @return
     */
    @PostMapping("select")
    @ResponseBody
    public PackResult<UserPO> select() {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPO::getPermission, 2);
        queryWrapper.eq(UserPO::getStatus, "在职");
        List<UserPO> userPOS = userMapper.selectList(queryWrapper);
        PackResult<UserPO> result = new PackResult<>();
        result.setDataList(userPOS);
        return result;
    }

    /**
     * 离职员工展示
     * @return
     */
    @PostMapping("selectResignUser")
    @ResponseBody
    public PackResult<UserPO> selectResignUser() {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPO::getPermission, 2);
        queryWrapper.in(UserPO::getStatus, "离职中","已离职");
        List<UserPO> userPOS = userMapper.selectList(queryWrapper);
        PackResult<UserPO> result = new PackResult<>();
        result.setDataList(userPOS);
        return result;
    }


    /**
     * 员工办理离职接口
     * @return
     */
    @PostMapping("resign")
    @ResponseBody
    public PackResult<Boolean> resign(UserPO po) {
        UserPO userPO = userMapper.selectById(po.getId());
        userPO.setStatus("离职中");
        userMapper.updateById(userPO);
        return new PackResult<>();
    }

    /**
     * 员工离职接口
     * @return
     */
    @PostMapping("goaway")
    @ResponseBody
    @Transactional(rollbackFor = Throwable.class)
    public PackResult<Boolean> goaway(UserPO po) {
        UserPO userPO = userMapper.selectById(po.getId());
        userPO.setStatus("已离职");
        userMapper.updateById(userPO);

        DepartmentPO departmentPO = departmentMapper.selectById(po.getDepartmentId());
        if (departmentPO != null && departmentPO.getNumber() != null && departmentPO.getNumber() > 0) {
            departmentPO.setNumber(departmentPO.getNumber() - 1);
            departmentMapper.updateById(departmentPO);
        }
        return new PackResult<>();
    }
}