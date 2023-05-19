package com.example.samp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatOrderDTO {

    private Long id;

    private Long matId;

    private LocalDateTime comDate;

    private int leadTime;

}
