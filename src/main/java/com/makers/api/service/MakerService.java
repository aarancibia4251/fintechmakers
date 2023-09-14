package com.makers.api.service;

import com.makers.api.shared.dto.MakerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MakerService {
  Flux<MakerDto> getMakers();
  Mono<MakerDto> getMaker(Long id);
  Mono<MakerDto> saveMaker(MakerDto makerDto);
  Mono<MakerDto> updateMaker(MakerDto makerDto);
  Mono<Void> deleteMaker(Long id);
}
