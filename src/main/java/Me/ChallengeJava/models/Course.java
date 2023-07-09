package Me.ChallengeJava.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    @ElementCollection
    private List<Shift> shifts = new ArrayList<>();
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<Inscription> inscriptions = new HashSet<>();
    public Course() {
    }
    public Course(String name, String description, List<Shift> shifts, String imageUrl) {
        this.name = name;
        this.description = description;
        this.shifts = shifts;
        this.imageUrl = imageUrl;
    }
    public void addInscription(Inscription inscription){
        inscription.setCourse(this);
        inscriptions.add(inscription);
    }

    /* GETTERS */
    public Long getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public List<Shift> getShifts() {return shifts;}
    public Set<Inscription> getInscriptions() {return inscriptions;}
    public String getImageUrl() {return imageUrl;}

    /* SETTERS */
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setShifts(List<Shift> shifts) {this.shifts = shifts;}
    public void setInscriptions(Set<Inscription> inscriptions) {this.inscriptions = inscriptions;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
}
