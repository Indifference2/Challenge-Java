package Me.ChallengeJava.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private Long id;
    private LocalDateTime dateTime;
    private Shift shift;
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
    public Shift getShift() {return shift;}

    /* SETTERS */
    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}
    public void setUser(User user) {this.user = user;}
    public void setCourse(Course course) {this.course = course;}
    public void setShift(Shift shift) {this.shift = shift;}
}
