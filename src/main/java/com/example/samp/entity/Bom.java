package com.example.samp.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BOM")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Bom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    private int cab;

    private int gal;

    private int pom;

    private int plu;

    private int col;

    private int wat;

    private int pau;

    private int stick;

    private int box;

}
