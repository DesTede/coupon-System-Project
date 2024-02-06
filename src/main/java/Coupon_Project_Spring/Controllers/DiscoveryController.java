package Coupon_Project_Spring.Controllers;

import Coupon_Project_Spring.CustomExceptions.CouponNotFoundException;
import Coupon_Project_Spring.CustomExceptions.CustomerNotFoundException;
import Coupon_Project_Spring.Models.Category;
import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Services.DiscoveryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/discovery")
public class DiscoveryController {
    
    private DiscoveryService discoveryService;

    public DiscoveryController(DiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    @GetMapping("/coupons")
    public List<Coupon> getAllCoupons(){
        return discoveryService.getAllCoupons();
    }
    
    @GetMapping("/coupon/{id}")
    public Coupon getCoupon(@PathVariable int id) throws CouponNotFoundException {
        return discoveryService.getCoupon(id);
    }
    
    @GetMapping("/categories")
    public List<Category> getCategories() {
        return discoveryService.getCategories();
    }
    
    @GetMapping("/coupons/price/{price}")
    public List<Coupon> getCouponsByPrice(@PathVariable double price) throws CustomerNotFoundException {
        return discoveryService.getCouponsByPrice(price);
    }
    
    @GetMapping("/coupons/category/{category}")
    public List<Coupon> getCouponsByCategory(@PathVariable String category) throws CustomerNotFoundException {
        return discoveryService.getCouponsByCategory(Category.valueOf(category));
    }
}
