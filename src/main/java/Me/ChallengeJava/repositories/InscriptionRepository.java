package Me.ChallengeJava.repositories;

import Me.ChallengeJava.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
}
