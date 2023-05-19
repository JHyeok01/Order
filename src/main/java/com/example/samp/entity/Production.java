package com.example.samp.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="production")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name="mat_id")
    private Mat mat;

    @ManyToOne
    @JoinColumn(name="pre_id")
    private PreProduct preProduct;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private String lotId;

    private int matInput;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
