package com.mailSender.MailSender.jobs;

import com.mailSender.MailSender.DTO.SetApproveRecipeMessageDto;
import com.mailSender.MailSender.service.interfaces.SendEmail;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailSendJob implements Job {
    @Autowired
    SendEmail emailSender;

    @Override
    public void execute(JobExecutionContext context)  {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        if (jobDataMap.get("subject")!=null&&jobDataMap.get("emailTo")!=null&&jobDataMap.get("text")!=null){
            emailSender.sendMail(new SetApproveRecipeMessageDto(
                    (String) context.getJobDetail().getJobDataMap().get("subject"),
                    (String) context.getJobDetail().getJobDataMap().get("emailTo"),
                    (String) context.getJobDetail().getJobDataMap().get("text")
            )) ;
        } else {
            log.warn(  "Wrong Job Data Map:\nsubject:{}\n,emailTo:{}}\ntext:{}}%n",  context.getJobDetail().getJobDataMap().get("subject"),
                     context.getJobDetail().getJobDataMap().get("emailTo"),
                    context.getJobDetail().getJobDataMap().get("text"));
            try {
                context.getScheduler().deleteJob(context.getJobDetail().getKey());
            } catch (SchedulerException e) {
                log.error("Job with key {} doesn't exist",context.getJobDetail().getKey());
            }
        }

    }
}
