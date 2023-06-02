package client.gui;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Carte;
import model.Client;
import services.Observer;
import services.Service;
import services.BibliotecaException;

import java.util.List;


public class AdminController implements Observer {
    private Service server;
    @FXML
    private TextField cnpTF;
    @FXML
    private TextField numeTF;
    @FXML
    private TextField prenumeTF;
    @FXML
    private TextField genTF;
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

    @FXML
    private TextField idTF;

    @FXML
    private TextField titluTF;
    @FXML
    private TextField autorTF;

    @FXML
    private TextField tipTF;



    private final ObservableList<Carte> model = FXCollections.observableArrayList();

    public void initialize(Service server) throws BibliotecaException {
       this.server = server;
        id.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        autor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tip.setCellValueFactory(new PropertyValueFactory<>("tip"));
        reloadMainTable();
    }

    private void reloadMainTable() throws BibliotecaException {
        Platform.runLater(()->{
            try {
                List<Carte> carti = server.getCarti();
                tabel.getItems().setAll(carti);
                //System.out.println(seats.size());
            } catch (BibliotecaException e) {
                System.out.println("Reload table error " + e);
            }});
    }

    public void creaazaCont(ActionEvent actionEvent) throws BibliotecaException {
        String cnp = this.cnpTF.getText();
        String nume = this.numeTF.getText();
        String prenume = this.prenumeTF.getText();
        String tip = this.genTF.getText();
        Client client = new Client(cnp,nume,prenume,tip);
        server.addClient(client);

    }

    public void adaugaCarte(ActionEvent actionEvent) throws BibliotecaException {
       Integer ISBN  = Integer.valueOf(this.idTF.getText());
       String titlu = titluTF.getText();
       String autor = autorTF.getText();
       String tip = tipTF.getText();
       Carte carte = new Carte(ISBN,titlu,autor,tip,"disponibila","");
       System.out.println(carte);
       server.addCarte(carte);
       //reloadMainTable();
    }

    public void stergeCarte(ActionEvent actionEvent) throws BibliotecaException {
        Carte carte = tabel.getSelectionModel().getSelectedItem();
        System.out.println(carte);
        server.deleteCarte(carte);
        //reloadMainTable();
    }

    public void modificaCarte(ActionEvent actionEvent) throws BibliotecaException {
        Carte carte = tabel.getSelectionModel().getSelectedItem();
        String tip = tipTF.getText();
        carte.setTip(tip);
        System.out.println(carte);
        server.updateCarte(carte);
        //reloadMainTable();

    }

    @Override
    public void updateTables() throws BibliotecaException {
        reloadMainTable();
    }


}
