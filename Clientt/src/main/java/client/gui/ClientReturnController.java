package client.gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Carte;
import model.Client;
import services.Service;
import services.BibliotecaException;

import java.util.List;

import static java.util.stream.StreamSupport.stream;

public class ClientReturnController {
    private Service server;
    @FXML
    private TextField usernameTF;

    @FXML
    private TextField parolaTF;

    @FXML
    private Button logIn;

    @FXML
    TableColumn<Carte,Integer> id;

    @FXML
    TableColumn<Carte,String> titlu;

    @FXML
    TableColumn<Carte,String> autor;

    @FXML
    TableColumn<Carte,String> tip;

    @FXML
    private TableView<Carte> tabel;



    private Client client;
    private final ObservableList<Carte> model = FXCollections.observableArrayList();

    public void initialize(Service server, Client client) throws BibliotecaException {
        this.server = server;
        this.client = client;
        id.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        autor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tip.setCellValueFactory(new PropertyValueFactory<>("tip"));
        reloadMainTable();
    }

    private void reloadMainTable() throws BibliotecaException {
        System.out.println(client);
        List<Carte> carti = server.getCartiDupaClient(this.client);
        for(Carte cartee: carti)
            System.out.println(cartee);
        //carti.add(carte);
        tabel.getItems().setAll(carti);
    }



}
