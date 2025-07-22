package org.example.interf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;

public class InicioController {

    @FXML
    private Label etiquetaBienvenida;

    @FXML
    private Button botonEventos;

    private Usuario usuarioActual;

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;

        if (etiquetaBienvenida != null) {
            etiquetaBienvenida.setText("¡Bienvenido " + usuario.getNombreUsuario() + "!" + " | " + " Rol actual: " + usuario.getRol());
        }

        if (usuario.getRol().equalsIgnoreCase("Invitado") && botonEventos != null) {
            botonEventos.setVisible(false);
        }
    }

    @FXML
    private void cambiarASugerencias(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sugerencias-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene nuevaEscena = new Scene(root);
            stage.setScene(nuevaEscena);
            stage.setTitle("Buzón de Sugerencias 🎤");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirVistaEventos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("eventos-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene nuevaEscena = new Scene(root);
            stage.setScene(nuevaEscena);
            stage.setTitle("Gestión de Eventos 🎼");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inicio de Sesión 🔐");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
