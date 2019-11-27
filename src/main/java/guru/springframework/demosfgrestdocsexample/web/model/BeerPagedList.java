package guru.springframework.demosfgrestdocsexample.web.model;

/*
 * Created by arunabhamidipati on 25/11/2019
 */

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BeerPagedList extends PageImpl<BeerDto> {
 public BeerPagedList(List<BeerDto> content, Pageable pageable, long total) {
  super(content, pageable, total);
 }

 public BeerPagedList(List<BeerDto> content) {
  super(content);
 }
}
