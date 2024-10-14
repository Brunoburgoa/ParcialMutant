package com.ParcialMutantes.Controllers;


import com.ParcialMutantes.Dto.DnaDto;
import com.ParcialMutantes.Repository.DnaRepository;
import com.ParcialMutantes.Service.DnaService;
import com.ParcialMutantes.Validators.DnaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant")
public class DnaController {

    @Autowired
    private DnaService dnaService;
    @Autowired
    private DnaRepository dnaRepository;
    @PostMapping("/post")
    public ResponseEntity<String> isMutant(@RequestBody DnaDto dnaDto) {
        try {
            String[] dna = dnaDto.getDna();

            // Validar si es una matriz NxN
            if (!DnaValidator.isValidNxNMatrix(dna)) {
                return ResponseEntity.badRequest().body("El DNA no es una matriz NxN.");
            }

            // Validar si contiene solo las letras A, C, G, T
            if (!DnaValidator.isValidDnaLetters(dna)) {
                return ResponseEntity.badRequest().body("El DNA contiene letras no válidas.");
            }

            // Verificar si el DNA ya existe
            if (dnaRepository.existsByDna(dna)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El DNA ya ha sido ingresado anteriormente.");
            }

            // Continuar con el resto de la lógica
            if (dnaService.isMutant(dna)) {
                return ResponseEntity.ok("Es mutante");
            } else {
                return ResponseEntity.badRequest().body("No es mutante");
            }
        } catch (IllegalArgumentException e) {
            // Si el ADN tiene caracteres inválidos, devolvemos un error 400 con el mensaje
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
