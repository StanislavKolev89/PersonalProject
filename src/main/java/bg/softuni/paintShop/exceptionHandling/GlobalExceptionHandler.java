package bg.softuni.paintShop.exceptionHandling;


import bg.softuni.paintShop.exception.ObjectNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView globalHandler() {
        return new ModelAndView("error");
    }
}
