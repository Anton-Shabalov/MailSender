package com.mailSender.MailSender.scheduling;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class EmailSender implements Job {

    public void sendEmail() {
        System.out.println("Banana69");
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        sendEmail();
    }
}
