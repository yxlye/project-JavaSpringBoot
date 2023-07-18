package sg.edu.ntu.m3project.m3project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.m3project.m3project.repository.SeatRepository;

@CrossOrigin
@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    SeatRepository seatRepo;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(seatRepo.findAll());
    }

}
