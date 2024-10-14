package com.ParcialMutantes.Service;

import com.ParcialMutantes.Entities.Dna;
import com.ParcialMutantes.Repository.DnaRepository;
import com.ParcialMutantes.Validators.DnaValidator;
import com.ParcialMutantes.Dto.StatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DnaService {

    @Autowired
    private DnaRepository dnaRepository;

    public boolean isMutant(String[] dna) {
        // Convierte el array de String en un array bidimensional de caracteres
        char[][] dnaMatrix = new char[dna.length][dna[0].length()];
        for (int i = 0; i < dna.length; i++) {
            dnaMatrix[i] = dna[i].toCharArray(); // Convierte cada string en un array de chars
        }

        boolean isMutant = DnaValidator.isMutant(dnaMatrix); // Modificar el método DnaValidator para aceptar char[][]

        Dna dnaEntity = new Dna();
        dnaEntity.setDna(dna); // Aquí guardas el array de strings original
        dnaEntity.setMutant(isMutant);
        dnaRepository.save(dnaEntity);

        return isMutant;

    }

    public StatsDto getStats() {
        long countMutant = dnaRepository.countByIsMutant(true);
        long countHuman = dnaRepository.countByIsMutant(false);
        double ratio = (countHuman == 0) ? 0 : (double) countMutant / countHuman;

        StatsDto stats = new StatsDto(countMutant,countHuman,ratio);

        return stats;
    }
}
