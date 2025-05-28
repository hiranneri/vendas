package br.com.mesadigital.vendas.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    public static final DateTimeFormatter PATTERN_EN_US = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");


    /**
     * Retorna a data e hora no formata PT_BR
     * @param dataHora 05/10/2025 10:10:12
     * @return dataEHora
     */
    public static String toPTBR(LocalDateTime dataHora) {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    /**
     * Retorna a data e hora no formato EN_US
     * @param dataHora 05-10-2025T10:10:12
     * @return dataEHora
     */
    public static String toEN_US(LocalDateTime dataHora) {
        return dataHora.format(PATTERN_EN_US);
    }
}
