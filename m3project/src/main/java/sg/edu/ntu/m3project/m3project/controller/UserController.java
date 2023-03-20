package sg.edu.ntu.m3project.m3project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import sg.edu.ntu.m3project.m3project.repository.UserRepository;
import sg.edu.ntu.m3project.m3project.entity.UserEntity;
import sg.edu.ntu.m3project.m3project.helper.ResponseMessage;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll() {
        try {
            List<UserEntity> users = (List<UserEntity>)userRepo.findAll();
            return ResponseEntity.ok().body(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable int id) {
        try {
            Optional<UserEntity> optional = userRepo.findById(id);
            if (optional.isPresent()) {
                UserEntity user = optional.get();
                return ResponseEntity.ok().body(user);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Invalid User ID. Please try a different User ID."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody UserEntity user) {
        try {
            UserEntity createNewUser = userRepo.save(user);
            return new ResponseEntity(userRepo.findById(createNewUser.getId()), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody UserEntity user, @PathVariable int id) {
        try {
            Optional<UserEntity> currentUser = userRepo.findById(id);
            if (currentUser.isPresent()) {
                UserEntity editedUser = currentUser.get();
                editedUser.setName(user.getName());
                editedUser.setEmail(user.getEmail());
                editedUser.setPhone(user.getPhone());
                editedUser = userRepo.save(editedUser);
                return ResponseEntity.ok().body(editedUser);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("Invalid User ID. Please try a different User ID."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Something went wrong. Please try again later."));
        }
    }
}