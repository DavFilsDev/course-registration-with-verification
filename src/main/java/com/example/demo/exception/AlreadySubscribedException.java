package com.example.demo.exception;

public class AlreadySubscribedException extends RuntimeException {
  public AlreadySubscribedException(String message) {
    super(message);
  }
}
