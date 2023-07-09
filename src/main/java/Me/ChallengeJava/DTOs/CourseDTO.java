package Me.ChallengeJava.DTOs;

import Me.ChallengeJava.models.Course;
import Me.ChallengeJava.models.Shift;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private Set<Shift> shifts;
    private String imageUrl;
    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
        this.shifts = course.getShifts().stream().collect(toSet());
        this.imageUrl = course.getImageUrl();
    }

    /* GETTERS */
    public Long getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public Set<Shift> getShifts() {return shifts;}
    public String getImageUrl() {return imageUrl;}
}
