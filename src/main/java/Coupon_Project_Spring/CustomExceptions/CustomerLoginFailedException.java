package Coupon_Project_Spring.CustomExceptions;

public class CustomerLoginFailedException extends Exception{
    public CustomerLoginFailedException() {
        super("Customer login failed! credentials incorrect");
    }

    
}
