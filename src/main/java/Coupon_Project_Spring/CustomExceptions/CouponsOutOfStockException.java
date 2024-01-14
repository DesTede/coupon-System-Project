package Coupon_Project_Spring.CustomExceptions;

public class CouponsOutOfStockException extends Exception{
    
    public CouponsOutOfStockException(String message) {
        super(message);
    }
}
