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
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;
    public Inscription() {
    }

    /* GETTERS */
    public Long getId() {return id;}
    public LocalDateTime getDateTime() {return dateTime;}
    public User getUser() {return user;}
    public Course getCourse() {return course;}

    /* SETTERS */
    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}
    public void setUser(User user) {this.user = user;}
    public void setCourse(Course course) {this.course = course;}
}
