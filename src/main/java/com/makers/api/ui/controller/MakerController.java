package com.makers.api.ui.controller;

import com.makers.api.service.MakerService;
import com.makers.api.shared.dto.MakerDto;
import com.makers.api.ui.model.request.CreateMakerRequest;
import com.makers.api.ui.model.request.UpdateMakerRequest;
import com.makers.api.ui.model.response.MakerResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/makers")
@RestController
public class MakerController {

  @Autowired
  private MakerService makerService;
  @Autowired
  private ModelMapper mapper;

  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
  public Mono<CollectionModel<MakerResponse>> getMakers() {
    return makerService.getMakers()
      .map(makerDto -> {
        MakerResponse response = mapper.map(makerDto, MakerResponse.class);
        Link link = linkTo(MakerController.class).slash(response.getId()).withSelfRel();
        Link linkStudent = linkTo(MakerController.class).slash(response.getId()).withRel("student");
        response.add(link);
        response.add(linkStudent);
        return response;
      })
      .collectList()
      .map(CollectionModel::of);
  }

  @GetMapping("/{id}")
  public Mono<MakerResponse> getMaker(@PathVariable("id") Long code) {
    return makerService.getMaker(code)
      .switchIfEmpty(Mono.error(new RuntimeException("No existe el maker con codigo: " + code)))
      .map(makerDto -> mapper.map(makerDto, MakerResponse.class));
  }

  @PostMapping
  public Mono<MakerResponse> saveMaker(@Valid @RequestBody CreateMakerRequest createMakerRequest) {
    return makerService.saveMaker(mapper.map(createMakerRequest, MakerDto.class))
      .map(makerDto -> mapper.map(makerDto, MakerResponse.class));
  }

  @PutMapping
  public Mono<MakerResponse> updateMaker(@Valid @RequestBody UpdateMakerRequest updateMakerRequest) {
    return makerService.updateMaker(mapper.map(updateMakerRequest, MakerDto.class))
      .map(makerDto -> mapper.map(makerDto, MakerResponse.class));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity> deleteMaker(@PathVariable("id") Long id) {
    return makerService.deleteMaker(id)
      .map(ResponseEntity::ok);
  }
}
