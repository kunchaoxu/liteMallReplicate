package com.liteMallReplicate.litemallcore.notify;

import org.springframework.scheduling.annotation.Async;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.*;

public class NotifyService {
    private MailSender mailSender;
    private String sendForm;
    private String sendTo;
    private SmsSender smsSender;

    private List<Map<String, String>> smsTemplate = new ArrayList<>();
    private List<Map<String, String>> wxTemplate = new ArrayList<>();

    public boolean isMailEnable() {return mailSender != null;}
    public boolean isSmsEnable() {return smsSender != null;}

    @Async
    public void notifySms(String phoneNumber, String message) {
        if (smsSender == null) return;

        smsSender.send(phoneNumber, message);
    }

    @Async
    public void notifySmsTemplate(String phoneNumber, NotifyType notifyType, String[] params) {
        if (smsSender == null) return;

        String templateIdStr = getTemplateId(notifyType, smsTemplate);
        if (templateIdStr == null) return;

        smsSender.sendWithTemplate(phoneNumber, templateIdStr, params);
    }

    public SmsResult notifySmsTemplateSync(String phoneNumber, NotifyType notifyType, String[] params) {
        if (smsSender == null) return null;

        return smsSender.sendWithTemplate(phoneNumber, getTemplateId(notifyType, smsTemplate), params);
    }

    @Async
    public void notifyMail(String subject, String content) {
        if (mailSender == null) return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendFrom);
        message.sendTo(sendTo);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    private String getTemplateId(NotifyType notifyType, List<Map<String, String>> values) {
        for (Map<String, String> item : values) {
            String notifyTypeStr = notifyType.getType();

            if (item.get("name").equals(notifyTypeStr)) {
                return item.get("templateId");
            }
        }
        return null;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setSendForm(String sendForm) {
        this.sendForm = sendForm;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public void setSmsSender(SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void setSmsTemplate(List<Map<String, String>> smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public void setWxTemplate(List<Map<String, String>> wxTemplate) {
        this.wxTemplate = wxTemplate;
    }

}
