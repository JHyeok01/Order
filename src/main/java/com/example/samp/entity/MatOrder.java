package com.example.samp.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="matOrder")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long matId;

    private LocalDateTime orderDate;

    private LocalDateTime comDate;

    private int leadTime;
}