package com.atguigu.java.ai.langchain4j.tools;

import com.atguigu.java.ai.langchain4j.entity.Appointment;
import com.atguigu.java.ai.langchain4j.service.AppointmentService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangyue
 * @date 2026/4/10 14:49
 * @desc TODO
 */

@Component
public class AppointmentTools {
    @Autowired
    AppointmentService appointmentService;

    @Tool(name="预约挂号",value = "根据参数，先执行工具方法queryDepartment查询是否可预约，" +
            "并直接给用户回答是否可预约，并让用户确认所有预约信息，确认无误后再进行预约" +
            "如果用户没有提供具体的医生姓名，请从向量存储中找到一位医生")
    public String bookAppointment(Appointment appointment) {
        System.out.println("=== bookAppointment 被调用 ===");
        if(!queryDepartment(appointment.getDepartment(),appointment.getDate(),appointment.getTime(),appointment.getDoctorName())){
            return "该科室目前没有可以预约的号源";
        }
        Appointment one = appointmentService.getOne(appointment);

        if(one==null){
            appointment.setId(null);//防止大模型幻觉设置了id
            if(appointmentService.save(appointment)){
                return "预约成功，并返回预约详情";
            }
            else{
                return "预约失败";
            }
        }
        return "您在相同的科室和时间已有预约";

    }


    @Tool(name = "取消预约挂号",value =
            "根据参数，查询是否存在预约记录。如果存在则删除预约记录，" +
                    "若删除成功则返回取消预约成功，删除不成功则返回取消预约失败。" +
                    "如果查不到这条记录，则返回失败信息" +
                    "")
    public String cancleAppointment(Appointment appointment) {
        Appointment one = appointmentService.getOne(appointment);
        if(one!=null){
            if(appointmentService.removeById(one.getId())){
                return "取消预约成功";
            }
            else{
                return "取消预约失败";
            }
        }

        return "没有这条预约记录，请核对科室和时间";
    }




    /*
    要让项目真正具备现实预约能力，需要跨越以下鸿沟：
    系统对接：需要与医院官方挂号系统（如预约平台API）或公共卫生平台（如各地健康云）进行安全、稳定的对接，获取真实的号源池并执行预约。
    合规与安全：涉及用户真实的姓名、身份证号等敏感信息，必须符合《网络安全法》、《个人信息保护法》等法规，并通过严格的安全认证。
    业务复杂性：真实的挂号流程包含医保验证、候补排队、退号规则、停诊处理等复杂业务规则，远超过教学项目的模拟范围。
    此处无法真正查看科室信息，因此打印一些日志信息并一律返回true，表示科室是有医生可以预约的
     */
    @Tool(name="查询是否有号源",value="根据科室名称，日期，时间和医生姓名查询是否有号源，并返回给用户")
    public boolean queryDepartment(
            @P(value = "科室名称")String name,
            @P(value = "日期")String date,
            @P(value = "时间，可选上午、下午")String time,
            @P(value = "医生名称",required = false)String doctorName
    ) {
        System.out.println("查询是否有号源");
        System.out.println("科室名称"+name);
        System.out.println("日期"+date);
        System.out.println("时间"+time);
        System.out.println("医生名称"+doctorName);

        //TODO 维护医生的排班信息：
        //如果没有指定医生名字，则根据其他条件查询是否有可以预约的医生（有返回true，否则返回false）；
        //如果指定了医生名字，则判断医生是否有排班（没有排版返回false）
        //如果有排班，则判断医生排班时间段是否已约满（约满返回false，有空闲时间返回true）
        return true;
    }

}
