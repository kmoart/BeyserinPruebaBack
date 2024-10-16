package com.example.TestIntegration.controller;


import com.example.TestIntegration.dto.User;
import com.example.TestIntegration.exception.BusinessException;
import com.example.TestIntegration.exception.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    List<User> users = List.of( new User("23445322","C","Juan","Camilo","López","Cortes","6012345678","kra 100 no 50-70","Bogotá D.C"),
            new User("1039454860","P","Santiago","Esteban","Amaya","Torres","6019475738","Calle 11 no 7-70","Bogotá D.C"));

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}/{idType}")
    public ResponseEntity<User> findUserById(@PathVariable String id,@PathVariable String idType){

            User userT = new User("","","","","","","","","");

        if(id.isEmpty() || id == null){
            throw new RequestException("400","id is required");
        }

        if(idType.isEmpty() || idType == null){
            throw new RequestException("400","idType is required");
        }

        for(User user: this.users){

            if(user.getId().equals(id) && user.getIdType().equals(idType)){
                       userT = user;
            }
        }

        return new ResponseEntity<>(userT,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveUser(@RequestBody User user){
        if(user.getId().isEmpty() || user.getId() == null){
          throw new RequestException("P-401","id is required");
        }
        if(user.getIdType().isEmpty() || user.getIdType() == null){
            throw new RequestException("P-402","idType is required");
        }

        if(user.getId().equals("23445322")){
            throw new BusinessException("P-300", HttpStatus.INTERNAL_SERVER_ERROR, "id already exist.");
        }

        if(user.getId().equals("1039454860")){
            throw new BusinessException("P-300", HttpStatus.INTERNAL_SERVER_ERROR, "id already exist.");
        }
        System.out.println("--UserController:saveUser --request" +user.toString());
        return  new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable("id") String id, @RequestBody User user){
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
