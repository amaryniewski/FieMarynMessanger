package com.websystique.springmvc.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by DanielFiecko on 21/12/16.
 */


@Entity
@Table(name="USER")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=1, max=250)
    @Column(name = "message", nullable = false)
    private int message;
//
//    @Column(name = "date", nullable = false)
//    private CalendarData date;



}
