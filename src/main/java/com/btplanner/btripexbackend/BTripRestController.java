package com.btplanner.btripexbackend;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.btplanner.btripexbackend.security.accountmodel.User;
import com.btplanner.btripexbackend.security.repository.UserRepository;

@RestController
public class BTripRestController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    public BTripRestController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/user/registration")
    @ResponseBody
    public User register(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        logger.debug("Registering user account with information: {} and {}", username, password);

        User temp = userRepository.findByUsername(username);

        Long id = temp.getId();

        userRepository.setUserInfoById(username, password, id);

        return userRepository.findByUsername(username);
    }

}
