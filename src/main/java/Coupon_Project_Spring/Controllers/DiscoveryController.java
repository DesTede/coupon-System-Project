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

/**
 * This class is a RestController that handles all the requests from the discovery service.
 * It uses the DiscoveryService to handle the requests.
 */
@RestController
@RequestMapping("/discovery")
public class DiscoveryController {

    /**
     * here we inject the DiscoveryService to the controller.
     */
    private DiscoveryService discoveryService;

    public DiscoveryController(DiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    /**
     * This method is used to get all the coupons that are available.
     * It uses the DiscoveryService to get the coupons.
     * @return List of all the coupons that are available.
     */
    @GetMapping("/coupons")
    public List<Coupon> getAllCoupons(){
        return discoveryService.getAllCoupons();
    }
    
    /**
     * This method is used to get the details of a specific coupon.
     * It uses the DiscoveryService to get the details.
     * @param id of the coupon.
     * @return Coupon with the details of the coupon.
     * @throws CouponNotFoundException if the coupon is not found.
     */
    @GetMapping("/coupon/{id}")
    public Coupon getCoupon(@PathVariable int id) throws CouponNotFoundException {
        return discoveryService.getCoupon(id);
    }
    
    /**
     * This method is used to get all the categories.
     * It uses the DiscoveryService to get the categories.
     * @return List of all the categories that are available.
     */
    @GetMapping("/categories")
    public List<Category> getCategories() {
        return discoveryService.getCategories();
    }
    
    /**
     * This method is used to get all the coupons that are available in a specific price range.
     * It uses the DiscoveryService to get the coupons.
     * @param price of the coupons.
     * @return List of all the coupons that are available in the price range.
     */
    @GetMapping("/coupons/price/{price}")
    public List<Coupon> getCouponsByPrice(@PathVariable double price) {
        return discoveryService.getCouponsByPrice(price);
    }
    
    /**
     * This method is used to get all the coupons that are available in a specific category.
     * It uses the DiscoveryService to get the coupons.
     * @param category of the coupons.
     * @return List of all the coupons that are available in the category.
     */
    @GetMapping("/coupons/category/{category}")
    public List<Coupon> getCouponsByCategory(@PathVariable String category) {
        return discoveryService.getCouponsByCategory(Category.valueOf(category));
    }
}
