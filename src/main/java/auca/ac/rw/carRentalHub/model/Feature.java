package auca.ac.rw.carRentalHub.model;

import java.util.UUID;
import java.util.List;
import java.math.BigDecimal;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "feature")
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    private String description;

    private BigDecimal basePrice;

    // One-to-Many with join table (VehicleFeature)
    @OneToMany(mappedBy = "feature", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VehicleFeature> vehicleFeatures;

    // Constructors
    public Feature() {}

    public Feature(String name, String description, BigDecimal basePrice) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public List<VehicleFeature> getVehicleFeatures() { return vehicleFeatures; }
    public void setVehicleFeatures(List<VehicleFeature> vehicleFeatures) { 
        this.vehicleFeatures = vehicleFeatures; 
    }
}