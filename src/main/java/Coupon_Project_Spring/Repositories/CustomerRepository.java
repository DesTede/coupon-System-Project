package Coupon_Project_Spring.Repositories;

import Coupon_Project_Spring.Models.Coupon;
import Coupon_Project_Spring.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * this interface extends JpaRepository and used to interact with the database and manage the Customer
 * and the customers_coupons tables.
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    
    /**
     * a method used to check if a customer exists in the database by email.
     * @param email - the email of the customer to be found.
     * @return true if a customer matching the email found in the database, false otherwise.
     */
    boolean existsByEmail(String email);

    
    /**
     * a method used to find a customer in the database by email and password.
     * @param email - the email of the customer to be found
     * @param password - the password of the customer to be found.
     * @return the customer entity if found, null otherwise.
     */
    Customer findByEmailAndPassword(String email, String password);

    
    /**
     * a method used to delete coupon purchases from the customers_coupons table.
     * @param customerId - the id of the customer whose purchases are to be deleted.
     */
    @Transactional
    @Modifying
    @Query(value = "delete from Customers_coupons where customers_id = ?1", nativeQuery = true)
    void deletePurchaseByCustomer(int customerId);
}
