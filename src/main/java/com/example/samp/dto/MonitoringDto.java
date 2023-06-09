package com.example.samp.dto;

import lombok.Data;

@Data
public class MonitoringDto {

    private Long id;

    private String lotId;

    private Long orderId;

    private Long productionId;

    private double advance;

}
