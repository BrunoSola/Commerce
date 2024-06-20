package com.brunosola.commerce.services;

import com.brunosola.commerce.dto.OrderDTO;
import com.brunosola.commerce.entities.Order;
import com.brunosola.commerce.repositories.OrderRepository;
import com.brunosola.commerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado."));
        return new OrderDTO(order);
    }
}
