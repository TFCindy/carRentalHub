package auca.ac.rw.carRentalHub.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique = true)
	private String username;

	@Column(unique = true)
	private String email;

	private String password;

	// Simple role relationship: a user has one role
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	// User location (province/district/...)
	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	public User() {}

	// Getters and setters
	public UUID getId() { return id; }
	public void setId(UUID id) { this.id = id; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public Role getRole() { return role; }
	public void setRole(Role role) { this.role = role; }

	public Location getLocation() { return location; }
	public void setLocation(Location location) { this.location = location; }
}
