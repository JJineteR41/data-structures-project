package org.example.interf;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import org.example.interf.TablaHashUsuarios;
import org.example.interf.Usuario;
import org.example.interf.PersistenciaUsuarios;

import java.io.IOException;
import java.util.List;

public class LoginController {

    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoContrasena;
    @FXML private Label mensajeError;

    private TablaHashUsuarios tablaUsuarios;

    @FXML
    public void initialize() {
        tablaUsuarios = new TablaHashUsuarios();
        List<Usuario> usuarios = PersistenciaUsuarios.cargarUsuarios();
        for (Usuario u : usuarios) {
            tablaUsuarios.agregarUsuario(   u);
        }
    }

    @FXML
    public void manejarLogin() {
        String usuario = campoUsuario.getText().trim();
        String clave = campoContrasena.getText().trim();

        if (tablaUsuarios.autenticar(usuario, clave)) {
            Usuario logueado = tablaUsuarios.buscarUsuario(usuario);
            abrirVistaInicio(logueado);
        } else {
            mensajeError.setText("Usuario o contraseña incorrectos");
            mensajeError.setVisible(true);
        }
    }

    @FXML
    private void ingresarComoInvitado(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
            Parent root = loader.load();

            InicioController controller = loader.getController();
            controller.setUsuarioActual(new Usuario("Invitado", "", "Invitado"));

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Ensamble Músico Vocal 🎶");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irARegistro(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registro-view.fxml"));
            Parent root = loader.load();

            RegistroController controladorRegistro = loader.getController();
            controladorRegistro.setTablaUsuarios(tablaUsuarios);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Registro de Usuario 📝");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void abrirVistaInicio(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
            Parent root = loader.load();

            // Si quieres pasar el usuario a InicioController:
            InicioController controller = loader.getController();
            controller.setUsuarioActual(usuario);

            Stage stage = (Stage) campoUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Ensamble Músico Vocal 🎵");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}