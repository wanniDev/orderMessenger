package me.spring.producer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.spring.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
