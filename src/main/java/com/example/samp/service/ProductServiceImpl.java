package com.example.samp.service;

import com.example.samp.dto.ProductDTO;
import com.example.samp.entity.Product;
import com.example.samp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void InsertData(){
        Product product = new Product();
        product.setLotId("양배추-02");
        product.setProduct("양배추즙");
        product.setNum(2000);

        productRepository.save(product);
    }

    @Override
    public ProductDTO findData() {

        return (ProductDTO) productRepository.findAll();
    }
}
