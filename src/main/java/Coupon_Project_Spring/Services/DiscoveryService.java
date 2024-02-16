package Coupon_Project_Spring.Services;
import Coupon_Project_Spring.CustomExceptions.CouponNotFoundException;
import Coupon_Project_Spring.Models.Category;
import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Repositories.CouponRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * a service class that provides the business logic for the discovery controller.
 * here we implement the business logic of the discovery controller.
 * the discovery controller is responsible for providing the customer and non customers with the available coupons.
 
 */

@Service
public class DiscoveryService {

    /**
     * a repository of all coupons.
     * this repository is used to access the database and retrieve the coupons.
     * the repository is injected into the service using the constructor.
     */
    private CouponRepository couponRepo;

    public DiscoveryService(CouponRepository couponRepo) {
        this.couponRepo = couponRepo;
    }
    

    /**
     * A method to return all the coupons in the database.
     * @return a list of all the coupons in the database.
     */
    public List<Coupon> getAllCoupons(){
        return couponRepo.findAll();
    }
    
    /**
     * A method to return a coupon by its id.
     * @param id - the id of the coupon.
     * @return the coupon entity of the coupon with the provided id.
     * @throws CouponNotFoundException - custom exception when a coupon you're trying to get is not in the database.
     */
    public Coupon getCoupon(int id) throws CouponNotFoundException {
        return couponRepo.findById(id).orElseThrow(()-> new CouponNotFoundException("Coupon not found"));
    }

    /**
     * A method to return all the categories of the coupons in the database.
     * @return  all the categories of the coupons in the database.
     */
    public List<Category> getCategories() {
        return List.of(Category.values());
    }

    /**
     * A method to return all the coupons in the database that are less than or equal to the provided price.
     * @param price - the maximum price of the coupons.
     * @return a list of all the coupons in the database that are less than or equal to the provided price.
     */
    public List<Coupon> getCouponsByPrice(double price){
        return getAllCoupons().stream().filter(c->c.getPrice() <=price).toList();
    }
    
    /**
     * A method to return all the coupons in the database that are of the provided category.
     * @param category - the category of the coupons.
     * @return a list of all the coupons in the database that are of the provided category.
     */
    public List<Coupon> getCouponsByCategory(Category category){
        List<Coupon> allCoupons = getAllCoupons();
        return allCoupons.stream().filter(c->c.getCategory().equals(category)).toList();
    }
    
}
