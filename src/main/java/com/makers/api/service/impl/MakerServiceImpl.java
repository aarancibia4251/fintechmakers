package com.makers.api.service.impl;

import com.makers.api.entity.MakerEntity;
import com.makers.api.repository.MakerRepository;
import com.makers.api.service.MakerService;
import com.makers.api.shared.dto.MakerDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MakerServiceImpl implements MakerService {

  @Autowired
  private MakerRepository makerRepository;
  @Autowired
  private ModelMapper mapper;

  @Override
  public Flux<MakerDto> getMakers() {
    List<MakerEntity> makers = makerRepository.findAll();
    return Flux.fromIterable(makers)
      .map(maker -> mapper.map(maker, MakerDto.class));
  }

  @Override
  public Mono<MakerDto> getMaker(Long id)  {
    Optional<MakerEntity> makerEntity = makerRepository.findById(id);
    if (makerEntity.isEmpty()) {
      return Mono.empty();
    }
    return Mono.just(makerEntity)
      .switchIfEmpty(Mono.empty())
      .flatMap(maker -> Mono.just(mapper.map(maker, MakerDto.class)));
  }

  @Override
  public Mono<MakerDto> saveMaker(MakerDto makerDto) {
    MakerEntity makerEntity = mapper.map(makerDto, MakerEntity.class);
    return saveOrInsert(makerEntity);
  }

  @Override
  public Mono<MakerDto> updateMaker(MakerDto makerDto) {
    return getMaker(makerDto.getId())
      .flatMap(makerDb -> {
        MakerEntity makerEntity = mapper.map(makerDto, MakerEntity.class);
        return saveOrInsert(makerEntity);
      });
  }

  @Override
  public Mono<Void> deleteMaker(Long id) {
    return getMaker(id)
      .switchIfEmpty(Mono.error(new RuntimeException("No existe el maker con id: " + id)))
      .map(maker -> {
        makerRepository.deleteById(id);
        return maker;
      })
      .then();
  }

  public Mono<MakerDto> saveOrInsert(MakerEntity entity) {
    MakerEntity savedMaker = makerRepository.save(entity);
    return Mono.just(mapper.map(savedMaker, MakerDto.class));
  }
}
