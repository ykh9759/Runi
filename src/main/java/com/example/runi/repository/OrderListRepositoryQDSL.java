package com.example.runi.repository;

import java.util.List;

import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.OrderListEntity;

public interface OrderListRepositoryQDSL {

    public List<OrderListEntity> findBySearch(SearchDto dto, Integer memberNo);

}
