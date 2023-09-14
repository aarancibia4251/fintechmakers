package com.makers.api.ui.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdateMakerRequest {
  private long id;
  @NotBlank(message = "First name can not be null")
  private String firstname;
  @NotBlank(message = "Last name can not be null")
  private String lastname;
  @NotBlank(message = "Email name can not be null")
  @Email
  private String email;
}
