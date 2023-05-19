package com.example.samp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="monitoring")
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Monitoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lotId;

    private Long orderId;

    private Long productionId;

    private double advance;

}
