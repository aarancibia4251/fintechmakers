package com.makers.api.shared.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MakerDto {
  private long id;
  private String firstname;
  private String lastname;
  private String email;
}
