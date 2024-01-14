package Coupon_Project_Spring.CustomExceptions;

public class CompanyLoginFailedException extends Exception{
    public CompanyLoginFailedException() {
        super("Company login failed! credentials incorrect");
    }
}
