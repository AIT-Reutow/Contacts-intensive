package de.aittr.contactsinensive.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    protected final HttpStatus httpStatus;
    protected final String message;
}
