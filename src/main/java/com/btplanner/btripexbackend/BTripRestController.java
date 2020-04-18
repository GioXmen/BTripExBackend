package com.btplanner.btripexbackend;


import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import com.btplanner.btripexbackend.datamodel.accountmodel.Event;
import com.btplanner.btripexbackend.datamodel.accountmodel.ExpenseReport;
import com.btplanner.btripexbackend.datamodel.accountmodel.Trip;
import com.btplanner.btripexbackend.datamodel.covidmodel.CovidSummary;
import com.btplanner.btripexbackend.datamodel.repository.EventRepository;
import com.btplanner.btripexbackend.util.ImageUtilityImpl;
import com.btplanner.btripexbackend.util.JasperReportsUtil;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.btplanner.btripexbackend.datamodel.accountmodel.User;
import com.btplanner.btripexbackend.datamodel.errormodel.ApiError;
import com.btplanner.btripexbackend.datamodel.repository.TripRepository;
import com.btplanner.btripexbackend.datamodel.repository.UserRepository;
import org.springframework.web.client.RestTemplate;

@RestController
public class BTripRestController {
    private static final String USER_ALREADY_EXISTS = "User already exists, please choose a different username";
    private static final String USER_NOT_FOUND = "User could not be found";
    private static final String BAD_CREDENTIALS = "Wrong Username or Password";
    private static final String USER_UPDATED = "User has been updated";
    private static final String DEFAULT_IMAGE_URL = "https://source.unsplash.com/560x560/?trip";
    private static CovidSummary covidSummary;
    private static Date covidLastUpdated;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final EventRepository eventRepository;
    @Autowired
    private final ImageUtilityImpl imageUtilityImpl;

    @Autowired
    private final JasperReportsUtil jasperReportsUtil;

