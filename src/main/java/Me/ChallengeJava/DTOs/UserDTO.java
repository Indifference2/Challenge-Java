package Me.ChallengeJava.DTOs;

import Me.ChallengeJava.models.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String ssn;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String birthday;
    private String state;
    private String city;
    private String address;
    private Set<InscriptionDTO> inscriptions;
    public UserDTO(User user){
        this.id = user.getId();
        this.ssn = user.getSsn();
        this.name = user.getName();
        this.lastname = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.birthday = user.getBirthday();
        this.state = user.getState();
        this.city = user.getCity();
        this.address = user.getAddress();
        this.inscriptions = user.getInscriptions().stream().map(inscription -> new InscriptionDTO(inscription)).collect(Collectors.toSet());
    }

    /* GETTERS */

    public long getId() {return id;}
    public String getSsn() {return ssn;}
    public String getName() {return name;}
    public String getLastname() {return lastname;}
    public String getEmail() {return email;}
    public String getPhone() {return phone;}
    public String getBirthday() {return birthday;}
    public String getState() {return state;}
    public String getCity() {return city;}
    public String getAddress() {return address;}
    public Set<InscriptionDTO> getInscriptions() {return inscriptions;}
}
