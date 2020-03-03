package com.btplanner.BTripExBackend;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.btplanner.BTripExBackend.security.AccountModel.User;
import com.btplanner.BTripExBackend.security.Repository.UserRepository;

@RestController
public class BTripRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    public BTripRestController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    @ResponseBody
    public User register(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        LOGGER.debug("Registering user account with information: {} and {}", username, password);

        User temp = userRepository.findByUsername(username);

        Long id = temp.getId();

        userRepository.setUserInfoById(username, password, id);

        return userRepository.findByUsername(username);
    }

}
