package Coupon_Project_Spring.CustomExceptions;

public class CouponAlreadyPurchasedException extends Exception{
    

    public CouponAlreadyPurchasedException(String message) {
        super(message);
    }
}

