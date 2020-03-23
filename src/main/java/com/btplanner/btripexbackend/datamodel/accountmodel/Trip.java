package com.btplanner.btripexbackend.datamodel.accountmodel;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long id;

    @Column(name = "trip_name")
    private String name;

    @Column(name = "trip_destination")
    private String destination;

    @Column(name = "trip_description")
    private String description;

    @Column(name = "trip_start_date")
    private Date startDate;

    @Column(name = "trip_end_date")
    private Date endDate;

    @Lob
    @Column( length = 100000, name = "trip_thumbnail" )
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "trip")
    @JsonManagedReference
    private List<Event> events;

    public Trip(String name, User user, String destination, Date startDate, Date endDate, String thumbnail, String description){
        this.name = name;
        this.user = user;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public Trip(Long id, String name, String thumbnail){
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public Trip(User user, String name, String thumbnail){
        this.user = user;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public Trip(){}

    public Trip(final Trip s) {
        this.id = s.getId();
        this.user = s.getUser();
        this.name = s.getName();
        this.thumbnail = s.getThumbnail();
        this.endDate = s.getEndDate();
        this.startDate = s.getStartDate();
        this.destination = s.getDestination();
        this.description = s.getDescription();
        //this.user = s.getUser();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
