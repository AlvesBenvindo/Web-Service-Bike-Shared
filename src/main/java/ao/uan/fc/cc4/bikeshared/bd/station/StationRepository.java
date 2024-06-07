package ao.uan.fc.cc4.bikeshared.bd.station;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<StationModel, Long> {

}
