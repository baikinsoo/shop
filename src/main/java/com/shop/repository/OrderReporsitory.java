package com.shop.repository;

import com.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReporsitory extends JpaRepository<Order, Long> {

}
