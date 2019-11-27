package guru.springframework.demosfgrestdocsexample.bootstrap;

/*
 * Created by arunabhamidipati on 26/11/2019
 */

import guru.springframework.demosfgrestdocsexample.domain.Beer;
import guru.springframework.demosfgrestdocsexample.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BeerLoader implements CommandLineRunner {
    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.loadBeerObjects();
    }

    private void loadBeerObjects() {
        if (beerRepository.count() == 0) {
            beerRepository.save(
                    Beer.builder()
                            .id(UUID.randomUUID())
                            .beerName("Carona")
                            .beerStyle("LAGER")
                            .minOnHand(10)
                            .quantityOnHand(50)
                            .upc(123456789L)
                            .price(new BigDecimal("10.26"))
                            .build());

            beerRepository.save(Beer.builder()
                    .id(UUID.randomUUID())
                    .beerName("Heinkein")
                    .beerStyle("ALE")
                    .minOnHand(15)
                    .quantityOnHand(50)
                    .upc(123123123L)
                    .price(new BigDecimal("3.56"))
                    .build());
        }
    }
}
