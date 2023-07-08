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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
    }
    // Create a new inscription
    @PostMapping("/api/inscriptions")
    public ResponseEntity<Object> createInscription(@RequestBody InscriptionApplicationDTO inscriptionApplicationDTO){
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

    @PutMapping("/api/inscriptions/{id}")
    public ResponseEntity<Object> modifyInscription(@PathVariable Long id, @RequestBody Inscription inscription){

    }
}
