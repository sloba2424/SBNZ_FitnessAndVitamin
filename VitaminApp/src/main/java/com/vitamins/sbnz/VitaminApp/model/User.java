package com.sbnz.ibar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="\"User\"")
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode
public abstract class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
    		name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	@EqualsAndHashCode.Include
	private UUID id;

	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String firstName;
	

	private String lastName;
	
    private Long lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))

    private List<Authority> authorities;


	@Column(nullable = false)
	private Gender gender;

	@Column(nullable = false)
	private float weight;

	@Column(nullable = false)
	private float height;

	@Column(nullable = false)
	private int age;

	@Column(nullable = false)
	private float bodyFat;

	@Column(nullable = false)
	private int calories;

	@Column(nullable = false)
	private ActivityLevel activityLevel;

	@Column(nullable = false)
	private BodyStatus bodyStatus;

	@Column(nullable = false)
	private float bmi;

	@Column(nullable = false)
	private float bmr;

	@Column(nullable = false)
	private List<Meal> mealPreference;

	@Column(nullable = false)
	private List<Meal> mealAllergies;


	private boolean enabled;

	public User(UUID id) {
		super();
		this.id = id;
	}

    @Override
    public List<Authority> getAuthorities() {
        return this.authorities;
    }
    
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	public String getInitials() {
		return String.format("%s%s", this.firstName.charAt(0), this.lastName.charAt(0));
	}
	
}
