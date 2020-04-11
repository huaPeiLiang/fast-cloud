package com.fast.util;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.URI;

@Component
public class MailUtil {
    @Value("${mail.exchange.domain}")
    private String exchangeDomain;
    @Value("${mail.exchange.user}")
    private String exchangeUser;
    @Value("${mail.exchange.password}")
    private String exchangePassword;
    @Value("${mail.exchange.server:outlook.live.com}")
    private String exchangeServer;

    // 创建邮件服务 Exchange2010_SP2 Exchange2007_SP1
    private ExchangeService getExchangeService() {
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
//        //用户认证信息
        ExchangeCredentials credentials;
        if (StringUtils.isEmpty(exchangeDomain)) {
            credentials = new WebCredentials(exchangeUser, exchangePassword);
        } else {
            credentials = new WebCredentials(exchangeUser, exchangePassword, exchangeDomain);
        }
        service.setCredentials(credentials);
        try {
            service.setUrl(new URI("https://" + exchangeServer + "/ews/Exchange.asmx"));
        } catch (Exception e){
            e.printStackTrace();
        }
        return service;
    }

    // 通过exchange协议向多人发送邮件
    public void sendMailByExchangeToMany(String subject, String[] to, String[] cc, String bodyText, String[] attachmentPaths)
            throws Exception {
        ExchangeService service = getExchangeService();

        EmailMessage msg = new EmailMessage(service);
        msg.setSubject(subject);
        MessageBody body = MessageBody.getMessageBodyFromText(bodyText);
        body.setBodyType(BodyType.HTML);
        msg.setBody(body);
        for (String toPerson : to) {
            msg.getToRecipients().add(toPerson);
        }
        if (cc != null) {
            for (String ccPerson : cc) {
                msg.getCcRecipients().add(ccPerson);
            }
        }
        if (attachmentPaths != null) {
            for (String attachmentPath : attachmentPaths) {
                msg.getAttachments().addFileAttachment(attachmentPath);
            }
        }
        msg.send();
    }

    // 通过exchange协议向一人发送邮件
    public void sendMailByExchangeToOne(String subject, String to, String bodyText, String[] attachmentPaths)
            throws Exception {
        ExchangeService service = getExchangeService();

        EmailMessage msg = new EmailMessage(service);
        msg.setSubject(subject);
        MessageBody body = MessageBody.getMessageBodyFromText(bodyText);
        body.setBodyType(BodyType.HTML);
        msg.setBody(body);
        msg.getToRecipients().add(to);
        if (attachmentPaths != null) {
            for (String attachmentPath : attachmentPaths) {
                msg.getAttachments().addFileAttachment(attachmentPath);
            }
        }
        msg.send();
    }

}
