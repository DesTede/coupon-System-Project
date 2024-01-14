package Coupon_Project_Spring.CustomExceptions;

public class CustomerAlreadyExistsException extends Exception{
    

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
