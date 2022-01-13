package com.pay.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pay.common.BizException;
import com.pay.common.PackResult;
import com.pay.common.UserContextInfo;
import com.pay.mapper.AttendanceMapper;
import com.pay.po.AttendancePO;
import com.pay.po.SalaryPO;
import com.pay.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @menu 考勤管理
 */
@RestController
@RequestMapping("pay/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceMapper attendanceMapper;

    /**
     * 早上打卡
     */
    @PostMapping("morningAttendance")
    @ResponseBody
    public PackResult<Boolean> morningAttendance() {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        String morningTime = sdf.format(new Date());

        SimpleDateFormat dayFormat = new SimpleDateFormat( "yyyy-MM-dd");
        String day = dayFormat.format(new Date());

        UserPO user = UserContextInfo.getInstance().getUser();

        LambdaQueryWrapper<AttendancePO> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(AttendancePO::getDay, day);
        queryWrapper.eq(AttendancePO::getUserId, user.getId());
        AttendancePO attendancePO = attendanceMapper.selectOne(queryWrapper);

        if (attendancePO != null) {
            if (attendancePO.getMorning() != null) {
                throw new BizException("今天已经进行上班打卡，无需操作");
            }
            attendancePO.setMorning(morningTime);
            attendanceMapper.updateById(attendancePO);
        } else {
            AttendancePO entiy = new AttendancePO();
            entiy.setDay(day);
            entiy.setMorning(morningTime);
            entiy.setUserId(user.getId());
            entiy.setUserName(user.getName());
            attendanceMapper.insert(entiy);
        }
        return new PackResult<>();
    }

    /**
     * 晚上打卡
     */
    @PostMapping("nightAttendance")
    @ResponseBody
    public PackResult<Boolean> nightAttendance() {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        String nightTime = sdf.format(new Date());

        SimpleDateFormat dayFormat = new SimpleDateFormat( "yyyy-MM-dd");
        String day = dayFormat.format(new Date());

        UserPO user = UserContextInfo.getInstance().getUser();

        LambdaQueryWrapper<AttendancePO> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(AttendancePO::getDay, day);
        queryWrapper.eq(AttendancePO::getUserId, user.getId());
        AttendancePO attendancePO = attendanceMapper.selectOne(queryWrapper);

        if (attendancePO != null) {
            attendancePO.setNight(nightTime);
            attendanceMapper.updateById(attendancePO);
        } else {
            AttendancePO entiy = new AttendancePO();
            entiy.setDay(day);
            entiy.setNight(nightTime);
            entiy.setUserId(user.getId());
            entiy.setUserName(user.getName());
            attendanceMapper.insert(entiy);
        }
        return new PackResult<>();
    }


    /**
     * 管理员 查询所有人的考勤
     *
     * @return
     */
    @PostMapping("updateRecord")
    @ResponseBody
    public PackResult<Boolean> updateRecord(AttendancePO po) {
        attendanceMapper.updateById(po);
        return new PackResult<>();
    }
    /**
     * 查询自己的考勤情况
     *
     * @return
     */
    @PostMapping("selectByUser")
    @ResponseBody
    public PackResult<AttendancePO> selectByUser() {
        List<AttendancePO> po = attendanceMapper.selectList(new LambdaQueryWrapper<AttendancePO>().eq(AttendancePO::getUserId, UserContextInfo.getInstance().getUserId()));
        return new PackResult<>(po);
    }

    /**
     * 管理员 查询所有人的考勤
     *
     * @return
     */
    @PostMapping("select")
    @ResponseBody
    public PackResult<AttendancePO> selectByAll() {
        List<AttendancePO> po = attendanceMapper.selectList(new QueryWrapper<>());
        return new PackResult<>(po);
    }

}