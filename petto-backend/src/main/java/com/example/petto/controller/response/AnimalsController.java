package com.example.petto.controller.response;

import com.example.petto.entity.Animals;
import com.example.petto.service.AnimalsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/petto/animals")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class AnimalsController {

    @Autowired
    private AnimalsService animalsService;

    @GetMapping("/lists")
    public ResponseEntity<List<Animals>> getLists () throws Exception {
        log.info("getLists(): " + animalsService.list());
        return new ResponseEntity<>(animalsService.list(), HttpStatus.OK);
    }

}


