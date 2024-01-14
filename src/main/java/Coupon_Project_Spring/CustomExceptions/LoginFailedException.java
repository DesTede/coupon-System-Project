package Coupon_Project_Spring.CustomExceptions;

public class LoginFailedException extends Exception{
    public LoginFailedException() {
        super("Incorrect Client type");
    }
}
