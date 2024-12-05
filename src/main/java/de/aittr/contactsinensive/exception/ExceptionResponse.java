package de.aittr.contactsinensive.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ExceptionResponse(String message,
                                String error,
                                int stausCode,
                                LocalDateTime timestamp
) implements Serializable {
}
