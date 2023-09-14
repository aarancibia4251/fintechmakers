package com.makers.api.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@Table(name = "makers")
@Entity
public class MakerEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String firstname;

  @Column
  private String lastname;

  @Column(unique = true)
  private String email;

}
