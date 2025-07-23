package org.example.interf;
import java.util.Date;

public class Evento implements Comparable<Evento>{

    private String evento;
    private Date fecha;

    public Evento(String evento, Date fecha) {
        this.evento = evento;
        this.fecha = fecha;
    }

    public String getEvento() {
        return evento;
    }

    public Date getFecha() {
        return fecha;
    }

    @Override
    public int compareTo(Evento otro) {
        return this.fecha.compareTo(otro.fecha);
    }

    @Override
    public String toString() {
        return evento + " - " + fecha.toString();
    }
}
