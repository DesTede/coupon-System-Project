package Coupon_Project_Spring.Controllers;

import Coupon_Project_Spring.CustomExceptions.CouponNotFoundException;
import Coupon_Project_Spring.CustomExceptions.CustomerNotFoundException;
import Coupon_Project_Spring.Models.Category;
import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Services.PublicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController{
    
    private PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping("/coupons")
    public List<Coupon> getAllCoupons(){
        return publicService.getAllCoupons();
    }
    
    @GetMapping("/coupon/{id}")
    public Coupon getCoupon(@PathVariable int id) throws CouponNotFoundException {
        return publicService.getCoupon(id);
    }
    
    @GetMapping("/categories")
    public List<Category> getCategories() {
        return publicService.getCategories();
    }
    
    @GetMapping("/coupons/price/{price}")
    public List<Coupon> getCouponsByPrice(@PathVariable double price) throws CustomerNotFoundException {
        return publicService.getCouponsByPrice(price);
    }
    
    @GetMapping("/coupons/category/{category}")
    public List<Coupon> getCouponsByCategory(@PathVariable String category) throws CustomerNotFoundException {
        return publicService.getCouponsByCategory(Category.valueOf(category));
    }
}
