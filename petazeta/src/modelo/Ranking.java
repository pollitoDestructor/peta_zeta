package modelo;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

public class Ranking {
    private static Ranking miRanking = new Ranking();
    private Map<String, Integer> listaPuntuaciones;
    private static final String ARCHIVO_RANKING = "ranking.txt";

    private Ranking() {
        listaPuntuaciones = new HashMap<>();
        cargarRankingDesdeArchivo(); // Cargar datos guardados al iniciar
    }
    public static Ranking getRanking() {
        return miRanking;
    }

    public void anadirJugador(String color, int puntuacion) {
        // Verificar si el jugador ya está en el ranking
        if (listaPuntuaciones.containsKey(color)) {
            int puntuacionRanking = listaPuntuaciones.get(color);

            if (puntuacion > puntuacionRanking) { // Solo actualiza si es mayor
                listaPuntuaciones.put(color, puntuacion);
                System.out.println("¡NEW RECORD! " + color + " -> " + puntuacion + " puntos");
                guardarRankingEnArchivo(); // Guardar cambios
            }
        } else {
            // Si el jugador no está en el ranking, lo añade por primera vez
            listaPuntuaciones.put(color, puntuacion);
            guardarRankingEnArchivo(); // Guardar cambios
        }
    }

    public void eliminarJugador(String color) {
        listaPuntuaciones.remove(color);
        guardarRankingEnArchivo();
    }

    public Map<String, Integer> obtenerRankingOrdenado() {
        return listaPuntuaciones.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue())) // Orden descendente
                .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), Map::putAll);
    }


    // Guardar ranking en un archivo de texto
    private void guardarRankingEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_RANKING))) {
            for (Map.Entry<String, Integer> entry : listaPuntuaciones.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el ranking: " + e.getMessage());
        }
    }

    // Cargar ranking desde un archivo de texto
    private void cargarRankingDesdeArchivo() {
        listaPuntuaciones.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_RANKING))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String nombre = partes[0];
                    int puntuacion = Integer.parseInt(partes[1]);
                    listaPuntuaciones.put(nombre, puntuacion);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo de ranking. Se creará uno nuevo.");
        }
    }
}
