package tcd.ie.dublinbikes.mail.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import tcd.ie.dublinbikes.mail.entity.MailMessageObj;
import tcd.ie.dublinbikes.mail.service.ISMTPMailService;

/**
 * 
 * @author arun
 *
 */
@Component
public class ActiveMQConsumerService {

    Logger logger = LoggerFactory.getLogger(ActiveMQConsumerService.class);

    @Autowired
    private ISMTPMailService mailSenderService;

    @JmsListener(destination = QueueConstants.MAIL_QUEUE_NAME, containerFactory = "jmsContainerFactory")
    public void onSendMailRequest(MailMessageObj mailMessage) {
        logger.info("Consuming mail message for sending mail to: " + mailMessage.getToList());
        mailSenderService.sendMail(mailMessage);
    }

}
