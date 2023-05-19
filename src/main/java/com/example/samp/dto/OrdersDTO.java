package com.example.samp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrdersDTO {

    private Long id;

    private String orderBy;

    private String product;

    private int box;

    private LocalDateTime orderDate;

    private LocalDateTime comDate;

    private String status;

}
