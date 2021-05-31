package main;

import controllers.FarmacistController;
import controllers.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IServices;

public class MainFx extends Application {
    private Stage primaryStage;

    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";


    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IServices server=(IServices) factory.getBean("service");


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/logInView.fxml"));
        Parent mainPage = loader.load();
        FarmacistController controller = new FarmacistController();
        controller.setServer(server);

        LogInController ctrl = loader.getController();
        ctrl.setService(controller);

        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(mainPage));
        primaryStage.show();
    }
}