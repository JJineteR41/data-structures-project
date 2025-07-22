package org.example.interf;

import java.util.Date;

public class Tarea implements Comparable<Tarea> {
    private String descripcion;
    private int prioridad; // 1 = máxima prioridad, números mayores son los de menor prioridad
    private Date fechaCreacion;
    private String categoria; // "Ensayo", "Presentación", "Administrativo", "Revision de partituras"
    private boolean completada;

    public Tarea(String descripcion, int prioridad, String categoria) {
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.categoria = categoria;
        this.fechaCreacion = new Date();
        this.completada = false;
    }

    public Tarea(String descripcion, int prioridad) {
        this(descripcion, prioridad, "General");
    }

    // Getters y setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    @Override
    public int compareTo(Tarea otra) {
        // Prioridad dada por los numeros menores
        int comparacionPrioridad = Integer.compare(this.prioridad, otra.prioridad);

        // Si tienen la misma prioridad, ordenamos por la fecha de creacion de la tarea, las mas antiguas primero
        if (comparacionPrioridad == 0) {
            return this.fechaCreacion.compareTo(otra.fechaCreacion);
        }

        return comparacionPrioridad;
    }

    @Override
    public String toString() {
        return String.format("[P%d] %s (%s) - %s",
                prioridad, descripcion, categoria,
                completada ? "Completada" : "Pendiente");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Tarea tarea = (Tarea) obj;
        return prioridad == tarea.prioridad &&
                descripcion.equals(tarea.descripcion) &&
                categoria.equals(tarea.categoria) &&
                fechaCreacion.equals(tarea.fechaCreacion);
    }

    @Override
    public int hashCode() {
        return descripcion.hashCode() + prioridad + categoria.hashCode();
    }
}