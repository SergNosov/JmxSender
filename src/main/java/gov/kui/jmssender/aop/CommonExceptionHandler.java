package gov.kui.jmssender.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    private String propertiesMaxSize;

    @Autowired
    public void setPropertiesMaxSize(@Value("${spring.servlet.multipart.max-file-size}") String maxFileSize) {
        this.propertiesMaxSize = maxFileSize;
    }

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception ex, Model model) {
        log.info("----- handleAllException:");
        logginException(ex);

        final StringBuilder message = new StringBuilder("---- ")
                .append(ex.toString())
                .append(";");

        model.addAttribute("errorMessage", message);

        return "errorpage";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeException(MaxUploadSizeExceededException ex, Model model) {
        log.info("----- handleMaxUploadSizeException:");
        logginException(ex);

        final StringBuilder message = new StringBuilder("---- Превышен размер допустимого значения при загрузки файла: ")
                .append(propertiesMaxSize)
                .append("; ")
                .append(ex.getMessage());

        model.addAttribute("errorMessage", message);

        return "errorpage";
    }

    private void logginException(Exception ex) {
        log.error("--- exception: " + ex.getMessage());
      //  Arrays.stream(ex.getStackTrace()).forEach(el -> log.error("--- stackTrace: " + el));
    }
}
