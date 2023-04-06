package com.yatsevich.citylist.api.adapter.out.service.exception;

public class DefaultDataParsingException extends RuntimeException {

  public static final String GENERIC_IO_ERROR_MESSAGE = "An error occurred while filling the database with initial data. Error message: %s";

  public DefaultDataParsingException(String message) {
    super(message);
  }
}
