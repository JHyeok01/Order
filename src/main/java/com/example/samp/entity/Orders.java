package com.example.samp.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "orders")
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderBy;

    private String product;

    private int box;

    private LocalDateTime orderDate;

    private LocalDateTime comDate;

    private String status;
}
