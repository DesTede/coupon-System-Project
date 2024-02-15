package Coupon_Project_Spring.JobThread;

import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * this Spring service component is designed to handle the daily coupon cleanup job.
 * this scheduled job runs at fixed intervals and deletes all expired coupons from the database.
 */
@Service 
public class CouponExpirationDailyJob {

    private boolean quit; 
    private CouponRepository couponRepo;
    

    public CouponExpirationDailyJob(CouponRepository couponRepo) {
        this.couponRepo = couponRepo;
    }

    /**
     * this method is used to run the daily coupon cleanup job. here we implement the logic of the job.
     * the method Iterates over all coupons in the database.
     * @Transactional annotation to ensure that the method runs as a transaction.
     * @Scheduled annotation to ensure that the method runs at fixed intervals.
     */
    @Transactional
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24 )
    public void run() {
        if (!quit) {
            System.out.println("Daily coupon cleanup is now starting");
            int count = 0;
            try {
                LocalDate today = LocalDate.now();
                List<Coupon> couponList = new ArrayList<>();
                for (Coupon coupon: couponRepo.findAll()) {
                    if (coupon.getEndDate().isBefore(today)){
                    couponList.add(coupon);                      // for debugging purposes
                    count++;                                    // for debugging purposes
                        couponRepo.deleteCouponPurchases(coupon.getId());
                        couponRepo.deleteById(coupon.getId());
                    }
                }
                System.out.println("number of deleted coupons: " + count);
                if (couponList.isEmpty()){
                    System.out.println("No expired coupons found ");
                }else 
                    System.out.println("Expired CouponList is: " + couponList.size());
                
            } catch (Exception e){
                System.out.println("Daily coupon cleanup has been interrupted");
            }
        }
    }
    

    /**
     * using this method, we stop the daily cleanup job.
     * we set the boolean value of quit to true.
     */
    public void stop(){
        quit = true;
    }
    
}
