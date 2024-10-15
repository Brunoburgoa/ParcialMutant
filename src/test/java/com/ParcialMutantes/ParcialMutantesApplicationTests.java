package com.ParcialMutantes;

import com.ParcialMutantes.Dto.DnaDto;
import com.ParcialMutantes.Dto.StatsDto;
import com.ParcialMutantes.Repository.DnaRepository;
import com.ParcialMutantes.Service.DnaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ParcialMutantesApplicationTests {

	@Mock
	private DnaRepository dnaRepository;

	@InjectMocks
	private DnaService dnaService;

	@Test
	public void testIsMutantHorizontal() {
		DnaDto dnaDto = new DnaDto();
		dnaDto.setDna(new String[]{"TTGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});
		boolean isMutant = dnaService.isMutant(dnaDto.getDna());  // Retorna boolean
		assertEquals(true, isMutant);
	}

	@Test
	public void testIsMutantVertical() {
		DnaDto dnaDto = new DnaDto();
		dnaDto.setDna(new String[]{"TTGCGA", "TAGTGC", "TTATGT", "TGAAGG", "CGCCTA", "TCACTG"});
		boolean isMutant = dnaService.isMutant(dnaDto.getDna());  // Retorna boolean
		assertEquals(true, isMutant);
	}

	@Test
	public void testIsMutantDiagonal() {
		DnaDto dnaDto = new DnaDto();
		dnaDto.setDna(new String[]{"ATGCGA","CAGTTC","TTATGT","AGAATG","CGCTTA", "TCTCTG"});
		boolean isMutant = dnaService.isMutant(dnaDto.getDna());  // Retorna boolean
		assertEquals(true, isMutant);
	}

	@Test
	public void testIsNotMutant() {
		DnaDto dnaDto = new DnaDto();
		dnaDto.setDna(new String[]{"TTGCGA", "CAGTCC", "TTATGT", "AGAAGG", "CGCCTA", "TCACTG"});
		boolean isMutant = dnaService.isMutant(dnaDto.getDna());  // Retorna boolean
		assertEquals(false, isMutant);
	}

	@Test
	public void testGetStats() {
		when(dnaRepository.countByIsMutant(true)).thenReturn(1L);
		when(dnaRepository.countByIsMutant(false)).thenReturn(1L);

		StatsDto stats = dnaService.getStats();

		assertEquals(1, stats.getCountMutantDna());
		assertEquals(1, stats.getCountHumanDna());
		assertEquals(1.0, stats.getRatio());
	}
}
