package org.example.interf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorTareas {
    private HeapPrioridad<Tarea> tareasPendientes;
    private List<Tarea> tareasCompletadas;

    public GestorTareas() {
        this.tareasPendientes = new HeapPrioridad<>();
        this.tareasCompletadas = new ArrayList<>();
    }

    // Agregamos una nueva tarea
    public void agregarTarea(String descripcion, int prioridad, String categoria) {
        Tarea nuevaTarea = new Tarea(descripcion, prioridad, categoria);
        tareasPendientes.insertar(nuevaTarea);
    }

    public void agregarTarea(String descripcion, int prioridad) {
        agregarTarea(descripcion, prioridad, "General");
    }

    // Obtenemos la tarea de mayor prioridad
    public Tarea obtenerTareaPrioritaria() {
        return tareasPendientes.verMinimo();
    }

    // Completamos la tarea de mayor prioridad
    public Tarea completarTareaPrioritaria() {
        Tarea tarea = tareasPendientes.extraerMinimo();
        if (tarea != null) {
            tarea.setCompletada(true);
            tareasCompletadas.add(tarea);
        }
        return tarea;
    }

    // Eliminamos una tarea específica
    public boolean eliminarTarea(Tarea tarea) {
        return tareasPendientes.eliminar(tarea);
    }

    // Marcamos una tarea como completada sin seguir el orden de prioridad
    public boolean completarTarea(Tarea tarea) {
        if (tareasPendientes.eliminar(tarea)) {
            tarea.setCompletada(true);
            tareasCompletadas.add(tarea);
            return true;
        }
        return false;
    }

    // Obtenemos todas las tareas pendientes ordenadas por prioridad
    public List<Tarea> obtenerTareasPendientesOrdenadas() {
        return tareasPendientes.obtenerTodosOrdenados();
    }

    // Obtenemos todas las tareas completadas
    public List<Tarea> obtenerTareasCompletadas() {
        return new ArrayList<>(tareasCompletadas);
    }

    // Obtenemos tareas por categoría
    public List<Tarea> obtenerTareasPorCategoria(String categoria) {
        List<Tarea> resultado = new ArrayList<>();

        // Tareas pendientes de la categoría
        List<Tarea> pendientes = tareasPendientes.obtenerElementos();
        for (Tarea tarea : pendientes) {
            if (tarea.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(tarea);
            }
        }

        return resultado;
    }

    // Obtenemos las estadísticas relacionadas a la tarea
    public EstadisticasTareas obtenerEstadisticas() {
        return new EstadisticasTareas(
                tareasPendientes.size(),
                tareasCompletadas.size(),
                obtenerCategorias()
        );
    }

    // Obtenemos todas las categorías únicas
    public List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();

        // Categorías de tareas pendientes
        for (Tarea tarea : tareasPendientes.obtenerElementos()) {
            if (!categorias.contains(tarea.getCategoria())) {
                categorias.add(tarea.getCategoria());
            }
        }

        // Categorías de tareas completadas
        for (Tarea tarea : tareasCompletadas) {
            if (!categorias.contains(tarea.getCategoria())) {
                categorias.add(tarea.getCategoria());
            }
        }

        return categorias;
    }

    // Verificamos si hay tareas pendientes
    public boolean hayTareasPendientes() {
        return !tareasPendientes.isEmpty();
    }

    // Obtenemos el número de tareas pendientes
    public int numeroDeTareasPendientes() {
        return tareasPendientes.size();
    }

    // Obtenemos el número de tareas completadas
    public int numeroDeTareasCompletadas() {
        return tareasCompletadas.size();
    }

    // Limpiamos todas las tareas
    public void limpiarTodas() {
        tareasPendientes.limpiar();
        tareasCompletadas.clear();
    }

    // Limpiamos solo las tareas completadas
    public void limpiarTareasCompletadas() {
        tareasCompletadas.clear();
    }

    // Imprimimos el reporte de tareas
    public void imprimirReporte() {
        System.out.println("\n=== REPORTE DE TAREAS DEL ENSAMBLE MUSICAL ===");

        System.out.println("\n--- TAREAS PENDIENTES (por prioridad) ---");
        if (tareasPendientes.isEmpty()) {
            System.out.println("No hay tareas pendientes.");
        } else {
            List<Tarea> pendientes = obtenerTareasPendientesOrdenadas();
            for (int i = 0; i < pendientes.size(); i++) {
                System.out.println((i + 1) + ". " + pendientes.get(i));
            }
        }

        System.out.println("\n--- TAREAS COMPLETADAS ---");
        if (tareasCompletadas.isEmpty()) {
            System.out.println("No hay tareas completadas.");
        } else {
            for (int i = 0; i < tareasCompletadas.size(); i++) {
                System.out.println((i + 1) + ". " + tareasCompletadas.get(i));
            }
        }

        EstadisticasTareas stats = obtenerEstadisticas();
        System.out.println("\n--- ESTADÍSTICAS ---");
        System.out.println("Tareas pendientes: " + stats.getTareasPendientes());
        System.out.println("Tareas completadas: " + stats.getTareasCompletadas());
        System.out.println("Total de tareas: " + (stats.getTareasPendientes() + stats.getTareasCompletadas()));
        System.out.println("Categorías: " + String.join(", ", stats.getCategorias;
    }

    // Clase interna para las estadísticas de las tareas
    public static class EstadisticasTareas {
        private int tareasPendientes;
        private int tareasCompletadas;
        private List<String> categorias;

        public EstadisticasTareas(int pendientes, int completadas, List<String> categorias) {
            this.tareasPendientes = pendientes;
            this.tareasCompletadas = completadas;
            this.categorias = categorias;
        }

        public int getTareasPendientes() { return tareasPendientes; }
        public int getTareasCompletadas() { return tareasCompletadas; }
        public List<String> getCategorias() { return categorias; }
        public int getTotal() { return tareasPendientes + tareasCompletadas; }
    }
}