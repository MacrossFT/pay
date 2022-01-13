package com.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.pay.po.UserPO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserPO> {

}