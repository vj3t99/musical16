package com.musical16.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.OrderDetailEntity;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long>{

}
