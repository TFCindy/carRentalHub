package auca.ac.rw.carRentalHub.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import auca.ac.rw.carRentalHub.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
