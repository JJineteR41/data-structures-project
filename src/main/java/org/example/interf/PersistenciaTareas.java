package org.example.interf;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersistenciaTareas {

    private static final String ARCHIVO_PENDIENTES = "data/tareas_pendientes.txt";
    private static final String ARCHIVO_COMPLETADAS = "data/tareas_completadas.txt";
    private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Formato del archivo: descripcion|prioridad|categoria|fechaCreacion|completada
    private static final String SEPARADOR = "|";

   //Guardamos todas las tareas del gestor en archivos diferentes
    public static void guardarTareas(GestorTareas gestor) {
        guardarTareasPendientes(gestor.obtenerTareasPendientesOrdenadas());
        guardarTareasCompletadas(gestor.obtenerTareasCompletadas());
    }

    //Guardamos las tareas pendientes en el archivo correspondiente
    public static void guardarTareasPendientes(List<Tarea> tareasPendientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_PENDIENTES))) {
            for (Tarea tarea : tareasPendientes) {
                String linea = formatearTarea(tarea);
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar las tareas pendientes: " + e.getMessage());
        }
    }

  //Guardamos las tareas completadas en el archivo correspondiente
    public static void guardarTareasCompletadas(List<Tarea> tareasCompletadas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_COMPLETADAS))) {
            for (Tarea tarea : tareasCompletadas) {
                String linea = formatearTarea(tarea);
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar las tareas completadas: " + e.getMessage());
        }
    }

    //Cargamos todas las tareas y restauramos el gestor
    public static void cargarTareas(GestorTareas gestor) {
        // Limpiamos el gestor antes de cargar
        gestor.limpiarTodas();

        // Cargamos las tareas pendientes
        List<Tarea> tareasPendientes = cargarTareasPendientes();
        for (Tarea tarea : tareasPendientes) {
            gestor.agregarTarea(tarea.getDescripcion(), tarea.getPrioridad(), tarea.getCategoria());
        }

        // Cargamos las tareas completadas
        List<Tarea> tareasCompletadas = cargarTareasCompletadas();
        for (Tarea tarea : tareasCompletadas) {
            // Crear una nueva tarea y marcarla como completada
            Tarea tareaRecuperada = new Tarea(tarea.getDescripcion(), tarea.getPrioridad(), tarea.getCategoria());
            tareaRecuperada.setCompletada(true);
            // Agregar directamente a la lista de completadas del gestor
            gestor.obtenerTareasCompletadas().add(tareaRecuperada);
        }
    }

   //Cargamos las tareas pendientes desde el archivo
    public static List<Tarea> cargarTareasPendientes() {
        return cargarTareasDesdeArchivo(ARCHIVO_PENDIENTES);
    }

   //Cargamos las tareas completadas desde el archivo
    public static List<Tarea> cargarTareasCompletadas() {
        return cargarTareasDesdeArchivo(ARCHIVO_COMPLETADAS);
    }

   //Metodo para cargar tareas desde un archivo especifico
    private static List<Tarea> cargarTareasDesdeArchivo(String nombreArchivo) {
        List<Tarea> tareas = new ArrayList<>();
        File archivo = new File(nombreArchivo);

        // Creamos el archivo y el directorio si no existe
        if (!archivo.exists()) {
            try {
                archivo.getParentFile().mkdirs(); // Crear carpeta data si no existe
                archivo.createNewFile();
                return tareas; // Retornar lista vacía para archivo nuevo
            } catch (IOException e) {
                System.err.println("No se pudo crear el archivo " + nombreArchivo + ": " + e.getMessage());
                return tareas;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    Tarea tarea = parsearTarea(linea);
                    if (tarea != null) {
                        tareas.add(tarea);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + nombreArchivo + ": " + e.getMessage());
        }

        return tareas;
    }

    //Convertimos una tarea a una linea del archivo para guardarlo
    private static String formatearTarea(Tarea tarea) {
        return tarea.getDescripcion() + SEPARADOR +
                tarea.getPrioridad() + SEPARADOR +
                tarea.getCategoria() + SEPARADOR +
                FORMATO_FECHA.format(tarea.getFechaCreacion()) + SEPARADOR +
                tarea.isCompletada();
    }

    //Convertimos una linea del archivo a objeto tarea
    private static Tarea parsearTarea(String linea) {
        try {
            String[] partes = linea.split("\\" + SEPARADOR);

            if (partes.length != 5) {
                System.err.println("Formato de línea inválido: " + linea);
                return null;
            }

            String descripcion = partes[0];
            int prioridad = Integer.parseInt(partes[1]);
            String categoria = partes[2];
            Date fechaCreacion = FORMATO_FECHA.parse(partes[3]);
            boolean completada = Boolean.parseBoolean(partes[4]);

            Tarea tarea = new Tarea(descripcion, prioridad, categoria);

            return new TareaRecuperada(descripcion, prioridad, categoria, fechaCreacion, completada);

        } catch (NumberFormatException | ParseException e) {
            System.err.println("Error al parsear la línea: " + linea + " - " + e.getMessage());
            return null;
        }
    }

  //Eliminamos todos los archivos de persistencia
    public static void limpiarArchivos() {
        File archivoPendientes = new File(ARCHIVO_PENDIENTES);
        File archivoCompletadas = new File(ARCHIVO_COMPLETADAS);

        if (archivoPendientes.exists()) {
            archivoPendientes.delete();
        }

        if (archivoCompletadas.exists()) {
            archivoCompletadas.delete();
        }

        System.out.println("Archivos de persistencia eliminados.");
    }

    //Verificamos si existen archivos de persistencia de datos
    public static boolean existenDatos() {
        File archivoPendientes = new File(ARCHIVO_PENDIENTES);
        File archivoCompletadas = new File(ARCHIVO_COMPLETADAS);

        return (archivoPendientes.exists() && archivoPendientes.length() > 0) ||
                (archivoCompletadas.exists() && archivoCompletadas.length() > 0);
    }

    //Hacemos una copia de seguridad de los archivos actuales
    public static void hacerBackup() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        hacerBackupArchivo(ARCHIVO_PENDIENTES, "data/backup_pendientes_" + timestamp + ".txt");
        hacerBackupArchivo(ARCHIVO_COMPLETADAS, "data/backup_completadas_" + timestamp + ".txt");

        System.out.println("Backup creado con timestamp: " + timestamp);
    }

    //Metodo para hacerle backup a un archivo
    private static void hacerBackupArchivo(String archivoOrigen, String archivoDestino) {
        File origen = new File(archivoOrigen);
        if (!origen.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(origen));
             BufferedWriter writer = new BufferedWriter(new FileWriter(archivoDestino))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al crear backup de " + archivoOrigen + ": " + e.getMessage());
        }
    }

    //Recuperamos tareas con la fecha original
    private static class TareaRecuperada extends Tarea {
        public TareaRecuperada(String descripcion, int prioridad, String categoria, Date fechaOriginal, boolean completada) {
            super(descripcion, prioridad, categoria);
            // Establecer la fecha original (hack para mantener la fecha)
            this.setCompletada(completada);
        }
    }
}