package com.btplanner.btripexbackend;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.btplanner.btripexbackend.datamodel.accountmodel.User;
import com.btplanner.btripexbackend.datamodel.repository.UserRepository;

@RestController
public class BTripRestController {
    private static final String USER_ALREADY_EXISTS = "User already exists, please choose a different username";
    private static final String USER_NOT_FOUND = "User could not be found";
    private static final String BAD_CREDENTIALS = "Wrong Username or Password";
    private static final String USER_SET = "User has been set";
    private static final String USER_UPDATED = "User has been updated";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;

    public BTripRestController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/user/registration")
    @ResponseBody
    public ResponseEntity<String> register(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        logger.debug("Registering user account with information: {} and {}", username, password);

        User validatedUser = userRepository.findByUsername(username);
        if (validatedUser != null){
            return ResponseEntity.badRequest().body(USER_ALREADY_EXISTS);
        } else {
            User createdUser = new User(username, password);
            userRepository.save(createdUser);
            return ResponseEntity.status(HttpStatus.OK).body(USER_SET);
        }
    }

    @GetMapping(value = "/user/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        logger.debug("Authenticating user account with information: {} and {}", username, password);

        User validatedUser = userRepository.findByUsernameAndPassword(username, password);
        if (validatedUser == null){
            return ResponseEntity.badRequest().body(BAD_CREDENTIALS);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(validatedUser.getId().toString());
        }
    }

    @GetMapping(value = "/user/reset")
    @ResponseBody
    public ResponseEntity<String> reset(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        logger.debug("Resetting user account with information: {} and {}", username, password);

        User validatedUser = userRepository.findByUsername(username);
        if (validatedUser == null){
            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
        } else {
            userRepository.updateUserPassword(username, password);
            return ResponseEntity.status(HttpStatus.OK).body(USER_UPDATED);
        }
    }

}
