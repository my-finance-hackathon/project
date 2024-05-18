package my.finance.hackathon.app.controller.defaults;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public String allExceptionHandler(Exception ex) {
        log.info(Arrays.toString(ex.getStackTrace()));
        return ex.getMessage();
    }

}
