package Coupon_Project_Spring.CustomExceptions;

public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException() {
        super("Category not found");
    }
}
