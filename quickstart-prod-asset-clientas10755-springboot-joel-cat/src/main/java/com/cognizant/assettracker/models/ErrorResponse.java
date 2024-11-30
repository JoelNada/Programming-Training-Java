package com.cognizant.assettracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class ErrorResponse {

  private final String message;
  private final int code;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorView = (ErrorResponse) o;
    return Objects.equals(message, errorView.message) && Objects.equals(code, errorView.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code + message);
  }

  @Override
  public String toString() {
    return "ErrorView{" + "code='" + code + '\'' + "message='" + message + '\'' + '}';
  }
}
