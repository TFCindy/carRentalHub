package auca.ac.rw.carRentalHub.model;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    
    @Column(unique = true)
    private String licenseNumber;
    
    private LocalDate licenseExpiryDate;

    /**
     * ONE-TO-MANY RELATIONSHIP with Reservation
     * 
     * EXPLANATION:
     * - One customer can have many reservations
     * - mappedBy = "customer" indicates the relationship is owned by Reservation.customer field
     * - cascade = CascadeType.ALL means operations on Customer cascade to Reservations
     * - orphanRemoval = true removes reservations if removed from collection
     * - The foreign key "customer_id" is in the Reservation table (MANY side)
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();

    // Constructors
    public Customer() {}

    public Customer(String firstName, String lastName, String email, String licenseNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.licenseNumber = licenseNumber;
    }

    // Helper methods for bidirectional relationship
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setCustomer(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setCustomer(null);
    }

    // Business logic method
    public boolean hasValidLicense() {
        return licenseExpiryDate != null && licenseExpiryDate.isAfter(LocalDate.now());
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public LocalDate getLicenseExpiryDate() { return licenseExpiryDate; }
    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) { 
        this.licenseExpiryDate = licenseExpiryDate; 
    }

    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { 
        this.reservations = reservations; 
    }
}