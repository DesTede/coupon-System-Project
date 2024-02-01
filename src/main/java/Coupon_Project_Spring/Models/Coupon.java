package Coupon_Project_Spring.Models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * this class represents a Spring entity in the system.
 * it contains information about the coupon including id (PK, AI, private setter),
 * the company it's associated with, amount in stock, category, title, description, start and end dates
 * its price and a list of customers who purchased it.
 */

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
public class Coupon {
    
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    
    @ManyToOne
    private Company company;
    private int amount;
    
    @Enumerated(EnumType.STRING)
    private Category category;
    private String title;
    private String description;
    private String image;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    
    @ManyToMany(mappedBy = "coupons", fetch = FetchType.EAGER)
    private Set<Customer> customers;

    public Coupon(Company company, int amount, Category category, String title, String description, String image, LocalDate startDate, LocalDate endDate, double price, Set<Customer> customers) {
        this.company = company;
        this.amount = amount;
        this.category = category;
        this.title = title;
        this.description = description;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.customers = new HashSet<>();
    }

    public Coupon(int amount, Category category, String title, String description, String image, LocalDate startDate, LocalDate endDate, double price, Set<Customer> customers) {
        this.amount = amount;
        this.category = category;
        this.title = title;
        this.description = description;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.customers = new HashSet<>(); // i want to add here an empty set of customers, so that whenever i create a coupon, it will be created with an empty set of customers
        
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", company name: " + company.getName() +
                ", company id: " + company.getId() +
                ", company email: " + company.getEmail() +
                ", amount: " + amount +
                ", category: " + category +
                ", title: " + title + '\'' +
                ", description: " + description + '\'' +
                ", startDate: " + startDate +
                ", endDate: " + endDate +
                ", price: " + price +
                ", customers: " + customers.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id && amount == coupon.amount && Double.compare(price, coupon.price) == 0 && Objects.equals(company, coupon.company) && category == coupon.category && Objects.equals(title, coupon.title) && Objects.equals(description, coupon.description) && Objects.equals(image, coupon.image) && Objects.equals(startDate, coupon.startDate) && Objects.equals(endDate, coupon.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, company, amount, category, title, description, image, startDate, endDate, price);
    }


    @JsonGetter("company")
    public  String getCompanyData(){
        if (company != null)
            return "name: " + company.getName() + " email: " + company.getEmail();
        else 
            return  "No associated company";
    }

    @JsonGetter("customers")
    public Serializable getCustomersData(){
        return customers != null? customers.size() : "0";
    }
}

//
//public class CustomDateDeserializer
//        extends StdDeserializer<Date> {
//
//    private static SimpleDateFormat formatter
//            = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
//
//    public CustomDateDeserializer() {
//        this(null);
//    }
//
//    public CustomDateDeserializer(Class<?> vc) {
//        super(vc);
//    }
//


//    @Override
//    public Date deserialize(
//            JsonParser jsonparser, DeserializationContext context)
//            throws IOException {
//
//        String date = jsonparser.getText();
//        try {
//            return formatter.parse(date);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public class EventWithSerializer {
//        public String name;
//
//        @JsonDeserialize(using = CustomDateDeserializer.class)
//        public Date eventDate;
//    }



