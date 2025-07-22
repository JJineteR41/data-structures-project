package org.example.interf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;

public class InicioController {

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
    private void irATareas(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tareas-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
