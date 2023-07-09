package Me.ChallengeJava;

import Me.ChallengeJava.models.User;
import Me.ChallengeJava.repositories.CourseRepository;
import Me.ChallengeJava.repositories.InscriptionRepository;
import Me.ChallengeJava.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChallengeJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeJavaApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(UserRepository userRepository, InscriptionRepository inscriptionRepository, CourseRepository courseRepository){
		return (args -> {
			// User 1
			User user1 = new User(
					"001-01-5554",
					"Michael",
					"Jordan",
					"michaeljordan@gmail.com",
					"843-444-1525",
					"03/16/1995",
					"New York",
					"Mount Vernon",
					"326 Kennedy Dr",
					"basketball");
			// Save user 1
			userRepository.save(user1);

			// User 2
			User user2 = new User(
					"001-01-5554",
					"Paul",
					"Aaron",
					"aaronpaul@gmail.com",
					"561-249-0865",
					"15/05/1987",
					"Florida",
					"Miami",
					"360 NW 98th St",
					"breakingbad");
			// Save user 2
			userRepository.save(user2);
		});
	}
}
