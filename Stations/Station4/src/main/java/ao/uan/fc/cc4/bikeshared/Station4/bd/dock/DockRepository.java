package ao.uan.fc.cc4.bikeshared.Station4.bd.dock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DockRepository extends JpaRepository<DockModel, Long> {

    DockModel findByReference (String reference);

}
