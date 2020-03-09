package com.btplanner.btripexbackend;


import com.btplanner.btripexbackend.datamodel.accountmodel.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.btplanner.btripexbackend.datamodel.accountmodel.User;
import com.btplanner.btripexbackend.datamodel.errormodel.ApiError;
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

    @PostMapping(value = "/user/registration")
    @ResponseBody
    public ResponseEntity<Object> register(@RequestBody User user) {
        logger.debug("Registering user account with information: {} and {}", user.getUsername(), user.getPassword());

        User validatedUser = userRepository.findByUsername(user.getUsername());
        if (validatedUser != null){
            ApiError error = new ApiError(HttpStatus.BAD_REQUEST, USER_ALREADY_EXISTS);
            //return  ResponseEntity.badRequest().body(USER_ALREADY_EXISTS);
            return ResponseEntity.badRequest().body(error);
        } else {
            User createdUser = new User(user.getUsername(), user.getPassword());
            userRepository.save(createdUser);
            //return ResponseEntity.status(HttpStatus.OK).body(USER_SET);
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByUsername(user.getUsername()));
        }
    }

    @GetMapping(value = "/user/login")
    @ResponseBody
    public ResponseEntity<Object> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        logger.debug("Authenticating user account with information: {} and {}", username, password);

        User validatedUser = userRepository.findByUsernameAndPassword(username, password);
        if (validatedUser == null){
            ApiError error = new ApiError(HttpStatus.BAD_REQUEST, BAD_CREDENTIALS);
            //return ResponseEntity.badRequest().body(BAD_CREDENTIALS);
            return ResponseEntity.badRequest().body(error);
        } else {
            //return ResponseEntity.status(HttpStatus.OK).body(validatedUser.getId().toString());
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByUsernameAndPassword(username, password));
        }
    }

    @PutMapping(value = "/user/reset")
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
/*
    @PostMapping(value = "/trip/add")
    @ResponseBody
    public ResponseEntity<Object> addTrip(@RequestBody Trip trip) {
        logger.debug("Adding trip for user with ID: {} and {}", id, name);

        User validatedUser = userRepository.findByUsernameAndPassword(username, password);
        if (validatedUser == null){
            ApiError error = new ApiError(HttpStatus.BAD_REQUEST, BAD_CREDENTIALS);
            //return ResponseEntity.badRequest().body(BAD_CREDENTIALS);
            return ResponseEntity.badRequest().body(error);
        } else {
            //return ResponseEntity.status(HttpStatus.OK).body(validatedUser.getId().toString());
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByUsernameAndPassword(username, password));
        }
    }*/

}
