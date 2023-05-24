package com.example.samp.dto;

import lombok.Data;

@Data
public class LotDto {
    private Long id;

    private Long orderId;

    private String weigh;

    private String wash;

    private String extract;

    private String blend;

    private String packing;

    private String testing;

    private String cooling;

    private String boxing;
}
