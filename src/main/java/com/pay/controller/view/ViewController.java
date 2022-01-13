package com.pay.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("login")
    public String toLogin(){
        return "login.html";
    }

    @RequestMapping("main")
    public String toMain(){
        return "main.html";
    }

    @RequestMapping("user_main")
    public String toUserMain(){
        return "user_main.html";
    }

    @RequestMapping("userManager")
    public String toUserManager(){
        return "userManager.html";
    }

    @RequestMapping("department")
    public String toDepartment(){
        return "department.html";
    }

    /**
     * 员工培养计划
     * @return
     */
    @RequestMapping("train")
    public String toTrain(){
        return "train.html";
    }

    /**
     * 人员流动管理
     * @return
     */
    @RequestMapping("departmentUpdate")
    public String toDepartmentUpdate(){
        return "DepartmentUpdate.html";
    }

    /**
     * 员工离职页面
     * @return
     */
    @RequestMapping("resign")
    public String toResign(){
        return "resign.html";
    }

    /**
     * 薪酬管理
     * @return
     */
    @RequestMapping("salary")
    public String toSalary(){
        return "salary.html";
    }

    /**
     * 考勤管理
     * @return
     */
    @RequestMapping("attendance")
    public String toAttendance(){
        return "Attendance.html";
    }

    /**
     * 考勤管理
     * @return
     */
    @RequestMapping("attendanceUser")
    public String toAttendanceUser(){
        return "AttendanceUser.html";
    }

    /**
     * 福利管理
     * @return
     */
    @RequestMapping("welfare")
    public String toWelfare(){
        return "welfare.html";
    }

    /**
     * 请假管理
     * @return
     */
    @RequestMapping("leave")
    public String toLeave(){
        return "leave.html";
    }
}