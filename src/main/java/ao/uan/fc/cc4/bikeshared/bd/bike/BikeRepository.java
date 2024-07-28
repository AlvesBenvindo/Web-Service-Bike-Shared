package ao.uan.fc.cc4.bikeshared.bd.bike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<BikeModel, Long> {
    
}
