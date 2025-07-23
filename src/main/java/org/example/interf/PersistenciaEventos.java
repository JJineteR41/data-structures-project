package org.example.interf;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersistenciaEventos {
    private static final String ARCHIVO = "data/eventos.txt";
    private static final String SEPARADOR = "|";


    public static void guardarEventos(List<Evento> eventos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Evento evento : eventos) {
                String linea = formatearEvento(evento);
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar las tareas pendientes: " + e.getMessage());
        }
    }

    private static String formatearEvento(Evento evento) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return evento.getEvento() + SEPARADOR + formato.format(evento.getFecha());
    }

    private static Evento parsearEvento(String linea) {
        try {
            String[] partes = linea.split("\\" + SEPARADOR);
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            if (partes.length != 2) {
                System.err.println("Formato de línea inválido: " + linea);
                return null;
            }

            String event = partes[0];
            Date fecha = formato.parse(partes[1]);

            Evento evento = new Evento(event, fecha);

            return evento;

        } catch (NumberFormatException | ParseException e) {
            System.err.println("Error al parsear la línea: " + linea + " - " + e.getMessage());
            return null;
        }
    }


    public static List<Evento> cargarEventos() {
        List<Evento> eventos = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        // Creamos el archivo y el directorio si no existe
        if (!archivo.exists()) {
            try {
                archivo.getParentFile().mkdirs(); // Crear carpeta data si no existe
                archivo.createNewFile();
                return eventos; // Retornar lista vacía para archivo nuevo
            } catch (IOException e) {
                System.err.println("No se pudo crear el archivo " + ARCHIVO + ": " + e.getMessage());
                return eventos;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    Evento evento = parsearEvento(linea);
                    if (evento != null) {
                        eventos.add(evento);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + ARCHIVO + ": " + e.getMessage());
        }

        return eventos;
    }

}
