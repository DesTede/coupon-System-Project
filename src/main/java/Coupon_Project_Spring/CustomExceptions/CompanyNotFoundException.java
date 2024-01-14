package Coupon_Project_Spring.CustomExceptions;

public class CompanyNotFoundException extends Exception{
    
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
