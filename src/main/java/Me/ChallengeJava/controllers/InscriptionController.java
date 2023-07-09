package Me.ChallengeJava.controllers;

import Me.ChallengeJava.DTOs.InscriptionApplicationDTO;
import Me.ChallengeJava.DTOs.InscriptionDTO;
import Me.ChallengeJava.models.Course;
import Me.ChallengeJava.models.Inscription;
import Me.ChallengeJava.models.User;
import Me.ChallengeJava.repositories.CourseRepository;
import Me.ChallengeJava.repositories.InscriptionRepository;
import Me.ChallengeJava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class InscriptionController {
    @Autowired
    private InscriptionRepository inscriptionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    // Get all inscriptions
    @GetMapping("/api/inscriptions")
    public List<InscriptionDTO> getInscriptions(){
        return inscriptionRepository.findAll()
                .stream()
                .map(inscription -> new InscriptionDTO(inscription))
                .collect(toList());
    }
    // Get current inscriptions
    @GetMapping("/api/inscriptions/current")
    public List<InscriptionDTO> getCurrentInscriptions(Authentication authentication){
        // Get user authenticated
        User userAuthenticated = userRepository.findByEmail(authentication.getName());
        // Return all inscriptions user
        return userAuthenticated.getInscriptions()
                .stream()
                .map(inscription -> new InscriptionDTO(inscription))
                .collect(toList());
    }
    // Create a new inscription
    @PostMapping("/api/inscriptions")
    public ResponseEntity<Object> createInscription(@RequestBody InscriptionApplicationDTO inscriptionApplicationDTO, Authentication authentication){
        // Get user to enroll
        User userToEnroll = userRepository.findById(inscriptionApplicationDTO.getUser_id()).orElse(null);
        // Get course to enroll user
        Course courseToEnroll = courseRepository.findById(inscriptionApplicationDTO.getCourse_id()).orElse(null);
        // Validation User
        if(userToEnroll == null){
            return new ResponseEntity<>("User doesn't exist", HttpStatus.FORBIDDEN);
        }
        // Validation course
        if(courseToEnroll == null){
            return new ResponseEntity<>("Course doesn't exist", HttpStatus.FORBIDDEN);
        }
        // Validation shift
        if(!inscriptionApplicationDTO.getShift().name().equalsIgnoreCase("MORNING") &&
                !inscriptionApplicationDTO.getShift().name().equalsIgnoreCase("AFTERNOON") &&
                !inscriptionApplicationDTO.getShift().name().equalsIgnoreCase("NIGHT")){
            return new ResponseEntity<>("Wrong shift, the shift available are 'MORNING', 'AFTERNOON' and 'NIGHT'", HttpStatus.FORBIDDEN);
        }
        // Create a new inscription
        Inscription newInscription = new Inscription();
        // Set Shift
        newInscription.setShift(inscriptionApplicationDTO.getShift());
        // Set LocalDateTime
        newInscription.setDateTime(LocalDateTime.now());
        // Save inscription
        inscriptionRepository.save(newInscription);
        // Add user to inscription
        userToEnroll.addInscription(newInscription);
        // Save user
        userRepository.save(userToEnroll);
        // Add course to inscription
        courseToEnroll.addInscription(newInscription);
        // Save course
        courseRepository.save(courseToEnroll);

        return new ResponseEntity<>("Inscription successfully", HttpStatus.CREATED);
    }
    // Modify an inscription
    @PutMapping("/api/inscriptions/{id}")
    public ResponseEntity<Object> modifyInscription(@PathVariable Long id, @RequestBody InscriptionApplicationDTO inscriptionApplicationDTO){
        // Get inscription
        Inscription inscriptionToModify = inscriptionRepository.findById(id).orElse(null);
        // Get User
        User userToEnroll = userRepository.findById(inscriptionApplicationDTO.getUser_id()).orElse(null);
        // Get Course
        Course courseToEnroll = courseRepository.findById(inscriptionApplicationDTO.getCourse_id()).orElse(null);
        // Validation inscription
        if(inscriptionToModify == null){
            return new ResponseEntity<>("Inscription doesn't exist", HttpStatus.FORBIDDEN);
        }
        // Validation User
        if(userToEnroll == null){
            return new ResponseEntity<>("User doesn't exist", HttpStatus.FORBIDDEN);
        }
        // Validation course
        if(courseToEnroll == null){
            return new ResponseEntity<>("Course doesn't exist", HttpStatus.FORBIDDEN);
        }
        // Validation shift
        if(!inscriptionApplicationDTO.getShift().name().equalsIgnoreCase("MORNING") &&
                !inscriptionApplicationDTO.getShift().name().equalsIgnoreCase("AFTERNOON") &&
                !inscriptionApplicationDTO.getShift().name().equalsIgnoreCase("NIGHT")){
            return new ResponseEntity<>("Wrong shift, the shift available are 'MORNING', 'AFTERNOON' and 'NIGHT'", HttpStatus.FORBIDDEN);
        }
        // Set changes
        inscriptionToModify.setUser(userToEnroll); // User
        inscriptionToModify.setCourse(courseToEnroll); // Course
        inscriptionToModify.setShift(inscriptionApplicationDTO.getShift()); // Shift

        return new ResponseEntity<>("Inscription modified successfully", HttpStatus.ACCEPTED);
    }
    // Delete an inscription
    @DeleteMapping("/api/inscriptions/{id}")
    public ResponseEntity<?> deleteInscription(@PathVariable Long id, Authentication authentication){
        // Get inscription to delete
        Inscription inscriptionToDelete = inscriptionRepository.findById(id).orElse(null);
        // Get user request petition
        User userRequest = userRepository.findByEmail(authentication.getName());
        // Get rol
        String rol = authentication.getAuthorities().stream().collect(toList()).get(0).toString();
        // Get inscriptions user authenticated
        List<Inscription> inscriptionsUserAuthenticated = userRequest.getInscriptions().stream().collect(toList());
        // Verification inscription to delete
        if(inscriptionToDelete == null) {
            return new ResponseEntity<>("Inscription doesn't exist", HttpStatus.FORBIDDEN);
        }
        // Verification ROL ADMIN
        if(!rol.equals("ADMIN")){
            // Verification others ROL
                if(inscriptionsUserAuthenticated.stream().noneMatch(inscription -> inscription.getId().equals(inscriptionToDelete.getId()))){
                    return new ResponseEntity<>("This inscription doesn't belong you", HttpStatus.FORBIDDEN);
                }
                // Delete inscription
                inscriptionRepository.delete(inscriptionToDelete);

                return new ResponseEntity<>("Inscription deleted successfully", HttpStatus.ACCEPTED);
        }
        // Delete inscription
        inscriptionRepository.delete(inscriptionToDelete);

        return new ResponseEntity<>("Inscription deleted successfully", HttpStatus.ACCEPTED);
    }

}
