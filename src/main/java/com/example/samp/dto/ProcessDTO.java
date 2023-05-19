package com.example.samp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProcessDTO {
    private String name;

    private String lotId;

    private boolean status;

    private LocalDateTime endTime;

    private double amountHour;

    private int leadTime;

    private int maxAmount;
}
