package com.example.samp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="preproduct")
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lotId;

    private String product;

    private int num;
}
