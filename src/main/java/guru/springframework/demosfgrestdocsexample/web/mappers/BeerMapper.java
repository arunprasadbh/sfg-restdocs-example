package guru.springframework.demosfgrestdocsexample.web.mappers;

/*
 * Created by arunabhamidipati on 26/11/2019
 */

import guru.springframework.demosfgrestdocsexample.domain.Beer;
import guru.springframework.demosfgrestdocsexample.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto BeerToBeerDto(Beer beer);

    Beer BeerDtoToBeer(BeerDto dto) ;
}
