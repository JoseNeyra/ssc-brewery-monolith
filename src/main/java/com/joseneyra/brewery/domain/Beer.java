package com.joseneyra.brewery.domain;

import com.joseneyra.brewery.web.model.BeerStyleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Beer extends BaseEntity {

    @Builder
    public Beer(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String beerName,
                BeerStyleEnum beerStyle, String upc, Integer minOnHand,
                Integer quantityToBrew, BigDecimal price, Set<BeerInventory> beerInventory) {
        super(id, version, createdDate, lastModifiedDate);
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.minOnHand = minOnHand;
        this.quantityToBrew = quantityToBrew;
        this.price = price;
        this.beerInventory = beerInventory;
    }

    private String beerName;
    private BeerStyleEnum beerStyle;

    @Column(unique = true)
    private String upc;

    /**
     * Min on hand qty - used to trigger brew
     */
    private Integer minOnHand;
    private Integer quantityToBrew;
    private BigDecimal price;

    @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<BeerInventory> beerInventory = new HashSet<>();
}
