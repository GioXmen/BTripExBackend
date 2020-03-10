package com.btplanner.btripexbackend;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.btplanner.btripexbackend.datamodel.accountmodel.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.btplanner.btripexbackend.datamodel.accountmodel.User;
import com.btplanner.btripexbackend.datamodel.errormodel.ApiError;
import com.btplanner.btripexbackend.datamodel.repository.TripRepository;
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
    private final TripRepository tripRepository;

    public BTripRestController(final UserRepository userRepository,
            final TripRepository tripRepository) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
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

    @PostMapping(value = "/trip/add")
    @ResponseBody
    public ResponseEntity<Object> addTrip(@RequestBody Trip trip) {
        logger.debug("Adding trip for user: {} and trip name {}", trip.getUser().getUsername(), trip.getName());

        User validatedUser = userRepository.findByUsername(trip.getUser().getUsername());
        if (validatedUser == null){
            ApiError error = new ApiError(HttpStatus.BAD_REQUEST, BAD_CREDENTIALS);
            return ResponseEntity.badRequest().body(error);
        } else {
            Trip createdTrip = new Trip(trip.getName(), trip.getUser());
            tripRepository.save(createdTrip);
            return ResponseEntity.status(HttpStatus.OK).body(tripRepository.findById(createdTrip.getId()));
        }
    }


    @GetMapping(value = "/trip/get")
    @ResponseBody
    public ResponseEntity<List<Trip>> getTripForUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        List<Trip> createdTrip = tripRepository.findAllByUser(user);
/*        byte[] byteArray = Base64.getMimeDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAJYAAACWBAMAAADOL2zRAAAAG1BMVEWSyVKWlpaUqXyTtWuSwlqT\n"
                                                                + "vGOUr3SVooWVnI05h1uwAAAACXBIWXMAAA7EAAAOxAGVKw4bAAABAElEQVRoge3SMW+DMBiE4Ysx\n"
                                                                + "JqMJtHOTITPeOsLQnaodGImEUMZEkZhRUqn92f0MaTubtfeMh/QGHANEREREREREREREtIJJ0xbH\n"
                                                                + "299kp8l8FaGtLdTQ19HjofxZlJ0m1+eBKZcikd9PWtXC5DoDotRO04B9YOvFIXmXLy2jEbiqE6Df\n"
                                                                + "7DTleA5socLqvEFVxtJyrpZFWz/pHM2CVte0lS8g2eDe6prOyqPglhzROL+Xye4tmT4WvRcQ2/m8\n"
                                                                + "1p+/rdguOi8Hc5L/8Qk4vhZzy08DduGt9eVQyP2qoTM1zi0/uf4hvBWf5c77e69Gf798y08L7j0R\n"
                                                                + "ERERERERERH9P99ZpSVRivB/rgAAAABJRU5ErkJggg==");*/

        String image ="iVBORw0KGgoAAAANSUhEUgAAAJYAAACWBAMAAADOL2zRAAAAG1BMVEWSyVKWlpaUqXyTtWuSwlqT\n"
                + "vGOUr3SVooWVnI05h1uwAAAACXBIWXMAAA7EAAAOxAGVKw4bAAABAElEQVRoge3SMW+DMBiE4Ysx\n"
                + "JqMJtHOTITPeOsLQnaodGImEUMZEkZhRUqn92f0MaTubtfeMh/QGHANEREREREREREREtIJJ0xbH\n"
                + "299kp8l8FaGtLdTQ19HjofxZlJ0m1+eBKZcikd9PWtXC5DoDotRO04B9YOvFIXmXLy2jEbiqE6Df\n"
                + "7DTleA5socLqvEFVxtJyrpZFWz/pHM2CVte0lS8g2eDe6prOyqPglhzROL+Xye4tmT4WvRcQ2/m8\n"
                + "1p+/rdguOi8Hc5L/8Qk4vhZzy08DduGt9eVQyP2qoTM1zi0/uf4hvBWf5c77e69Gf798y08L7j0R\n"
                + "ERERERERERH9P99ZpSVRivB/rgAAAABJRU5ErkJggg==";
        List<Trip> output =
                createdTrip.stream()
                        .map(s-> {
                            Trip n = new Trip(s); // create new instance
                            if(n.getThumbnail() == null){
                                n.setThumbnail(image); // mutate its state
                            }
                            return n; // return mutated instance
                        })
                        .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

}
