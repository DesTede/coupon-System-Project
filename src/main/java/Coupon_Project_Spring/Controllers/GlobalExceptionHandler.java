package Coupon_Project_Spring.Controllers;

import Coupon_Project_Spring.CustomExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class is a global exception handler for all the exceptions that are thrown in the project.
 */
@ControllerAdvice
public class GlobalExceptionHandler{
    
    /**
     * This method handles all the NotFoundExceptions that are thrown in the project.
     * @param ex The exception that was thrown.
     * @return A response entity with the exception message and the appropriate status code.
     */
    @ExceptionHandler({CompanyNotFoundException.class, CouponNotFoundException.class, CustomerNotFoundException.class, CategoryNotFoundException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * This method handles all the exceptions that are thrown when a login fails.
     * @param ex The exception that was thrown.
     * @return A response entity with the exception message and the appropriate status code.
     */
    @ExceptionHandler({AdminLoginFailedException.class,CustomerLoginFailedException.class, CompanyLoginFailedException.class, LoginFailedException.class})
    public ResponseEntity<String> handleLoginException(Exception ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }  
    

    /**
     * This method handles all the exceptions that are thrown when a user tries to perform an action that he is not authorized to do.
     * @param ex The exception that was thrown.
     * @return A response entity with the exception message and the appropriate status code.
     */
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<String> handleForbidden(UnAuthorizedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
    
    
    /**
     * This method handles all the exceptions that are thrown when a user tries to perform a bad request.
     * @param ex The exception that was thrown.
     * @return A response entity with the exception message and the appropriate status code.
     */
    @ExceptionHandler({CompanyAlreadyExistsException.class, CustomerAlreadyExistsException.class, CouponAlreadyExistsException.class, CouponAlreadyPurchasedException.class, CouponExpiredException.class, CouponsOutOfStockException.class})
    public ResponseEntity<String> handleAlreadyExistsException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
