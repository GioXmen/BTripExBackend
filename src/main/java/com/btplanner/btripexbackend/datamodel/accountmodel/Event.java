package com.btplanner.btripexbackend.datamodel.accountmodel;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "event_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", length = 16)
    private EventType type;

    @Column(name = "event_description")
    private String description;

    @Column(name = "event_location")
    private String location;

    @Column(name = "event_start_date")
    private Date startDate;

    @Column(name = "event_end_date")
    private Date endDate;

    @Column(name = "event_time")
    private Date eventTime;

    @Column(name = "event_expense")
    private String expense;

    @Lob
    @Column( length = 100000, name = "event_expense_receipt" )
    private String expenseReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonBackReference
    private Trip trip;


    public Event() {
    }

    public Event(String name, EventType type, String description, String location, Date startDate,
                 Date endDate, Date eventTime, String expense, String expenseReceipt, Trip trip) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventTime = eventTime;
        this.expense = expense;
        this.expenseReceipt = expenseReceipt;
        this.trip = trip;
    }

    public Event(Long id, String name, EventType type, String description, String location, Date startDate,
                 Date endDate, Date eventTime, String expense, String expenseReceipt, Trip trip) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventTime = eventTime;
        this.expense = expense;
        this.expenseReceipt = expenseReceipt;
        this.trip = trip;
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

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getExpenseReceipt() {
        return expenseReceipt;
    }

    public void setExpenseReceipt(String expenseReceipt) {
        this.expenseReceipt = expenseReceipt;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
