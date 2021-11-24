package com.musical16.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.musical16.Entity.OrdersEntity;
import com.musical16.Entity.UserEntity;

public interface OrdersRepository extends JpaRepository<OrdersEntity, Long>{

	List<OrdersEntity> findAllByOrderByIdDesc(Pageable pageable);
	List<OrdersEntity> findByUser(UserEntity user);
}
