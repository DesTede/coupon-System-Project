package Coupon_Project_Spring.CustomExceptions;

public class AdminLoginFailedException extends Exception{
    public AdminLoginFailedException() {
        super("Admin login failed! credentials incorrect");
    }
    
}
