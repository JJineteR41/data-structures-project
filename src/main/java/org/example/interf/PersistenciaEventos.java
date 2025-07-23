package org.example.interf;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaEventos {
    public void saveToFile(String filename) {
        List<Evento> eventos = arbolEventos.inOrderTransversal();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(eventos);
            System.out.println("Eventos saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<Evento> eventos = (List<Evento>) ois.readObject();
            this.clear(); // Optional: clear existing tree
            for (Evento e : eventos) {
                this.insert(e);
            }
            System.out.println("Eventos loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
