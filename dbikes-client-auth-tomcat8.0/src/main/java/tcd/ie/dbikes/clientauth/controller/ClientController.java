package tcd.ie.dbikes.clientauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author arun
 *
 */
@RestController
public class ClientController {

    private static final String template = "Success, %s";

    @RequestMapping("/")
    public ClientObject login() {
        return new ClientObject("success");
    }
    
    @RequestMapping("/login")
    public ClientObject loginById(
            @RequestParam(value = "id", required = false, defaultValue = "3451264") String id) {
        return new ClientObject(String.format(template, id));
    }

}
