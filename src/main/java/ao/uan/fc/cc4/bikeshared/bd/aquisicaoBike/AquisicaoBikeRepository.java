package ao.uan.fc.cc4.bikeshared.bd.aquisicaoBike;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AquisicaoBikeRepository extends JpaRepository<AquisicaoBikeModel, Long> {

    List<AquisicaoBikeModel> findByCiclistaId (Long ciclistaId);

}
