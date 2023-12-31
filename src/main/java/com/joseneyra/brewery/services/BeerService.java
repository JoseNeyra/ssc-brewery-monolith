package com.joseneyra.brewery.services;

import com.joseneyra.brewery.web.model.BeerDto;
import com.joseneyra.brewery.web.model.BeerPagedList;
import com.joseneyra.brewery.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    BeerDto findBeerById(UUID beerId,  Boolean showInventoryOnHand);

    BeerDto saveBeer(BeerDto beerDto);

    void updateBeer(UUID beerId, BeerDto beerDto);

    void deleteById(UUID beerId);

    BeerDto findBeerByUpc(String upc);
}
