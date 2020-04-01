package com.springboot.pratice.springbootraml;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Item with given id found")
public class ItemNotFoundException extends RuntimeException {
}
