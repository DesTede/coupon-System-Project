package Coupon_Project_Spring.Repositories;

import Coupon_Project_Spring.Models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * this interface extends JpaRepository and used to interact with the database and manage the Company table.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    
    /**
     * this method is used to check if a company exists in the database using the email or name entered.
     * @param email - a String parameter one has to enter to compare with the database.
     * @param name - a String parameter one has to enter to compare with the database.
     * @return - a boolean value. true if a company matching the email or name found in the database, false otherwise.
     */
    boolean existsByEmailOrName(String email, String name);

    
    /**
     * this method is used to find a company in the database using the email and password entered.
     * @param email - a String parameter one has to enter to compare with the database.
     * @param password - a String parameter one has to enter to compare with the database.
     * @return - a Company object if a company matching the email and password found in the database, null otherwise.
     */
    Company findByEmailAndPassword(String email, String password);
}