    public BTripRestController(final UserRepository userRepository, final ImageUtilityImpl imageUtilityImpl,
                               final TripRepository tripRepository, final EventRepository eventRepository, final JasperReportsUtil jasperReportsUtil) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.eventRepository = eventRepository;
        this.imageUtilityImpl = imageUtilityImpl;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    @PostMapping(value = "/user/registration")
    @ResponseBody
    public ResponseEntity<Object> register(@RequestBody User user) {
        logger.debug("Registering user account with information: {} and {}", user.getUsername(), user.getPassword());

        User validatedUser = userRepository.findByUsername(user.getUsername());
        if (validatedUser != null) {
            ApiError error = new ApiError(HttpStatus.BAD_REQUEST, USER_ALREADY_EXISTS);
            return ResponseEntity.badRequest().body(error);
        } else {
            User createdUser = new User(user.getUsername(), user.getPassword());
            userRepository.save(createdUser);
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByUsername(user.getUsername()));
        }
    }

    @GetMapping(value = "/user/login")
    @ResponseBody
    public ResponseEntity<Object> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        logger.debug("Authenticating user account with information: {} and {}", username, password);

        User validatedUser = userRepository.findByUsernameAndPassword(username, password);
        if (validatedUser == null) {
            ApiError error = new ApiError(HttpStatus.BAD_REQUEST, BAD_CREDENTIALS);
            return ResponseEntity.badRequest().body(error);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByUsernameAndPassword(username, password));
        }
    }

    @PutMapping(value = "/user/reset")
    @ResponseBody
    public ResponseEntity<String> reset(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        logger.debug("Resetting user account with information: {} and {}", username, password);

        User validatedUser = userRepository.findByUsername(username);
        if (validatedUser == null) {
            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
        } else {
            userRepository.updateUserPassword(username, password);
            return ResponseEntity.status(HttpStatus.OK).body(USER_UPDATED);
        }
    }

    @PostMapping(value = "/trip/add")
    @ResponseBody
    public ResponseEntity<Object> addTrip(@RequestBody Trip trip) throws IOException {
        logger.debug("Adding trip for user: {} and trip name {}", trip.getUser().getUsername(), trip.getName());

        User validatedUser = userRepository.findByUsername(trip.getUser().getUsername());
        if (validatedUser == null) {
            ApiError error = new ApiError(HttpStatus.BAD_REQUEST, BAD_CREDENTIALS);
            return ResponseEntity.badRequest().body(error);
        } else {
            if (trip.getThumbnail() == null) {
                trip.setThumbnail(imageUtilityImpl.getBase64EncodedImage(DEFAULT_IMAGE_URL));
            }

            Trip createdTrip;
            if (trip.getId() != null) {
                createdTrip = new Trip(trip.getId(), trip.getName(), trip.getDestination(), trip.getStartDate(),
                        trip.getEndDate(), trip.getDescription(), trip.getThumbnail(), validatedUser);
            } else {
                createdTrip = new Trip(trip.getName(), trip.getDestination(), trip.getStartDate(),
                        trip.getEndDate(), trip.getDescription(), trip.getThumbnail(), validatedUser);
            }
            tripRepository.save(createdTrip);
            return ResponseEntity.status(HttpStatus.OK).body(tripRepository.findById(createdTrip.getId()));
        }
    }


    @GetMapping(value = "/trip/get")
    @ResponseBody
    public ResponseEntity<List<Trip>> getTripForUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        List<Trip> createdTrip = tripRepository.findAllByUser(user);

        String image = "iVBORw0KGgoAAAANSUhEUgAAAJYAAACWBAMAAADOL2zRAAAAG1BMVEWSyVKWlpaUqXyTtWuSwlqT\n"
                + "vGOUr3SVooWVnI05h1uwAAAACXBIWXMAAA7EAAAOxAGVKw4bAAABAElEQVRoge3SMW+DMBiE4Ysx\n"
                + "JqMJtHOTITPeOsLQnaodGImEUMZEkZhRUqn92f0MaTubtfeMh/QGHANEREREREREREREtIJJ0xbH\n"
                + "299kp8l8FaGtLdTQ19HjofxZlJ0m1+eBKZcikd9PWtXC5DoDotRO04B9YOvFIXmXLy2jEbiqE6Df\n"
                + "7DTleA5socLqvEFVxtJyrpZFWz/pHM2CVte0lS8g2eDe6prOyqPglhzROL+Xye4tmT4WvRcQ2/m8\n"
                + "1p+/rdguOi8Hc5L/8Qk4vhZzy08DduGt9eVQyP2qoTM1zi0/uf4hvBWf5c77e69Gf798y08L7j0R\n"
                + "ERERERERERH9P99ZpSVRivB/rgAAAABJRU5ErkJggg==";

        List<Trip> output =
                createdTrip.stream()
                        .map(s -> {
                            Trip n = new Trip(s);
                            if (n.getThumbnail() == null) {
                                n.setThumbnail(image);
                            }
                            return n;
                        })
                        .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

    @GetMapping(value = "/event/get")
    @ResponseBody
    public ResponseEntity<List<Event>> getEventForTrip(@RequestParam(value = "tripId") String tripId) {
        Trip trip = tripRepository.findById(Long.parseLong(tripId)).orElse(null);
        List<Event> createdEvents = eventRepository.findAllByTripOrderByStartDate(trip);

        return ResponseEntity.status(HttpStatus.OK).body(createdEvents);
    }

    @PostMapping(value = "/event/add")
    @ResponseBody
    public ResponseEntity<Object> addEvent(@RequestBody Event event) {
        Trip trip = tripRepository.findById(Long.parseLong(event.getTrip().getId().toString())).orElse(null);
        if (trip == null) {
            ApiError error = new ApiError(HttpStatus.BAD_REQUEST, BAD_CREDENTIALS);
            return ResponseEntity.badRequest().body(error);
        } else {
            Event createdEvent;
            if (event.getId() != null) {
                createdEvent = new Event(event.getId(), event.getName(), event.getType(), event.getDescription(),
                        event.getLocation(), event.getStartDate(), event.getEndDate(), event.getEventTime(),
                        event.getExpense(), event.getExpenseReceipt1(), event.getExpenseReceipt2(), event.getExpenseReceipt3(), trip);
            } else {
                createdEvent = new Event(event.getName(), event.getType(), event.getDescription(), event.getLocation(),
                        event.getStartDate(), event.getEndDate(), event.getEventTime(), event.getExpense(),
                        event.getExpenseReceipt1(), event.getExpenseReceipt2(), event.getExpenseReceipt3(), trip);
            }

            eventRepository.save(createdEvent);
            return ResponseEntity.status(HttpStatus.OK).body(eventRepository.findById(createdEvent.getId()));
        }
    }

    @GetMapping(value = "/report/generate")
    @ResponseBody
    public ResponseEntity<ExpenseReport> getEventsReportForTrip(@RequestParam(value = "tripId") String tripId,
                                                                @RequestParam(value = "eventIds", required = false) List<String> ids)
            throws IOException, JRException, URISyntaxException {
        Trip trip = tripRepository.findById(Long.parseLong(tripId)).orElse(null);
        List<Event> createdEvents = eventRepository.findAllByTripOrderByStartDate(trip);

        if (ids != null) {
            for (String eventId : ids) {
                createdEvents.removeIf(s -> s.getId().toString().equals(eventId));
            }
        }
        byte[] pdf = jasperReportsUtil.generateEventReport(createdEvents);
        String encoded = Base64.getEncoder().encodeToString(pdf);

        ExpenseReport newReport = new ExpenseReport(encoded);

        return ResponseEntity.status(HttpStatus.OK).body(newReport);
    }

    @GetMapping(value = "/covid/summary")
    @ResponseBody
    public ResponseEntity<CovidSummary> getCovidSummary() {
        Date currentDate = new Date();

        if (covidSummary == null || covidLastUpdated == null || !DateUtils.isSameDay(currentDate, covidLastUpdated)) {
            RestTemplate restTemplate = new RestTemplate();
            covidSummary = restTemplate.getForObject("https://api.covid19api.com/summary", CovidSummary.class);
            covidLastUpdated = currentDate;
        }

        return ResponseEntity.status(HttpStatus.OK).body(covidSummary);
    }

}
