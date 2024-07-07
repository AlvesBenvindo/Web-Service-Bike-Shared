package ao.uan.fc.cc4.bikeshared.bd.aquisicaoBike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AquisicaoBikeRepository extends JpaRepository<AquisicaoBikeModel, Long> {

}
