package com.joseneyra.brewery.services;

import com.joseneyra.brewery.web.model.BeerOrderDto;
import com.joseneyra.brewery.web.model.BeerOrderPagedList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerOrderService {
    BeerOrderPagedList listOrders(UUID customerId, Pageable pageable);

    BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto);

    BeerOrderDto getOrderById(UUID customerId, UUID orderId);

    void pickupOrder(UUID customerId, UUID orderId);

    BeerOrderPagedList listOrders(Pageable of);

    BeerOrderDto getOrderById(UUID orderId);
}
