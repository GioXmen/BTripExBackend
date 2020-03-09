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

    @Column(name = "trip_thumbnail")
    private byte[] thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonBackReference
    private User user;

    public Trip(String name, User user){
        this.name = name;
        this.user = user;
    }

    public Trip(String name, User user, byte[] thumbnail){
        this.name = name;
        this.user = user;
        this.thumbnail = thumbnail;
    }

    public Trip(){}

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

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
}
