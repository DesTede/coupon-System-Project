package Coupon_Project_Spring.Services;

import Coupon_Project_Spring.CustomExceptions.CouponNotFoundException;
import Coupon_Project_Spring.Models.Category;
import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Repositories.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscoveryService {
    
    private CouponRepository couponRepo;

    public DiscoveryService(CouponRepository couponRepo) {
        this.couponRepo = couponRepo;
    }
    

    public List<Coupon> getAllCoupons(){
        return couponRepo.findAll();
    }
    
    public Coupon getCoupon(int id) throws CouponNotFoundException {
        return couponRepo.findById(id).orElseThrow(()-> new CouponNotFoundException("Coupon not found"));
    }

    public List<Category> getCategories() {
        return List.of(Category.values());
    }
    
    public List<Coupon> getCouponsByPrice(double price){
        return getAllCoupons().stream().filter(c->c.getPrice() <=price).toList();
    }
    
    public List<Coupon> getCouponsByCategory(Category category){
        List<Coupon> allCoupons = getAllCoupons();
        return allCoupons.stream().filter(c->c.getCategory().equals(category)).toList();
    }
    
}
