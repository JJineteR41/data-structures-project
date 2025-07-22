package org.example.interf;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventosController {

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoFecha;

    @FXML
    private TableView<Evento> tablaEventos;

    @FXML
    private TableColumn<Evento, String> columnaNombre;

    @FXML
    private TableColumn<Evento, String> columnaFecha;

    private AVLTree<Evento> arbolEventos = new AVLTree<>();

    @FXML
    public void initialize() {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("evento"));

        columnaFecha.setCellValueFactory(cellData -> {
            Date fecha = cellData.getValue().getFecha();
            String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(fecha);
            return new javafx.beans.property.SimpleStringProperty(fechaFormateada);
        });

        refrescarTabla();
    }

    @FXML
    private void agregarEvento() {
        try {
            String nombre = campoNombre.getText();
            String fechaTexto = campoFecha.getText();

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = formato.parse(fechaTexto);

            Evento nuevoEvento = new Evento(nombre, fecha);
            arbolEventos.insert(nuevoEvento);

            campoNombre.clear();
            campoFecha.clear();

            refrescarTabla();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refrescarTabla() {
        tablaEventos.getItems().clear();
        List<Evento> eventosOrdenados = arbolEventos.getAllElementsSorted();
        tablaEventos.getItems().addAll(eventosOrdenados);
    }

    @FXML
    private void volverAlInicio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene nuevaEscena = new Scene(root);
            stage.setScene(nuevaEscena);
            stage.setTitle("Menú Principal");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void eliminarPrimerEvento() {
        try {
            arbolEventos.deleteFirst();
            refrescarTabla();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
