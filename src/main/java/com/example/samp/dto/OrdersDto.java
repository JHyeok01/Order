package com.example.samp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrdersDto {

    private Long id;

    private String orderFrom;

    private String product;

    private int box;

    private LocalDateTime orderDate;

    private LocalDateTime comDate;

    private String status;

}
