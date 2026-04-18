package com.atguigu.java.ai.langchain4j.service.impl;
import com.atguigu.java.ai.langchain4j.entity.Appointment;
import com.atguigu.java.ai.langchain4j.mapper.AppointmentMapper;
import com.atguigu.java.ai.langchain4j.service.AppointmentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * @author yangyue
 * @date 2026/4/10 9:58
 * @desc 只需要实现getOne方法，其他的不用管
 */

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper,Appointment> implements AppointmentService {
    /**
     * 查询mysql数据库中是否存在与参数appointment完全一样的预约信息
     * @param appointment，传入的封装好的订单信息
     * @return
     */
    @Override
    public Appointment getOne(Appointment appointment) {
        // 1. 创建查询条件构造器
        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();

        // 2. 添加多个查询条件（AND 连接）.相当于：
        //SELECT * FROM appointment WHERE username = ？AND id_card = ? AND department = ? AND date = ? AND time = ? LIMIT 1;
        queryWrapper.eq(Appointment::getUsername,appointment.getUsername());
        queryWrapper.eq(Appointment::getIdCard,appointment.getIdCard());
        queryWrapper.eq(Appointment::getDepartment,appointment.getDepartment());
        queryWrapper.eq(Appointment::getDate,appointment.getDate());
        queryWrapper.eq(Appointment::getTime,appointment.getTime());

        // 查询并返回结果

        Appointment appointmentDB =  baseMapper.selectOne(queryWrapper);
        return appointmentDB;
    }


}
