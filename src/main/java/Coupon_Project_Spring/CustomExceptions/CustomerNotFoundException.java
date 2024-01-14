package Coupon_Project_Spring.CustomExceptions;

public class CustomerNotFoundException extends Exception{

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
