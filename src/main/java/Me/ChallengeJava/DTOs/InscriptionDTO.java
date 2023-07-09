package Me.ChallengeJava.DTOs;

import Me.ChallengeJava.models.Inscription;
import Me.ChallengeJava.models.Shift;

import java.time.LocalDateTime;

public class InscriptionDTO {
    private Long id;
    private LocalDateTime dateTime;
    private Shift shift;
    private UserDTO userDTO;
    private CourseDTO courseDTO;
    public InscriptionDTO(Inscription inscription){
        this.id = inscription.getId();
        this.dateTime = inscription.getDateTime();
        this.userDTO = new UserDTO(inscription.getUser());
        this.courseDTO = new CourseDTO(inscription.getCourse());
        this.shift = inscription.getShift();
    }

    /* GETTERS */
    public Long getId() {return id;}
    public LocalDateTime getDateTime() {return dateTime;}
    public UserDTO getUserDTO() {return userDTO;}
    public CourseDTO getCourseDTO() {return courseDTO;}
    public Shift getShift() {return shift;}
}
