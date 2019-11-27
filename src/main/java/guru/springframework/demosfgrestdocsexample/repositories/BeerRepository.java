package guru.springframework.demosfgrestdocsexample.repositories;

/*
 * Created by arunabhamidipati on 26/11/2019
 */

import guru.springframework.demosfgrestdocsexample.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

}
