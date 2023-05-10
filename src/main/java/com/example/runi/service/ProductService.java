package com.example.runi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.runi.domain.dto.ProductDto;
import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.ProductEntity;
import com.example.runi.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        
    }

    @Transactional
    public void productSave(ProductDto productDto, Integer memberNo) {

        productDto.setMemberNo(memberNo);
        
        ProductEntity productEntity = productDto.toEntity();

        System.out.println("proEntity: " + productEntity);
        productRepository.save(productEntity);

    }

    @Transactional
    public void productUpdate(ProductDto productDto) {

        
        Optional<ProductEntity> productEntity = productRepository.findByNo(productDto.getNo());

        if(productEntity.isPresent()) {
            productEntity.get().updateProduct(productDto.getProductName(), productDto.getPrice());
            System.out.println("업데이트 : " + productEntity);
            productRepository.save(productEntity.get());
        }
    }

    @Transactional
    public String productDelete(Integer no) {

        Optional<ProductEntity> product = productRepository.findByNo(no);
        if(product.isPresent()) {
            product.get().updateSaveStatus("N"); 
            productRepository.save(product.get());
            return "Y";
        }else {
            return "N";
        }
    }


    //상품내역검색
    public List<ProductEntity> getProductList(SearchDto request, Integer memberNo) {

        System.out.println("service : " + request);

        List<ProductEntity> list = new ArrayList<>();

        if(request.isDtoEntireVariableNull()) {
            list = productRepository.findByMemberNoAndSaveStatusOrderByNoDesc(memberNo, "Y");
        } else {
            list = productRepository.findBySearch(request, memberNo);
        }

        return list;
    }

    public Map<String, String> checkDuplication(ProductDto productDto, String action) {

        Map<String, String> map = new HashMap<>();

        boolean pnDuplicate = productRepository.existsByProductNameAndSaveStatus(productDto.getProductName().trim(), "Y");
        if (pnDuplicate) {

            Optional<ProductEntity> entity = productRepository.findByNo(productDto.getNo());

            if(entity.isPresent()) {
                if(!productDto.getProductName().trim().equals(entity.get().getProductName())) {
                     map.put("valid_productName","이미 존재하는 상품명입니다.");
                } 
            }
            else {
                map.put("valid_productName","이미 존재하는 상품명입니다.");
            }
        }
        
        return map;
    }
}
