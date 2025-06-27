package org.example.interf;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SugerenciasController {

    @FXML
    private TextField campoSugerencia;

    @FXML
    private TextArea areaSugerencias;

    private Cola<String> cola = new Cola<>();

    @FXML
    public void initialize() {
        for (String s : PersistenciaSugerencias.cargarSugerencias()) {
            cola.enqueue(s);
        }
        actualizarVista();
    }

    @FXML
    protected void agregarSugerencia() {
        String sugerencia = campoSugerencia.getText().trim();

        if (sugerencia.isEmpty()) {
            mostrarAlerta("Sugerencia vacía", "Por favor, escribe una sugerencia antes de agregar.");
            return;
        }

        cola.enqueue(sugerencia);
        campoSugerencia.clear();
        actualizarVista();
        PersistenciaSugerencias.guardarSugerencias(cola.toList()); //Julián: Esto guarda las sugerencias luego de agregar alguna
    }

    @FXML
    protected void leerSugerencia() {
        if (cola.isEmpty()) {
            mostrarAlerta("Sin sugerencias", "El buzón está vacío.");
            return;
        }

        String sugerencia = cola.peek();
        mostrarAlerta("Sugerencia más antigua", sugerencia);
    }

    @FXML
    protected void eliminarSugerencia() {
        if (cola.isEmpty()) {
            mostrarAlerta("Sin sugerencias", "Ya no hay sugerencias para eliminar.");
            return;
        }

        String eliminada = cola.dequeue();
        mostrarAlerta("Sugerencia eliminada", "Se eliminó: " + eliminada);
        actualizarVista();
        PersistenciaSugerencias.guardarSugerencias(cola.toList()); //Julián: Esto guarda las sugerencias luego de eliminar alguna
    }

    private void actualizarVista() {
        StringBuilder sb = new StringBuilder();
        for (String s : cola.toList()) {
            sb.append("- ").append(s).append("\n");
        }
        areaSugerencias.setText(sb.toString());
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
