    package org.example.interf;

    import javafx.application.Application;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Scene;
    import javafx.stage.Stage;

    import java.io.IOException;

    public class HelloApplication extends Application {
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/interf/login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.sizeToScene();
            stage.setTitle("Inicio de Sesión 🔐");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
    }