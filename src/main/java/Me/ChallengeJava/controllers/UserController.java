package Me.ChallengeJava.controllers;

import Me.ChallengeJava.DTOs.UserApplicationDTO;
import Me.ChallengeJava.DTOs.UserDTO;
import Me.ChallengeJava.models.User;
import Me.ChallengeJava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // Obtain all users
    @GetMapping("/api/users")
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(user))
                .collect(Collectors.toList());
    }

    // Create user
    @PostMapping("/api/users")
    public ResponseEntity<Object> createUser(@RequestBody UserApplicationDTO userApplicationDTO){
        // Validation SSN (Social Security Number)
        if(userApplicationDTO.getSsn().isBlank()){
            return new ResponseEntity<>("SSN can't be on blank", HttpStatus.FORBIDDEN);
        }
        // Validation name
        if(userApplicationDTO.getName().isBlank()){
            return new ResponseEntity<>("Name can't be on blank", HttpStatus.FORBIDDEN);
        }
        if(!Pattern.matches("^[a-z A-Z]+$", userApplicationDTO.getName())){
            return new ResponseEntity<>("Name can't contain numbers", HttpStatus.FORBIDDEN);
        }
        // Validation last name
        if(userApplicationDTO.getLastName().isBlank()){
            return new ResponseEntity<>("Last name can't be on blank", HttpStatus.FORBIDDEN);
        }
        if(!Pattern.matches("^[a-z A-Z]+$", userApplicationDTO.getLastName())){
            return new ResponseEntity<>("Last name can't contain numbers", HttpStatus.FORBIDDEN);
        }
        // Validation email
        if(userApplicationDTO.getEmail().isBlank()){
            return new ResponseEntity<>("Email can't be on blank", HttpStatus.FORBIDDEN);
        }
        // Validation phone
        if(userApplicationDTO.getPhone().isBlank()){
            return new ResponseEntity<>("Phone can't be on blank", HttpStatus.FORBIDDEN);
        }else if (!userApplicationDTO.getPhone().matches("\\d+")){
            return new ResponseEntity<>("Phone can't contain letters", HttpStatus.FORBIDDEN);
        }
        // Validation birthday
        if(userApplicationDTO.getBirthday().isBlank()){
            return new ResponseEntity<>("Birthday can't be on blank", HttpStatus.FORBIDDEN);
        }
        // Validation state
        if(userApplicationDTO.getState().isBlank()){
            return new ResponseEntity<>("State can't be on blank", HttpStatus.FORBIDDEN);
        }
        // Validation city
        if(userApplicationDTO.getCity().isBlank()){
            return new ResponseEntity<>("City can't be on blank", HttpStatus.FORBIDDEN);
        }
        // Validation address
        if(userApplicationDTO.getAddress().isBlank()){
            return new ResponseEntity<>("Address can't be on blank", HttpStatus.FORBIDDEN);
        }
        // Validation password
        if(userApplicationDTO.getPassword().isBlank()){
            return new ResponseEntity<>("Password can't be on blank", HttpStatus.FORBIDDEN);
        }
        // Create User
        User newUser = new User(
                userApplicationDTO.getSsn(), // SSN
                userApplicationDTO.getName(), // Name
                userApplicationDTO.getLastName(), // Last name
                userApplicationDTO.getEmail(), // Email
                userApplicationDTO.getPhone(), // Phone
                userApplicationDTO.getBirthday(), // Birthday
                userApplicationDTO.getState(), // State
                userApplicationDTO.getCity(), // City
                userApplicationDTO.getAddress(), // Address
                userApplicationDTO.getPassword()); // Password

        // Save user
        userRepository.save(newUser);

        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }
    // Modify a user
    @PutMapping("/api/users/{id}")
    public ResponseEntity<Object> modifyUser(@PathVariable Long id, @RequestBody UserApplicationDTO userApplicationDTO){
        // User to modify
        User userToModify = userRepository.findById(id).orElse(null);
        // Validation user to modify
        if(userToModify != null){
            // Validation SSN (Social Security Number)
            if(userApplicationDTO.getSsn().isBlank()){
                return new ResponseEntity<>("SSN can't be on blank", HttpStatus.FORBIDDEN);
            }
            // Validation name
            if(userApplicationDTO.getName().isBlank()){
                return new ResponseEntity<>("Name can't be on blank", HttpStatus.FORBIDDEN);
            }
            if(!Pattern.matches("^[a-z A-Z]+$", userApplicationDTO.getName())){
                return new ResponseEntity<>("Name can't contain numbers", HttpStatus.FORBIDDEN);
            }
            // Validation last name
            if(userApplicationDTO.getLastName().isBlank()){
                return new ResponseEntity<>("Last name can't be on blank", HttpStatus.FORBIDDEN);
            }
            if(!Pattern.matches("^[a-z A-Z]+$", userApplicationDTO.getLastName())){
                return new ResponseEntity<>("Last name can't contain numbers", HttpStatus.FORBIDDEN);
            }
            // Validation email
            if(userApplicationDTO.getEmail().isBlank()){
                return new ResponseEntity<>("Email can't be on blank", HttpStatus.FORBIDDEN);
            }
            // Validation phone
            if(userApplicationDTO.getPhone().isBlank()){
                return new ResponseEntity<>("Phone can't be on blank", HttpStatus.FORBIDDEN);
            }else if (!userApplicationDTO.getPhone().matches("\\d+")){
                return new ResponseEntity<>("Phone can't contain letters", HttpStatus.FORBIDDEN);
            }
            // Validation birthday
            if(userApplicationDTO.getBirthday().isBlank()){
                return new ResponseEntity<>("Birthday can't be on blank", HttpStatus.FORBIDDEN);
            }
            // Validation state
            if(userApplicationDTO.getState().isBlank()){
                return new ResponseEntity<>("State can't be on blank", HttpStatus.FORBIDDEN);
            }
            // Validation city
            if(userApplicationDTO.getCity().isBlank()){
                return new ResponseEntity<>("City can't be on blank", HttpStatus.FORBIDDEN);
            }
            // Validation address
            if(userApplicationDTO.getAddress().isBlank()){
                return new ResponseEntity<>("Address can't be on blank", HttpStatus.FORBIDDEN);
            }
            // Validation password
            if(userApplicationDTO.getPassword().isBlank()){
                return new ResponseEntity<>("Password can't be on blank", HttpStatus.FORBIDDEN);
            }
            // Set changes to user
            userToModify.setSsn(userApplicationDTO.getSsn()); // SSN (Social Security Number)
            userToModify.setName(userApplicationDTO.getName()); // Name
            userToModify.setLastName(userApplicationDTO.getLastName()); // Last name
            userToModify.setEmail(userApplicationDTO.getEmail()); // Email
            userToModify.setPhone(userApplicationDTO.getPhone()); // Phone
            userToModify.setBirthday(userApplicationDTO.getBirthday()); // Birthday
            userToModify.setState(userApplicationDTO.getState()); // State
            userToModify.setCity(userApplicationDTO.getCity()); // City
            userToModify.setAddress(userApplicationDTO.getAddress()); // Address
            userToModify.setPassword(userApplicationDTO.getPassword()); // Password
            // Save changes to user
            userRepository.save(userToModify);

            return new ResponseEntity<>("User modified successful", HttpStatus.OK);
        }

        return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
    }
    // Delete a user
    @DeleteMapping("/api/users/{id}")
    public void deleteUser(@PathVariable Long id){
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id ::" + id));
        // Delete user
        userRepository.delete(userToDelete);
    }

}
