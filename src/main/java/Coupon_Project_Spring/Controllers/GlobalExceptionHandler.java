package Coupon_Project_Spring.Controllers;

import Coupon_Project_Spring.CustomExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{
    
    @ExceptionHandler({CompanyNotFoundException.class, CouponNotFoundException.class, CustomerNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({AdminLoginFailedException.class,CustomerLoginFailedException.class, CompanyLoginFailedException.class, LoginFailedException.class})
    public ResponseEntity<String> handleLoginException(Exception ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }  
    

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<String> handleForbidden(UnAuthorizedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
    

    
    @ExceptionHandler({CompanyAlreadyExistsException.class, CustomerAlreadyExistsException.class, CouponAlreadyExistsException.class, CouponAlreadyPurchasedException.class, CouponExpiredException.class, CouponsOutOfStockException.class})
    public ResponseEntity<String> handleAlreadyExistsException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
