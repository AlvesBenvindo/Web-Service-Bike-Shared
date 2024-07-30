package ao.uan.fc.cc4.bikeshared.bd.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionModel, Long> {

    SessionModel findByToken(String token);
    SessionModel findByFingerprint(String fingerprint);
    SessionModel findByUser(Long user);

}
