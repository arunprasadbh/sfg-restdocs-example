package guru.springframework.demosfgrestdocsexample.web.controller;

/*
 * Created by arunabhamidipati on 25/11/2019
 */

import guru.springframework.demosfgrestdocsexample.web.model.BeerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        return new ResponseEntity<>(BeerDto.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNwqBeer(@RequestBody @Validated BeerDto beerDto){
        return new ResponseEntity( HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeer(@RequestBody @Validated BeerDto beerDto, @PathVariable("beerId") UUID beerId){
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity deleteBeer(@PathVariable("beerId") UUID beerId){
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
