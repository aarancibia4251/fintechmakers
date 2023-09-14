package com.makers.api.ui.model.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class MakerResponse extends RepresentationModel {
  private long id;
  private String firstname;
  private String lastname;
  private String email;
}
