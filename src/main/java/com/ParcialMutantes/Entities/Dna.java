package com.ParcialMutantes.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Data
public class Dna implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Dna_Sequence", unique = true)
    private String[] dna;

    private boolean isMutant;


}
