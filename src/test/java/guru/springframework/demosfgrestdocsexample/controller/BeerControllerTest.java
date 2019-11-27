package guru.springframework.demosfgrestdocsexample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.demosfgrestdocsexample.repositories.BeerRepository;
import guru.springframework.demosfgrestdocsexample.web.controller.BeerController;
import guru.springframework.demosfgrestdocsexample.web.model.BeerDto;
import guru.springframework.demosfgrestdocsexample.web.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
 * Created by arunabhamidipati on 25/11/2019
 */

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "guru.springframework.sfgrestdocsexample.web.mappers")
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerRepository beerRepository;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/{beerId}" , UUID.randomUUID().toString())
                .param("isCold", "yes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                            parameterWithName("beerId").description("UUID of the beer to get.")
                        ),
                        requestParameters(
                                parameterWithName("isCold").description("Is Beer cold query param")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of the Beer"),
                                fieldWithPath("version").description("Version number"),
                                fieldWithPath("beerName").description("Name of the beer"),
                                fieldWithPath("upc").description("Unique identifier"),
                                fieldWithPath("beerStyle").description("Style of the beer. e.g. Lager"),
                                fieldWithPath("quantityOnHand").description("Inventory level"),
                                fieldWithPath("price").description("Cost of the beer"),
                                fieldWithPath("createdDate").description("Date beer created"),
                                fieldWithPath("lastModifiedDate").description("Date beer is updated")
                        )
                      )
                );
    }

    @Test
    void saveNwqBeer() throws Exception {
        BeerDto dto = createNewBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(dto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
        mockMvc.perform(post("/api/v1/beer/" )
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("beerName").description("Name of the beer"),
                                fields.withPath("upc").description("Unique identifier"),
                                fields.withPath("beerStyle").description("Style of the beer. e.g. Lager"),
                                fields.withPath("price").description("Cost of the beer"),
                                fields.withPath("quantityOnHand").ignored()
                        )
                        ))
        ;
    }

    @Test
    void updateBeer() throws Exception {
        BeerDto dto = createNewBeerDto();
        UUID id = UUID.randomUUID(); //dto.getId();
        String beerDtoJson = objectMapper.writeValueAsString(dto);
        System.out.println(beerDtoJson);

        mockMvc.perform(put("/api/v1/beer/" + id.toString())
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBeer() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/beer/" + id.toString()).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    private BeerDto createNewBeerDto() {
        BeerDto dto = BeerDto
                .builder()
               // .id(UUID.randomUUID())
                .beerName("Carona")
                .upc(123456789L)
                .beerStyle(BeerStyle.LAGER)
                .price(new BigDecimal("12.3"))
                .build();
        return dto;
    }

    private static class ConstrainedFields{
        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
           return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                        .descriptionsForProperty(path), ". ")));
        }
    }
}