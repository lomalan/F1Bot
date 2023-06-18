package com.lomalan.main.rest.model.f1;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Circuit {
  private String url;

  @SerializedName("Location")
  private Location location;
}
