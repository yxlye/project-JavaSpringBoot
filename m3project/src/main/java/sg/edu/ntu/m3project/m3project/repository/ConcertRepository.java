package sg.edu.ntu.m3project.m3project.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;

@Repository
public interface ConcertRepository extends CrudRepository<ConcertEntity, Integer> {
    List<ConcertEntity> findByConcertDateAfter(Timestamp concertDate);

    List<ConcertEntity> findByArtist(String artist);

    List<ConcertEntity> findByArtistContaining(String artist);
}
