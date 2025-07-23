package org.example.interf;
import  java.util.ArrayList;
import java.util.List;

public class TablaHashUsuarios {
    private final int CAPACIDAD = 101;
    private List<Usuario>[] tabla;

    public TablaHashUsuarios() {
        tabla = new ArrayList[CAPACIDAD];
        for (int i = 0; i < CAPACIDAD; i++) {
            tabla[i] = new ArrayList<>();
        }
    }

    private int hash(String clave) {
        return Math.abs(clave.hashCode()) % CAPACIDAD;
    }

    public boolean agregarUsuario(Usuario usuario) {
        int index = hash(usuario.getNombreUsuario());
        for (Usuario u : tabla[index]) {
            if (u.getNombreUsuario().equals(usuario.getNombreUsuario())) return false; // usuario ya existe
        }
        tabla[index].add(usuario);
        return true;
    }

    public Usuario buscarUsuario(String nombreUsuario) {
        int index = hash(nombreUsuario);
        for (Usuario u : tabla[index]) {
            if (u.getNombreUsuario().equals(nombreUsuario)) return u;
        }
        return null;
    }

    public boolean eliminarUsuario(String nombreUsuario) {
        int index = hash(nombreUsuario);
        return tabla[index].removeIf(u -> u.getNombreUsuario().equals(nombreUsuario));
    }

    public boolean autenticar(String nombreUsuario, String contraseña) {
        Usuario u = buscarUsuario(nombreUsuario);
        return u != null && u.getContraseña().equals(contraseña);
    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        for (List<Usuario> lista : tabla) {
            usuarios.addAll(lista);
        }
        return usuarios;
    }
}