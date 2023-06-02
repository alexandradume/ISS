package client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Admin;
import model.Bibliotecar;
import model.Client;
import services.Service;
import services.BibliotecaException;

import java.io.IOException;

public class MainController {
    //private Service service;
    @FXML
    private TextField usernameTF;

    @FXML
    private TextField parolaTF;

    @FXML
    private Button logIn;
    private Service server;

    @FXML
    private Stage stage;

    public void logIn(ActionEvent actionEvent) throws BibliotecaException, IOException {
        String username = usernameTF.getText();
        String password = parolaTF.getText();
        usernameTF.setText("");
        parolaTF.setText("");
        //label.setText(username);
        Client client  = new Client(password,username, "", "");
        System.out.println(client);
        Client clientt = server.findOneClient(client);
        //System.out.println(clientt);
        /*Bibliotecar bibliotecar  = new Bibliotecar(password,username, "", "");
        Bibliotecar bibliotecarr = server.findOneBibliotecar(bibliotecar);
        //System.out.println(bibliotecarr);

        Admin admin = new Admin(password,username, "", "");
        Admin adminn = server.findOneAdmin(admin);*/
        //System.out.println(admin);
        if(clientt != null){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("client-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            System.out.println("hello");
            ClientController spectacleController = fxmlLoader.getController();
            int i = server.verifyType(clientt,  spectacleController);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(i);
            //spectacleController.setClass(client);
            stage.setTitle("client");
            stage.setScene(scene);

            spectacleController.initialize(server, client,stage);}
        else{
            Bibliotecar bibliotecar  = new Bibliotecar(password,username, "", "");
            Bibliotecar bibliotecarr = server.findOneBibliotecar(bibliotecar);
            if(bibliotecarr != null){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("bibliotecar-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    System.out.println("hello");
                    BibliotecarController bibliotecarController = fxmlLoader.getController();
                    int i = server.verifyBibliotecar(bibliotecar, bibliotecarController);
                    stage.setTitle("bibliotecar");
                    stage.setScene(scene);
                    bibliotecarController.initialize(server);
            }
            else{
                System.out.println("admin");
                Admin admin = new Admin(password,username, "", "");
                Admin adminn = server.findOneAdmin(admin);
                if(adminn != null){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("admin-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    System.out.println("admin");
                    AdminController adminController = fxmlLoader.getController();
                    int i = server.verifyAdmin(adminn, adminController);
                    stage.setTitle("Main");
                    stage.setScene(scene);
                    adminController.initialize(server);
                }
            }

        }



}

    public void initialize(Stage primaryStage, Service server) {
        this.server = server;
        this.stage = primaryStage;

    }
    }
