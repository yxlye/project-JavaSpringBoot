package sg.edu.ntu.m3project.m3project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.m3project.m3project.entity.ConcertEntity;
import sg.edu.ntu.m3project.m3project.service.ConcertService;

@CrossOrigin
@RestController
@RequestMapping("/concerts")
public class ConcertController {

    @Autowired
    ConcertService concertService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ConcertEntity>> findAllAvailable() {

        return new ResponseEntity<List<ConcertEntity>>(
                concertService.find("upcoming", ""),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResponseEntity<List<ConcertEntity>> findAll() {

        return new ResponseEntity<List<ConcertEntity>>(
                concertService.find("history", ""),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<ConcertEntity>> search(@RequestParam String artist) {

        return new ResponseEntity<List<ConcertEntity>>(
                concertService.find("artist", artist.toUpperCase()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/search/{concertId}", method = RequestMethod.GET)
    public ResponseEntity<ConcertEntity> findById(@PathVariable int concertId) {

        return new ResponseEntity<ConcertEntity>(
                concertService.findbyConcertId(concertId),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseEntity<ConcertEntity> create(
            @RequestHeader("token") String token,
            @RequestHeader("user-id") int userId,
            @RequestBody ConcertEntity concert) {

        return new ResponseEntity<ConcertEntity>(concertService.create(token, userId, concert), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/edit/{concertId}", method = RequestMethod.PUT)
    public ResponseEntity<ConcertEntity> update(
            @RequestHeader("user-id") int userId,
            @RequestBody ConcertEntity concert,
            @PathVariable int concertId) {

        return new ResponseEntity<ConcertEntity>(
                concertService.update(userId, concert, concertId),
                HttpStatus.OK);
    }

}
