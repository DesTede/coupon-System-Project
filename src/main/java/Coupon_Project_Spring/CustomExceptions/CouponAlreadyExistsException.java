package Coupon_Project_Spring.CustomExceptions;

public class CouponAlreadyExistsException extends Exception{
    

    public CouponAlreadyExistsException(String message) {
        super(message);
    }
}
