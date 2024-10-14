package com.ParcialMutantes.Controllers;

import com.ParcialMutantes.Dto.StatsDto;
import com.ParcialMutantes.Service.DnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private DnaService mutantService;

    @GetMapping
    public ResponseEntity<StatsDto> getStats() {
        StatsDto stats = mutantService.getStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}

