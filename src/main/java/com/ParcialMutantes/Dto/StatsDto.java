package com.ParcialMutantes.Dto;

import lombok.Data;

@Data
public class StatsDto {
    private long countMutantDna;
    private long countHumanDna;
    private double ratio;

    public StatsDto(long countMutant, long countHuman, double ratio) {
    }
}
