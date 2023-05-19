package com.example.samp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="proce")
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Proce {

    @Id
    private String name;

    private String lotId;

    private boolean status;

    private LocalDateTime endTime;

    private double amountHour;

    private int leadTime;

    private int maxAmount;

}
