package com.example.samp.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="lot")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String weigh;

    private String wash;

    private String extract;

    private String blend;

    private String packing;

    private String testing;

    private String cooling;

    private String boxing;
}
