package de.samples.blogposts.boundary;

import jakarta.validation.ValidationException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * Übersetzt in der Boundary geworfene Exceptions in HTTP Responses. Damit elimiert man einen Großteil der ResponseEntity-Erzeugungen im Controller.
 * Mit RFC-7807 werden nun auch Problem-DTOs in den Response-Body geschrieben. Daher die Verwendung von {@link RestControllerAdvice}.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  protected ProblemDetail handleValidationException(ValidationException ex) {
    var result = ProblemDetail.forStatus(UNPROCESSABLE_ENTITY);
    result.setTitle("Validation Error");
    result.setDetail(ex.getMessage());
    return result;
  }

}
