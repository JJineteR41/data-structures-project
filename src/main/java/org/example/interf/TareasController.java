package org.example.interf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class TareasController {

    @FXML
    private TextField descripcionField;

    @FXML
    private TextField prioridadField;

    @FXML
    private TextField categoriaField;

    @FXML
    private TableView<Tarea> tareasTable;

    @FXML
    private TableColumn<Tarea, String> descripcionColumn;

    @FXML
    private TableColumn<Tarea, Integer> prioridadColumn;

    @FXML
    private TableColumn<Tarea, String> categoriaColumn;

    @FXML
    private TableColumn<Tarea, String> completadaColumn;

    private final GestorTareas gestorTareas = new GestorTareas();
    private final ObservableList<Tarea> tareasObservable = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        descripcionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescripcion()));
        prioridadColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getPrioridad()).asObject());
        categoriaColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategoria()));
        completadaColumn.setCellValueFactory(cellData -> {
            String estado = cellData.getValue().isCompletada() ? "Sí" : "No";
            return new javafx.beans.property.SimpleStringProperty(estado);
        });

        tareasTable.setItems(tareasObservable);
        cargarTareas();
    }

    @FXML
    private void agregarTarea() {
        String descripcion = descripcionField.getText();
        String prioridadText = prioridadField.getText();
        String categoria = categoriaField.getText().isEmpty() ? "General" : categoriaField.getText();

        if (descripcion.isEmpty() || prioridadText.isEmpty()) {
            mostrarAlerta("Faltan datos", "Por favor completa la descripción y la prioridad.");
            return;
        }

        try {
            int prioridad = Integer.parseInt(prioridadText);
            gestorTareas.agregarTarea(descripcion, prioridad, categoria);
            cargarTareas();
            limpiarCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Prioridad inválida", "La prioridad debe ser un número entero.");
        }
    }

    @FXML
    private void completarTareaSeleccionada() {
        Tarea tareaSeleccionada = tareasTable.getSelectionModel().getSelectedItem();
        if (tareaSeleccionada == null) {
            mostrarAlerta("Sin selección", "Por favor selecciona una tarea para marcar como completada.");
            return;
        }

        boolean completada = gestorTareas.completarTarea(tareaSeleccionada);
        if (completada) {
            cargarTareas();
        } else {
            mostrarAlerta("Error", "No se pudo completar la tarea.");
        }
    }
    @FXML
    private void volverAlInicio(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void eliminarTareaSeleccionada() {
        Tarea tareaSeleccionada = tareasTable.getSelectionModel().getSelectedItem();
        if (tareaSeleccionada == null) {
            mostrarAlerta("Sin selección", "Por favor selecciona una tarea para eliminar.");
            return;
        }

        boolean eliminada = gestorTareas.eliminarTarea(tareaSeleccionada);

        if (eliminada) {
            cargarTareas();
        } else {
            mostrarAlerta("Error", "No se pudo eliminar la tarea.");
        }
    }



    private void cargarTareas() {
        tareasObservable.clear();
        tareasObservable.addAll(gestorTareas.obtenerTareasPendientesOrdenadas());
        tareasObservable.addAll(gestorTareas.obtenerTareasCompletadas());
    }

    private void limpiarCampos() {
        descripcionField.clear();
        prioridadField.clear();
        categoriaField.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }



}
