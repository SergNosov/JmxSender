package gov.kui.jmssender.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception ex, Model model) {
        logginException(ex);

        final StringBuilder message = new StringBuilder("---- ")
                .append(ex.toString())
                .append(";");

        model.addAttribute("errorMessage", message);

        return "errorpage";
    }

    private void logginException(Exception ex) {
        log.error("--- exception: " + ex.getMessage());
        //Arrays.stream(ex.getStackTrace()).forEach(el -> log.error("--- stackTrace: " + el));
    }
}
