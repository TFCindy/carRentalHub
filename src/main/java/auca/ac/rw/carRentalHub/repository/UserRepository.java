package auca.ac.rw.carRentalHub.repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import auca.ac.rw.carRentalHub.model.User;
import auca.ac.rw.carRentalHub.model.Location;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * existsBy() METHODS DEMONSTRATION
     * 
     * EXPLANATION:
     * Spring Data JPA automatically implements these methods based on method naming conventions.
     * The framework parses the method name, creates the appropriate JPQL query, 
     * and returns true if at least one result exists.
     * 
     * How it works:
     * 1. Method name starts with "existsBy"
     * 2. Followed by property names (Username, Email, etc.)
     * 3. Spring generates: SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = ?1
     * 4. More efficient than using .findBy().isPresent() because it doesn't fetch the entity
     */
    
    // Basic existence checks
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    // Check with multiple conditions (AND)
    boolean existsByUsernameAndEmail(String username, String email);
    
    // Check for existence excluding a specific ID (useful for updates)
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u " +
           "WHERE u.email = :email AND u.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") UUID id);
    
    // Check if user has any reservations
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
           "FROM User u JOIN u.reservations r WHERE u.id = :userId")
    boolean existsUserWithReservations(@Param("userId") UUID userId);
    
    // Complex existence check with location
    boolean existsByLocationAndRoleName(Location location, String roleName);
    
    // For requirement #8 - find users by province
    @Query("SELECT u FROM User u WHERE u.location.id IN " +
           "(SELECT l.id FROM Location l WHERE l.code = :identifier OR l.name = :identifier " +
           "OR l.parent.code = :identifier OR l.parent.name = :identifier)")
    List<User> findAllUsersByProvinceIdentifier(@Param("identifier") String identifier);
}