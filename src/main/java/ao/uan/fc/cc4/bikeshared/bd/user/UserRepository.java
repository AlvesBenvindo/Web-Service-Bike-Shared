package ao.uan.fc.cc4.bikeshared.bd.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByEmail ( String email );
    UserModel findByNome ( String nome );

}
