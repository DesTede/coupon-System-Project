package Coupon_Project_Spring.Repositories;

import Coupon_Project_Spring.Models.Category;
import Coupon_Project_Spring.Models.Company;
import Coupon_Project_Spring.Models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * this interface extends JpaRepository and used to interact with the database and manage the Coupons and
 * the customers_coupons tables.
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    /**
     * a custom query used to find coupons by their company.
     * @param company - the company whose coupons are to be found.
     * @return a list of coupons.
     */
    List<Coupon> findCouponsByCompany(Company company);

    
    /**
     * a method used to delete coupons from the database by the company id.
     * @param companyId - the id of the company whose coupons are to be deleted.
     */
    void deleteCouponsByCompanyId(int companyId);


    /**
     * a custom query used to delete coupon purchases from the customers_coupons table.
     * @param couponId - the id of the coupon whose purchases are to be deleted.
     */
    @Transactional
    @Modifying
    @Query(value = "delete from customers_coupons where coupons_id = ?1",nativeQuery = true)
    void deleteCouponPurchases(int couponId);
}
