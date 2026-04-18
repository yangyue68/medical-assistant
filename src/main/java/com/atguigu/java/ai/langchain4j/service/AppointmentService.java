package com.atguigu.java.ai.langchain4j.service;

import com.atguigu.java.ai.langchain4j.entity.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author yangyue
 * @date 2026/4/10 9:54
 * @desc 大模型调用的工具类里面，会进一步调用AppointmentService的getOne方法
 */
public interface AppointmentService extends IService<Appointment> {
    //getOne方法的作用是，查看传入的appointment在mysql数据库中是否存在，也就是这条预约信息是否已经存在
    Appointment getOne(Appointment appointment);
}
