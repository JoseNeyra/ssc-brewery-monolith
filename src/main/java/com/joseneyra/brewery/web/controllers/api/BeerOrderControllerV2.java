package com.joseneyra.brewery.web.controllers.api;

import com.joseneyra.brewery.domain.security.User;
import com.joseneyra.brewery.security.perms.order.v2.OrderReadPermission2;
import com.joseneyra.brewery.services.BeerOrderService;
import com.joseneyra.brewery.web.model.BeerOrderDto;
import com.joseneyra.brewery.web.model.BeerOrderPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * V2
 * This API takes the Customer ID from the Logged-In User
 * This is a best practice for endpoints serving front end applications
 * Needs spring-security-data dependency to implement this feature
 */


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/orders/")
public class BeerOrderControllerV2 {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerOrderService beerOrderService;

    @OrderReadPermission2
    @GetMapping
    public BeerOrderPagedList listOrders(@AuthenticationPrincipal User user,
                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize){

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        if (user.getCustomer() != null) {
            return beerOrderService.listOrders(user.getCustomer().getId(), PageRequest.of(pageNumber, pageSize));
        } else {
            return beerOrderService.listOrders(PageRequest.of(pageNumber, pageSize));
        }
    }

    @OrderReadPermission2
    @GetMapping("{orderId}")
    public BeerOrderDto getOrder(@PathVariable("orderId") UUID orderId){

        // This Service method returns null if the requester is not authorized to read this beer.
        // Check the repo query for more details
        BeerOrderDto beerOrderDto = beerOrderService.getOrderById(orderId);

        if (beerOrderDto == null || beerOrderDto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Not Found");
        }

        log.debug("Found Order: " + beerOrderDto);

        return beerOrderDto;

    }
}
