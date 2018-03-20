package tcd.ie.dublinbikes.mail.service;


import tcd.ie.dublinbikes.mail.entity.MailMessageObj;

/**
 * 
 * @author arun
 *
 */
public interface ISMTPMailService {
    void sendMail(MailMessageObj mailMessage);
}
