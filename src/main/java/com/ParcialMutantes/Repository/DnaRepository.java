package com.ParcialMutantes.Repository;

import com.ParcialMutantes.Entities.Dna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DnaRepository extends JpaRepository<Dna, Long> {
    boolean existsByDna(String[] dna);

    long countByIsMutant(boolean b);
}

