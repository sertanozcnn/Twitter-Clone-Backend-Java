package com.sertann.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Story {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @ManyToOne
    private User user;


    private String image;

    private String captions;

    private LocalDateTime timestamp;



}
