package auca.ac.rw.carRentalHub.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import auca.ac.rw.carRentalHub.model.Feature;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, UUID> {
    // you could add custom queries here if needed
}
