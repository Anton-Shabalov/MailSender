package com.mailSender.MailSender.jobs;

import com.mailSender.MailSender.DTO.Message;
import com.mailSender.MailSender.service.interfaces.SendEmail;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailSendJob implements Job {
    @Autowired
    SendEmail emailSender;

    @Override
    public void execute(JobExecutionContext context)  {
        emailSender.sendMail(new Message(
                (String) context.getJobDetail().getJobDataMap().get("subject"),
                (String) context.getJobDetail().getJobDataMap().get("emailTo"),
                (String) context.getJobDetail().getJobDataMap().get("text")
        ));
    }
}
