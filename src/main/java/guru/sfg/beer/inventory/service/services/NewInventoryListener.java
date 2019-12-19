package guru.sfg.beer.inventory.service.services;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.brewery.model.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class NewInventoryListener {

    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent event){
        log.debug("Got inventory: " + event.toString());

        beerInventoryRepository.save(BeerInventory.builder()
                                             .beerId(event.getBeerDTO().getId())
                                             .upc(event.getBeerDTO().getUpc())
                                             .quantityOnHand(event.getBeerDTO().getQuantityOnHand())
                                             .build());
    }

}
