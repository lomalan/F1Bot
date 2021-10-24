package com.lomalan.main.rest.model.f1;

import lombok.Data;

@Data
public class Driver {
  private String driverId;
  private String permanentNumber;
  private String code;
  private String url;
  private String givenName;
  private String familyName;
  private String dateOfBirth;
  private String nationality;
}
