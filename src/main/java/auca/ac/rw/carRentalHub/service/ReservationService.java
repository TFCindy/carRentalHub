package auca.ac.rw.carRentalHub.service;

import java.util.UUID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auca.ac.rw.carRentalHub.model.Reservation;
import auca.ac.rw.carRentalHub.model.Customer;
import auca.ac.rw.carRentalHub.model.Vehicle;
import auca.ac.rw.carRentalHub.model.Payment;
import auca.ac.rw.carRentalHub.repository.ReservationRepository;
import auca.ac.rw.carRentalHub.repository.CustomerRepository;
import auca.ac.rw.carRentalHub.repository.VehicleRepository;
import auca.ac.rw.carRentalHub.repository.PaymentRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Reservation saveReservation(Reservation reservation, UUID customerId, UUID vehicleId) {
        Optional<Customer> c = customerRepository.findById(customerId);
        Optional<Vehicle> v = vehicleRepository.findById(vehicleId);
        if (c.isEmpty() || v.isEmpty()) {
            return null;
        }
        reservation.setCustomer(c.get());
        reservation.setVehicle(v.get());
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservation(UUID id) {
        return reservationRepository.findById(id);
    }

    public Page<Reservation> listReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    public Page<Reservation> listByCustomer(UUID customerId, Pageable pageable) {
        return reservationRepository.findReservationsByCustomerId(customerId, pageable);
    }

    @Transactional
    public String addPayment(UUID reservationId, Payment payment) {
        Optional<Reservation> r = reservationRepository.findById(reservationId);
        if (r.isEmpty()) {
            return "Reservation not found";
        }
        Reservation reservation = r.get();
        payment.setReservation(reservation);
        reservation.setPayment(payment);
        paymentRepository.save(payment);
        reservationRepository.save(reservation);
        return "Payment added";
    }
}
