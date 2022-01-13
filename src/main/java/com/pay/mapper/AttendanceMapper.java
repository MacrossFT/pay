package com.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pay.po.AttendancePO;
import com.pay.po.DepartmentPO;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceMapper extends BaseMapper<AttendancePO> {
}
