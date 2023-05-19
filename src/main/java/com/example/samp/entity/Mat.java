package com.example.samp.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "mat")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matName;

    private int matNum;

}
