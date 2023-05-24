package com.example.samp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatOrderDto {

    private Long id;

    private Long matId;

    private LocalDateTime orderDate;

    private LocalDateTime comDate;

    private int quantity;

}
