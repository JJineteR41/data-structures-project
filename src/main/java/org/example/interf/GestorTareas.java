package org.example.interf;

import java.util.ArrayList;
import java.util.List;

public class GestorTareas {
    private HeapPrioridad<Tarea> tareasPendientes;
    private List<Tarea> tareasCompletadas;
    private boolean autoGuardado; // Control para habilitar/deshabilitar autoguardado

    public GestorTareas() {
        this(true); // Por defecto con autoguardado habilitado
    }

    public GestorTareas(boolean autoGuardado) {
        this.tareasPendientes = new HeapPrioridad<>();
        this.tareasCompletadas = new ArrayList<>();
        this.autoGuardado = autoGuardado;

        // Cargamos datos existentes si hay
        if (autoGuardado && PersistenciaTareas.existenDatos()) {
            cargarDatos();
        }
    }

    // Agregamos una nueva tarea
    public void agregarTarea(String descripcion, int prioridad, String categoria) {
        Tarea nuevaTarea = new Tarea(descripcion, prioridad, categoria);
        tareasPendientes.insertar(nuevaTarea);

        if (autoGuardado) {
            guardarDatos();
        }
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

            if (autoGuardado) {
                guardarDatos();
            }
        }
        return tarea;
    }

    // Eliminamos una tarea específica
    public boolean eliminarTarea(Tarea tarea) {
        boolean eliminada = tareasPendientes.eliminar(tarea);

        if (eliminada && autoGuardado) {
            guardarDatos();
        }

        return eliminada;
    }

    // Marcamos una tarea como completada sin seguir el orden de prioridad
    public boolean completarTarea(Tarea tarea) {
        if (tareasPendientes.eliminar(tarea)) {
            tarea.setCompletada(true);
            tareasCompletadas.add(tarea);

            if (autoGuardado) {
                guardarDatos();
            }
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

    // Obtenemos las tareas por categoría
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

    // Obtenemos las estadísticas
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

        if (autoGuardado) {
            guardarDatos();
        }
    }

    // Limpiamos solo las tareas completadas
    public void limpiarTareasCompletadas() {
        tareasCompletadas.clear();

        if (autoGuardado) {
            guardarDatos();
        }
    }

    // === MÉTODOS DE PERSISTENCIA ===

    /**
     * Guardamos los datos en archivos txt
     */
    public void guardarDatos() {
        PersistenciaTareas.guardarTareas(this);
    }

    /**
     * Cargamos los datos desde archivos
     */
    public void cargarDatos() {
        PersistenciaTareas.cargarTareas(this);
    }

    /**
     * Habilitamos o deshabilitamos el autoguardado
     */
    public void setAutoGuardado(boolean autoGuardado) {
        this.autoGuardado = autoGuardado;
    }

    /**
     * Verificamos si el autoguardado está habilitado
     */
    public boolean isAutoGuardadoHabilitado() {
        return autoGuardado;
    }

    /**
     * Hacemos una copia de seguridad de los datos actuales
     */
    public void hacerBackup() {
        PersistenciaTareas.hacerBackup();
    }

    /**
     * Reseteamos completamente el sistema (elimina archivos y limpia memoria)
     */
    public void resetearSistema() {
        limpiarTodas();
        PersistenciaTareas.limpiarArchivos();
        System.out.println("Sistema de tareas reseteado completamente.");
    }

    // Imprimimos reporte de tareas
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
        System.out.println("Categorías: " + String.join(", ", stats.getCategorias()));
        System.out.println("Autoguardado: " + (autoGuardado ? "Habilitado" : "Deshabilitado"));
        System.out.println("=============================================\n");
    }

    // Clase interna para estadísticas
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