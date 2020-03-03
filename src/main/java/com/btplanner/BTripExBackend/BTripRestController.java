package com.btplanner.BTripExBackend;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.btplanner.BTripExBackend.security.AccountModel.User;

@RestController
public class BTripRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    @ResponseBody
    public User register(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        LOGGER.debug("Registering user account with information: {} and {}", username, password);

        return new User(username, password);
    }

}
