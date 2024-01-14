package Coupon_Project_Spring.CustomExceptions;

public class CouponNotFoundException extends Exception{
    

    public CouponNotFoundException(String message) {
        super(message);
    }
}
