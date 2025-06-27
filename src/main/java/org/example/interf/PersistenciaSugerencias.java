package org.example.interf;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaSugerencias {

    private static final String ARCHIVO = "data/sugerencias.txt";

    //Julián: Este código tiene como propósito guardar una lista de sugerencias en el archivo
    public static void guardarSugerencias(List<String> sugerencias) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (String sugerencia : sugerencias) {
                writer.write(sugerencia);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar las sugerencias: " + e.getMessage());
        }
    }

    //Julián: Esto carga las sugerencias desde el archivo (si existe)
    public static List<String> cargarSugerencias() {
        List<String> sugerencias = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        // Julián: Esto crea el archivo si no existe aún (opcional)
        if (!archivo.exists()) {
            try {
                archivo.getParentFile().mkdirs(); // Por si la carpeta no existe
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("No se pudo crear el archivo de sugerencias: " + e.getMessage());
                return sugerencias;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                sugerencias.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer las sugerencias: " + e.getMessage());
        }

        return sugerencias;
    }
}