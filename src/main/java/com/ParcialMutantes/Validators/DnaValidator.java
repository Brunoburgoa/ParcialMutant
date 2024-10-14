package com.ParcialMutantes.Validators;

public class DnaValidator {

    public static boolean isMutant(char[][] dna) {
        int n = dna.length;
        int count = 0;

        // Verificar secuencias horizontales
        for (int i = 0; i < n; i++) {
            count += countSequences(new String(dna[i]));
        }

        // Verificar secuencias verticales
        for (int col = 0; col < n; col++) {
            StringBuilder sb = new StringBuilder();
            for (int row = 0; row < n; row++) {
                sb.append(dna[row][col]);
            }
            count += countSequences(sb.toString());
        }

        // Verificar secuencias diagonales (top-left to bottom-right)
        for (int i = 0; i < n; i++) {
            StringBuilder sb1 = new StringBuilder();
            for (int j = 0; j < n - i; j++) {
                sb1.append(dna[j + i][j]);
            }
            count += countSequences(sb1.toString());
        }

        for (int i = 1; i < n; i++) {
            StringBuilder sb2 = new StringBuilder();
            for (int j = 0; j < n - i; j++) {
                sb2.append(dna[j][j + i]);
            }
            count += countSequences(sb2.toString());
        }

        // Verificar secuencias diagonales (top-right to bottom-left)
        for (int i = 0; i < n; i++) {
            StringBuilder sb1 = new StringBuilder();
            for (int j = 0; j < n - i; j++) {
                sb1.append(dna[j][n - j - 1 - i]);
            }
            count += countSequences(sb1.toString());
        }

        for (int i = 1; i < n; i++) {
            StringBuilder sb2 = new StringBuilder();
            for (int j = 0; j < n - i; j++) {
                sb2.append(dna[j + i][n - j - 1]);
            }
            count += countSequences(sb2.toString());
        }

        return count > 1;
    }

    private static int countSequences(String sequence) {
        int count = 0;
        for (int i = 0; i < sequence.length() - 3; i++) {
            if (sequence.charAt(i) == sequence.charAt(i + 1) &&
                    sequence.charAt(i) == sequence.charAt(i + 2) &&
                    sequence.charAt(i) == sequence.charAt(i + 3)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isValidDnaLetters(String[] dna) {
        String validCharacters = "ACGT";
        for (String row : dna) {
            for (char c : row.toCharArray()) {
                if (validCharacters.indexOf(c) == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidNxNMatrix(String[] dna) {
        int n = dna.length;
        for (String row : dna) {
            if (row.length() != n) {
                return false;
            }
        }
        return true;
    }
}
