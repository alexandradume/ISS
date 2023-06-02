package client.gui;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Carte;
import model.Client;
import services.Observer;
import services.Service;
import services.BibliotecaException;

import java.io.IOException;
import java.util.List;

import static java.util.stream.StreamSupport.stream;

public class ClientController implements Observer {

    private Service server;

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField parolaTF;

    @FXML
    private Button button;

    @FXML
    TableColumn<Carte, Integer> id;

    @FXML
    TableColumn<Carte, String> titlu;

    @FXML
    TableColumn<Carte, String> autor;

    @FXML
    TableColumn<Carte, String> tip;

    @FXML
    private TableView<Carte> tabel;

    private Client client;

    @FXML
    private Stage stage;


    @FXML
    TableColumn<Carte,Integer> id1;

    @FXML
    TableColumn<Carte,String> titlu1;

    @FXML
    TableColumn<Carte,String> autor1;

    @FXML
    TableColumn<Carte,String> tip1;

    @FXML
    private TableView<Carte> tabel1;



    private final ObservableList<Carte> model = FXCollections.observableArrayList();

    public void initialize(Service server, Client client, Stage stage) throws BibliotecaException {
        this.server = server;
        this.stage = stage;
        this.client = client;
        id.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        autor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tip.setCellValueFactory(new PropertyValueFactory<>("tip"));
        id1.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titlu1.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        autor1.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tip1.setCellValueFactory(new PropertyValueFactory<>("tip"));
        reloadMainTable();
        reloadSecondTable();
    }

    private void reloadSecondTable() throws BibliotecaException {
        Platform.runLater(()->{
            try {
        System.out.println(client);
        List<Carte> carti = server.getCartiDupaClient(this.client);
        for(Carte cartee: carti)
            System.out.println(cartee);
        //carti.add(carte);
        tabel1.getItems().setAll(carti);}
            catch (BibliotecaException e) {
                System.out.println("Reload table error " + e);
            }});
    }

    private void reloadMainTable() throws BibliotecaException {
        Platform.runLater(()->{
        try{
        Carte carte = new Carte(55, "a","a","a","a", "123");
        List<Carte> carti = server.getCarti();
        for(Carte cartee: carti)
            System.out.println(cartee);
        //carti.add(carte);
        tabel.getItems().setAll(carti);}
        catch (BibliotecaException e) {
            System.out.println("Reload table error " + e);
        }});

    }




    public void cartiInchiriate(ActionEvent actionEvent) throws IOException, BibliotecaException {
        System.out.println(client);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("clientReturn-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        System.out.println("hello");
        ClientReturnController clientController = fxmlLoader.getController();
        stage.setTitle("client");
        stage.setScene(scene);

        clientController.initialize(server, client);
    }

    public void getCarti(ActionEvent actionEvent) {
    }

    public void inchiriaza(ActionEvent actionEvent) throws BibliotecaException {
        Carte carte = tabel.getSelectionModel().getSelectedItem();
        System.out.println(client);
        Carte carte1 = new Carte(carte.getISBN(),carte.getTitlu(),carte.getAutor(),carte.getTip(),"indisponibila",this.client.getCnp());
        System.out.println(carte1);
        server.updateCarte(carte1);
        reloadMainTable();
    }

    @Override
    public void updateTables() throws BibliotecaException {
        reloadMainTable();
        reloadSecondTable();
    }


    public void returneaza(ActionEvent actionEvent) throws BibliotecaException {
        //server.
        Carte carte = tabel1.getSelectionModel().getSelectedItem();
        carte.setStatus("return");
        server.updateCarte(carte);
    }
}
