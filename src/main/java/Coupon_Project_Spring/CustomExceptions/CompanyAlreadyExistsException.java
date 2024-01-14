package Coupon_Project_Spring.CustomExceptions;

public class CompanyAlreadyExistsException extends Exception{
    public CompanyAlreadyExistsException(String message) {
        super(message);
    }
    
}
