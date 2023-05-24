package com.example.samp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShipmentDto {
    private Long id;

    private Long orderId;

    private Long productId;

    private LocalDateTime comDate;
}
