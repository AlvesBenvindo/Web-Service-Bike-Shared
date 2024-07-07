package ao.uan.fc.cc4.bikeshared.bd.ciclista;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiclistaRepository extends JpaRepository<CiclistaModel, Long> {
    CiclistaModel findByUserId(Long userId);
}
