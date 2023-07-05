package Me.ChallengeJava.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;
    private String name;
    private String description;
    private List<Shift> shifts = new ArrayList<>();

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /* GETTERS */
    public Long getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public List<Shift> getShifts() {return shifts;}

    /* SETTERS */
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setShifts(List<Shift> shifts) {this.shifts = shifts;}
}
