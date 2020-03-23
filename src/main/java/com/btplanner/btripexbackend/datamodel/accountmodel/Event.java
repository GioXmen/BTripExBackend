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

    @Column(name = "event_type")
    private String type;

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


}
