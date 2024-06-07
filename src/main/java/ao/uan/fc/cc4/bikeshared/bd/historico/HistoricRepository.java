package ao.uan.fc.cc4.bikeshared.bd.historico;

import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricRepository extends JpaRepository<HistoricModel, Long> {
}
