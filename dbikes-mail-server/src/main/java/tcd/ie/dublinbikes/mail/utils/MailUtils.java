package tcd.ie.dublinbikes.mail.utils;

import org.springframework.stereotype.Component;

import tcd.ie.dublinbikes.mail.entity.MailMessageObj;
import tcd.ie.dublinbikes.mail.entity.MailRequest;

/**
 * 
 * @author arun
 *
 */
@Component
public class MailUtils {

    public MailMessageObj convert(MailRequest request) {
        MailMessageObj mailMessage = new MailMessageObj();
        mailMessage.setSubject(request.getSubject());
        mailMessage.setSender(request.getSender());
        mailMessage.setToList(request.getToList());
        mailMessage.setBccList(request.getBccList());
        mailMessage.setCcList(request.getCcList());
        mailMessage.setReplyTo(request.getReplyTo());
        mailMessage.setBody(request.getBody());
        return mailMessage;
    }
}
