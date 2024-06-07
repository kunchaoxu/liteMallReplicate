package com.liteMallReplicate.litemallcore.notify;

public interface SmsSender {

    SmsResult send(String phone, String content);

    SmsResult sendWithTemplate(String phone, String templateId, String[] params);

}
