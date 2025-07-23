package org.example.interf;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistroController implements Initializable {

    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoContrasena;
    @FXML private ComboBox<String> comboRol;
    @FXML private Label mensajeRegistro;

    private TablaHashUsuarios tablaUsuarios;

    public void setTablaUsuarios(TablaHashUsuarios tablaUsuarios) {
        this.tablaUsuarios = tablaUsuarios;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboRol.getItems().addAll("Administrador", "Intérprete", "Invitado");
    }

    @FXML
    private void registrarUsuario() {
        String nombre = campoUsuario.getText().trim();
        String contrasena = campoContrasena.getText();
        String rol = comboRol.getValue();

        if (nombre.isEmpty() || contrasena.isEmpty() || rol == null) {
            mensajeRegistro.setText("Por favor, complete todos los campos.");
            mensajeRegistro.setVisible(true);
            return;
        }

        if (tablaUsuarios.buscarUsuario(nombre) != null) {
            mensajeRegistro.setText("El usuario ya existe.");
            mensajeRegistro.setVisible(true);
            return;
        }

        Usuario nuevo = new Usuario(nombre, contrasena, rol);
        tablaUsuarios.agregarUsuario(nuevo);
        PersistenciaUsuarios.guardarUsuarios(tablaUsuarios);

        mensajeRegistro.setText("✅ Usuario registrado con éxito.");
        mensajeRegistro.setTextFill(javafx.scene.paint.Color.GREEN);
        mensajeRegistro.setVisible(true);

        campoUsuario.clear();
        campoContrasena.clear();
        comboRol.getSelectionModel().clearSelection();
    }

    @FXML
    private void volverALogin(javafx.event.ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inicio de Sesión 🔐");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
