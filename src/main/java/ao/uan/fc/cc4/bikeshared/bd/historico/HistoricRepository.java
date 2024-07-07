package ao.uan.fc.cc4.bikeshared.bd.historico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricRepository extends JpaRepository<HistoricModel, Long> {
}
