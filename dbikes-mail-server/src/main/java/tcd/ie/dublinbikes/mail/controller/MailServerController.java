package tcd.ie.dublinbikes.mail.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tcd.ie.dublinbikes.mail.activemq.ActiveMQPublisherService;
import tcd.ie.dublinbikes.mail.entity.MailMessageObj;
import tcd.ie.dublinbikes.mail.entity.MailRequest;
import tcd.ie.dublinbikes.mail.entity.MailResponse;
import tcd.ie.dublinbikes.mail.entity.ResponseStatusType;
import tcd.ie.dublinbikes.mail.utils.MailUtils;
/**
 * 
 * @author arun
 *
 */
@RestController
public class MailServerController {

    @Autowired
    protected ActiveMQPublisherService mailMQPublisherService;
    @Autowired
    private MailUtils mailRequestToMessageConverter;

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public MailResponse send(@RequestBody MailRequest request) {
        MailResponse response = new MailResponse();

        try {
            MailMessageObj message = mailRequestToMessageConverter.convert(request);
            mailMQPublisherService.publishMailMessage(message);
            response.setStatus(ResponseStatusType.SUCCESS.getValue());

        } catch(Exception e){
            response.setStatus(ResponseStatusType.FAILURE.getValue());
        }
        return response;
    }

}
