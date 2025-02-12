package sg.edu.ntu.m3project.m3project.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.exceptions.ConcertNotFoundException;
import sg.edu.ntu.m3project.m3project.repository.ConcertRepository;
import sg.edu.ntu.m3project.m3project.repository.UserRepository;

@Service
public class ConcertService {

    @Autowired
    ConcertRepository concertRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserService userService;

    public List<ConcertEntity> find(String findBy, String searchParam) {

        List<ConcertEntity> currentConcertList;

        switch (findBy) {
            case "upcoming":
                // find all upcoming

                Timestamp currentDatenTime = new Timestamp(new Date().getTime());
                currentConcertList = (List<ConcertEntity>) concertRepo
                        .findByConcertDateAfter(currentDatenTime);

                break;

            case "artist":
                // find by artist
                currentConcertList = (List<ConcertEntity>) concertRepo.findByArtist(searchParam);
                if (currentConcertList.size() == 0) {
                    currentConcertList = (List<ConcertEntity>) concertRepo.findByArtistContaining(searchParam);
                }

                break;

            case "history":
                // find all past and upcoming
            default:
                currentConcertList = (List<ConcertEntity>) concertRepo.findAll();
                break;
        }

        if (currentConcertList.size() > 0) {

            return currentConcertList;
        }

        throw new ConcertNotFoundException("No upcoming concerts");
    }

    public ConcertEntity findbyConcertId(int concertId) {

        Optional<ConcertEntity> optionalConcert = concertRepo.findById(concertId);

        if (optionalConcert.isPresent()) {
            return optionalConcert.get();

        }

        throw new ConcertNotFoundException("Invalid concert id.");
    }

    public ConcertEntity create(String token, int userId, ConcertEntity concert) {
        userService.checkAdmin(userId);
        ConcertEntity newConcert = concertRepo.save(concert);

        Optional<ConcertEntity> optionalConcert = concertRepo.findById(newConcert.getId());
        if (optionalConcert.isPresent()) {
            return optionalConcert.get();
        }
        throw new ConcertNotFoundException("Error encountered to retrieve concert created");
    }

    public ConcertEntity update(int userId, ConcertEntity concert, int concertId) {
        userService.checkAdmin(userId);
        Optional<ConcertEntity> optionalConcert = concertRepo.findById(concertId);

        if (optionalConcert.isPresent()) {
            ConcertEntity selectedConcert = optionalConcert.get();

            Timestamp updatedAt = new Timestamp(new Date().getTime());

            selectedConcert.setArtist(concert.getArtist());
            selectedConcert.setConcertDate(concert.getConcertDate());
            selectedConcert.setTicketPrice(concert.getTicketPrice());
            selectedConcert.setTicketsAvailable(concert.getTicketsAvailable());
            selectedConcert.setUpdatedAt(updatedAt);

            concertRepo.save(selectedConcert);
            return selectedConcert;
        }

        throw new ConcertNotFoundException("Concert to be edited does not exist");

    }

}
