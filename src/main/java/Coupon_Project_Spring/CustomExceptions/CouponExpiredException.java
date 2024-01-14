package Coupon_Project_Spring.CustomExceptions;

public class CouponExpiredException extends Exception{
    

    public CouponExpiredException(String message) {
        super(message);
    }
}
