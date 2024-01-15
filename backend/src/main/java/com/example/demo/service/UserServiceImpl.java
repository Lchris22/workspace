package com.example.demo.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

/**
* Implementation of the UserService interface providing operations related to users.
*/
@Service
public class UserServiceImpl implements UserService {
	/**
     * Repository for accessing user data in the database.
     */
	@Autowired
    private final UserRepository userRepository;
	
	/**
     * Password encoder for encrypting user passwords.
     */
	@Autowired
    private final BCryptPasswordEncoder passwordEncoder;
	
	/**
     * Constructor for initializing UserRepository and BCryptPasswordEncoder.
     *
     * @param userRepository   The repository for user data.
     * @param passwordEncoder  The password encoder for encrypting user passwords.
     */
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Creates a new user in the system after checking if the username is unique.
     *
     * @param user The User object containing user information.
     * @return The created User object.
     * @throws Exception If the username is not unique.
     */
    @Override
    public User createUser(User user) throws Exception {
        User localUser = userRepository.findByUsername(user.getUsername());
        if (localUser != null) {
            System.out.println("User is already there!!");
            throw new Exception("User is already present");
        } else {
            // Encrypt the user's password before saving
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            return userRepository.save(user);
        }
    }

    /**
     * Retrieves a list of all users in the system.
     *
     * @return List of User objects representing all users.
     */
	public List<User> getAllUsers() {
		return userRepository.findAll() ;
	}   
}
