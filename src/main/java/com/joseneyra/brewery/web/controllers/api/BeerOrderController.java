package com.joseneyra.brewery.web.controllers.api;


import com.joseneyra.brewery.security.perms.order.OrderCreatePermission;
import com.joseneyra.brewery.security.perms.order.OrderPickUpPermission;
import com.joseneyra.brewery.security.perms.order.OrderReadPermission;
import com.joseneyra.brewery.services.BeerOrderService;
import com.joseneyra.brewery.web.model.BeerOrderDto;
import com.joseneyra.brewery.web.model.BeerOrderPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.joseneyra.brewery.web.controllers.api.BeerRestController.DEFAULT_PAGE_NUMBER;
import static com.joseneyra.brewery.web.controllers.api.BeerRestController.DEFAULT_PAGE_SIZE;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers/{customerId}/")
@RestController
public class BeerOrderController {

    private final BeerOrderService beerOrderService;

    @OrderReadPermission
    @GetMapping("orders")
    public BeerOrderPagedList listOrders(@PathVariable("customerId")UUID customerId,
                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return beerOrderService.listOrders(customerId, PageRequest.of(pageNumber, pageSize));
    }


    @OrderCreatePermission
    @PostMapping("orders")
    @ResponseStatus(HttpStatus.CREATED)
    public BeerOrderDto placeOrder (@PathVariable("customerId") UUID customerId, @RequestBody BeerOrderDto beerOrderDto) {
        return beerOrderService.placeOrder(customerId, beerOrderDto);
    }


    @OrderReadPermission
    @GetMapping("orders/{orderId}")
    public BeerOrderDto getOrder (@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId) {
        return beerOrderService.getOrderById(customerId, orderId);
    }


    @OrderPickUpPermission
    @PutMapping("/orders/{orderId}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pickupOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId) {
        beerOrderService.pickupOrder(customerId, orderId);
    }
}
