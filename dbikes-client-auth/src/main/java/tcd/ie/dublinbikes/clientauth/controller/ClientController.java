package tcd.ie.dublinbikes.clientauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Just says hello
 */
@RestController
public class ClientController {

    private static final String template = "success";

    @RequestMapping("/login")
    public ClientObject greet(){
        return new ClientObject(String.format(template));
    }

}
