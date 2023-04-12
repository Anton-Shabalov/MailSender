package com.mailSender.MailSender.scheduling;

import com.mailSender.MailSender.DTO.Message;
import com.mailSender.MailSender.jobs.EmailSendJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Component
public class EmailJobFactory {
    private int countMessage=0;
    @Autowired
    private ApplicationContext applicationContext;
    private JobDetail createEmailSendJobDetail(Message message) {

        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(EmailSendJob.class);
        factory.setDescription("Email Sender");
        factory.setDurability(true);
        factory.setApplicationContext(applicationContext);
        factory.setName("Email Sender"+Instant.now().toString());
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("emailTo",message.getEmailTo());
        jobDataMap.put("subject",message.getSubject());
        jobDataMap.put("text",message.getText());
        factory.setJobDataMap(jobDataMap);
        factory.afterPropertiesSet();


        return factory.getObject();
    }

    private Trigger createEmailSendJobTrigger(JobDetail emailSendJobDetail)  {
        SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
        factory.setJobDetail(emailSendJobDetail);
        // TODO change to interval, corresponding to actual business-process requirements
        factory.setStartTime(Date.from(Instant.now().plus(10, ChronoUnit. SECONDS)));
        factory.setRepeatCount(0);
        factory.setRepeatInterval(100);
        factory.setName("Email Sender Trigger"+ Instant.now());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Autowired
    Scheduler scheduler;

    public void ScheduleEmailSending(Message message) throws SchedulerException {

        JobDetail emailSendJobDetail = createEmailSendJobDetail(message);
        Trigger emailSendTrigger = createEmailSendJobTrigger(emailSendJobDetail);
        scheduler.scheduleJob(emailSendJobDetail,emailSendTrigger);
    }
}
