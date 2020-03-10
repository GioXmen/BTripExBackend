package com.btplanner.btripexbackend.datamodel.accountmodel;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long id;

    @Column(name = "trip_name")
    private String name;

    @Lob
    @Column( length = 100000, name = "trip_thumbnail" )
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonBackReference
    private User user;

    public Trip(String name, User user){
        this.name = name;
        this.user = user;
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
        this.name = s.getName();
        this.thumbnail = s.getThumbnail();
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
}
