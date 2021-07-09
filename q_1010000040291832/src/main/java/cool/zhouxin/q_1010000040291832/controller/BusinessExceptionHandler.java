package cool.zhouxin.q_1010000040291832.controller;

import cool.zhouxin.q_1010000040291832.pojo.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author zhouxin
 * @since 2020/1/21 14:43
 */
@RestControllerAdvice
public class BusinessExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getAllErrors()
                                              .stream()
                                              .map(ObjectError::getDefaultMessage)
                                              .sorted()
                                              .collect(Collectors.joining(","));

        Response response = Response.builder().code(-1).msg(errorMsg).build();
        ResponseEntity<Response> result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        return result;
    }
}
