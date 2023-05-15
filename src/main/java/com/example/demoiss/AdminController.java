package com.example.demoiss;

import com.example.demoiss.model.Carte;
import com.example.demoiss.repository.*;
import com.example.demoiss.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

public class AdminController {
    private Service service;
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

    public void initialize() {
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        ClientRepository clientRepo=new ClientDBRepository(props);
        BibliotecarRepository bibliotecarRepo=new BibliotecarDBRepository(props);
        AdminRepository adminRepo=new AdminDBRepository(props);
        CarteRepository carteRepository = new CarteDBRepository(props);

        this.service = new Service(clientRepo,bibliotecarRepo,adminRepo,carteRepository);
        id.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        autor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tip.setCellValueFactory(new PropertyValueFactory<>("tip"));

        initializeazaTabel();

    }

    private void initializeazaTabel() {


        Iterable<Carte> entity = service.getCarti();
        List<Carte> pac = stream(entity.spliterator(), false).collect(Collectors.toList());
        model.setAll(pac);
        tabel.setItems(model);
    }

    public void creaazaCont(ActionEvent actionEvent) {
        System.out.println(cnpTF.getText() + numeTF.getText() + prenumeTF.getText() + genTF.getText());
        String username = numeTF.getText() +  " " +prenumeTF.getText();
        if(service.getClient(username, cnpTF.getText()) == null)
            service.adaugaCont(cnpTF.getText(), numeTF.getText(),prenumeTF.getText(),genTF.getText());
    }

    public void adaugaCarte(ActionEvent actionEvent) {
        if(!Objects.equals(idTF.getText(), "") && !Objects.equals(titluTF.getText(), "") && !Objects.equals(autor.getText(), "") && !Objects.equals(tipTF.getText(), "")){
        int id = Integer.parseInt(idTF.getText());
        service.adaugaCarte(id,titluTF.getText(),autorTF.getText(),tipTF.getText(), "disponibila", null);
        initializeazaTabel();
        }
    }

    public void setClass(Service service) {
        this.service = service;
    }
}
