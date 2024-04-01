package com.codingdojo.soloproject.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.soloproject.models.Book;
import com.codingdojo.soloproject.models.LoginUser;
import com.codingdojo.soloproject.models.User;
import com.codingdojo.soloproject.repositories.BookRepository;
import com.codingdojo.soloproject.repositories.UserRepository;

@Service
public class UserService {
	
	// adding the user repository as a dependency
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private BookRepository bookRepository;
	
    
    public User register(User newUser, BindingResult result) {
        // Reject if email is taken (present in database)
        Optional<User> potentialUser = userRepository.findByEmail(newUser.getEmail());
        if (potentialUser.isPresent()) {
            result.rejectValue("email", "Matches", "Email is already taken");
        }
        
        // Reject if password doesn't match confirmation
        if (!newUser.getPassword().equals(newUser.getConfirm())) {
            result.rejectValue("confirm", "Matches", "Passwords do not match");
        }
        
        // Return null if result has errors
        if (result.hasErrors()) {
            return null;
        }
        
        // Hash and set password, save user to database
        String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashed);
        return userRepository.save(newUser);
    }

    
 
	 public User login(LoginUser newLoginObject, BindingResult result) {
	
	     // Find user in the DB by email
	     Optional<User> potentialUser = userRepository.findByEmail(newLoginObject.getEmail());
	
	     // Reject if user is NOT present
	     if (!potentialUser.isPresent()) {
	         result.rejectValue("email", "Matches", "Invalid email or password");
	         return null;
	     }
	
	     // Get the user object from the optional
	     User user = potentialUser.get();
	
	     // Reject if BCrypt password match fails
	     if (!BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword())) {
	         result.rejectValue("password", "Matches", "Invalid email or password");
	         return null;
	     }
	
	     // Return null if result has errors
	     if (result.hasErrors()) {
	         return null;
	     }
	
	     // Return the authenticated user object
	     return user;
	 }

    
    
	 
    // returns all the users
    public List<User> allUsers() {
        return (List<User>) userRepository.findAll();
    }
    
    
    // creates a user
    public User createUser(User u) {
        return userRepository.save(u);
    }
    
    
    // retrieves a user
    public User findUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            return null;
        }
    }
    
    
    
    //updates a user 
    public User updateUser(User u) {
        return userRepository.save(u);
    }
    
    
    
    // delete a user
    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
        	userRepository.deleteById(id);
        } 
    }

}
