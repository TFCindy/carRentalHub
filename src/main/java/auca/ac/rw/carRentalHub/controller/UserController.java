package auca.ac.rw.carRentalHub.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.ac.rw.carRentalHub.dto.UserDTO;
import auca.ac.rw.carRentalHub.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * ENDPOINT: GET /api/users/by-province?identifier=Kigali
     * ENDPOINT: GET /api/users/by-province?identifier=RW-01
     * 
     * This endpoint implements REQUIREMENT #8
     * It retrieves all users from a province using either province code OR province name
     */
    @GetMapping("/by-province")
    public ResponseEntity<Map<String, Object>> getUsersByProvince(
            @RequestParam String identifier) {
        
        List<UserDTO> users = userService.getUsersByProvince(identifier);
        
        Map<String, Object> response = new HashMap<>();
        response.put("provinceIdentifier", identifier);
        response.put("totalUsers", users.size());
        response.put("users", users);
        
        // Add explanation for the assignment
        response.put("explanation", 
            "Found " + users.size() + " users in province '" + identifier + "'. " +
            "The query searches for all users whose location is the province itself " +
            "or any district/sector/cell/village within that province using the " +
            "location hierarchy (parent-child relationship).");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test endpoint to check if a user exists by username
     * Demonstrates existsBy() method
     */
    @GetMapping("/check-exists")
    public ResponseEntity<Map<String, Boolean>> checkUserExists(
            @RequestParam String username) {
        
        // This uses the existsByUsername method from requirement #7
        boolean exists = userRepository.existsByUsername(username);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        
        return ResponseEntity.ok(response);
    }
}