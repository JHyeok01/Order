package com.example.samp.dto;

import com.example.samp.entity.Mat;
import com.example.samp.entity.Orders;
import com.example.samp.entity.PreProduct;
import com.example.samp.entity.Product;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductionDto {

    private Long id;

    private Orders orders;

    private Mat mat;

    private PreProduct preProduct;

    private Product product;

    private String lotId;

    private int matInput;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
