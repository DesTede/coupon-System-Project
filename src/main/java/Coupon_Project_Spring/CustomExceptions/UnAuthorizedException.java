package Coupon_Project_Spring.CustomExceptions;

public class UnAuthorizedException extends Exception{
    public UnAuthorizedException(String message) {
        super(message);
    }
}
