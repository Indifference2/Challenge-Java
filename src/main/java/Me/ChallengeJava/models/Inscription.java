package Me.ChallengeJava.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;
    private LocalDateTime dateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;
    public Inscription() {
    }

    /* GETTERS */
    public Long getId() {return id;}
    public LocalDateTime getDateTime() {return dateTime;}
    public Person getPerson() {return person;}
    public Course getCourse() {return course;}

    /* SETTERS */
    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}
    public void setPerson(Person person) {this.person = person;}
    public void setCourse(Course course) {this.course = course;}
}
