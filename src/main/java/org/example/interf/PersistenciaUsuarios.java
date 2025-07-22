package org.example.interf;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuarios {

    private static final String ARCHIVO = "data/usuarios.csv";
    private static final String ENCABEZADO = "nombreUsuario,contraseña,rol";

    // Guarda los usuarios en formato CSV
    public static void guardarUsuarios(List<Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            writer.write(ENCABEZADO);
            writer.newLine();

            for (Usuario u : usuarios) {
                String linea = String.format("%s,%s,%s",
                        u.getNombreUsuario(),
                        u.getContraseña(),
                        u.getRol());
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar los usuarios: " + e.getMessage());
        }
    }

    // Carga usuarios desde el archivo CSV
    public static List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            try {
                archivo.getParentFile().mkdirs(); // crea la carpeta si no existe
                archivo.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                    writer.write(ENCABEZADO);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("No se pudo crear el archivo de usuarios: " + e.getMessage());
            }
            return usuarios;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = reader.readLine()) != null) {
                if (primeraLinea && linea.trim().equalsIgnoreCase(ENCABEZADO)) {
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String nombre = partes[0].trim();
                    String contraseña = partes[1].trim();
                    String rol = partes[2].trim();
                    usuarios.add(new Usuario(nombre, contraseña, rol));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer los usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    public static void guardarUsuarios(TablaHashUsuarios tabla) {
        guardarUsuarios(tabla.listarTodos());
    }
}
