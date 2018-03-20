package tcd.ie.dublinbikes.mail.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcd.ie.dublinbikes.mail.entity.MailMessageObj;

/**
 * 
 * @author arun
 *
 */
@Transactional
@Service
public class ActiveMQPublisherService {

    Logger logger = LoggerFactory.getLogger(ActiveMQPublisherService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void publishMailMessage(MailMessageObj mailMessage) {
        logger.info("Publishing mail message for sending mail to: " + mailMessage.getToList());
        jmsTemplate.convertAndSend(QueueConstants.MAIL_QUEUE_NAME, mailMessage);
    }
}
