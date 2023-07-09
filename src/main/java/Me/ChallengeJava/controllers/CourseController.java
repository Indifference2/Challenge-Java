package Me.ChallengeJava.controllers;

import Me.ChallengeJava.DTOs.CourseApplicationDTO;
import Me.ChallengeJava.DTOs.CourseDTO;
import Me.ChallengeJava.models.Course;
import Me.ChallengeJava.models.Shift;
import Me.ChallengeJava.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
    // Obtain all courses
    @GetMapping("/api/courses")
    public List<CourseDTO> getAllCourses(){
        return courseRepository.findAll()
                .stream()
                .map(course -> new CourseDTO(course))
                .collect(toList());
    }
    // Create a new course
    @PostMapping("/api/courses")
    public ResponseEntity<Object> createCourse(@RequestBody CourseApplicationDTO courseApplicationDTO){
        if(courseApplicationDTO.getName().isBlank()){
            return new ResponseEntity<>("Name can't be on blank", HttpStatus.FORBIDDEN);
        }
        if(courseApplicationDTO.getDescription().isBlank()){
            return new ResponseEntity<>("Description can't be on blank", HttpStatus.FORBIDDEN);
        }
        if(courseApplicationDTO.getShifts().isEmpty()){
            return new ResponseEntity<>("Shifts can't be empty", HttpStatus.FORBIDDEN);
        }
        // Create new course
        Course newCourse = new Course(
                courseApplicationDTO.getName(), // Name
                courseApplicationDTO.getDescription(), // Description
                courseApplicationDTO.getShifts().stream().collect(toList())); // Shifts
        // Save new course
        courseRepository.save(newCourse);

        return new ResponseEntity<>("Course created successfully", HttpStatus.CREATED);
    }
    // Modify a course
    @PutMapping("/api/courses/{id}")
    public ResponseEntity<Object> modifyCourse(@PathVariable Long id, @RequestBody CourseApplicationDTO courseApplicationDTO){
        Course courseToModify = courseRepository.findById(id).orElse(null);
        // Validation course
        if(courseToModify != null){
            // Validation name
            if(courseApplicationDTO.getName().isBlank()){
                return new ResponseEntity<>("Name can't be on blank", HttpStatus.FORBIDDEN);
            }
            // Validation description
            if(courseApplicationDTO.getDescription().isBlank()){
                return new ResponseEntity<>("Description can't be on blank", HttpStatus.FORBIDDEN);
            }
            // Validation shifts
            if(courseApplicationDTO.getShifts().isEmpty()){
                return new ResponseEntity<>("Shifts can't be on blank", HttpStatus.FORBIDDEN);
            }
            for(Shift shift : courseApplicationDTO.getShifts()){
                if(!shift.name().equalsIgnoreCase("MORNING") &&
                        !shift.name().equalsIgnoreCase("AFTERNOON") &&
                        !shift.name().equalsIgnoreCase("NIGHT")){
                    return new ResponseEntity<>("Wrong shift, the shift availables are 'MORRNING', 'AFTERNOON' and 'NIGHT'", HttpStatus.FORBIDDEN);
                }
            }
            // Set changes
            courseToModify.setName(courseApplicationDTO.getName()); // Name
            courseToModify.setDescription(courseApplicationDTO.getDescription()); // Description
            courseToModify.setShifts(courseApplicationDTO.getShifts().stream().collect(toList())); // Shifts
            // Save course
            courseRepository.save(courseToModify);

            return new ResponseEntity<>("Course modify successfully", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Course doesn't exist", HttpStatus.FORBIDDEN);
    }
    @DeleteMapping("/api/courses/{id}")
    public void deleteCourse(@PathVariable Long id){
        Course courseToDelete = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found for this id ::" + id));
        // Delete user
        courseRepository.delete(courseToDelete);
    }
}
