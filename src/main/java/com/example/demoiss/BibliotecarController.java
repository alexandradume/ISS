package com.example.demoiss;

import com.example.demoiss.model.Carte;
import com.example.demoiss.model.Client;
import com.example.demoiss.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

public class BibliotecarController {
    private Service service;
    @FXML
    private TextField usernameTF;

    @FXML
    private TextField parolaTF;

    @FXML
    private Button button;

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


    public void initialize() {
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        /*ClientRepository clientRepo=new ClientDBRepository(props);
        BibliotecarRepository bibliotecarRepo=new BibliotecarDBRepository(props);
        AdminRepository adminRepo=new AdminDBRepository(props);
        CarteRepository carteRepository = new CarteDBRepository(props);

        this.service = new Service(clientRepo,bibliotecarRepo,adminRepo,carteRepository);*/
        id.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        autor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tip.setCellValueFactory(new PropertyValueFactory<>("tip"));

    }
    private void initializareTabel() {


        Iterable<Carte> entity = service.getCartiInchiriateBibliotecar();
        List<Carte> pac = stream(entity.spliterator(), false).collect(Collectors.toList());
        model.setAll(pac);

    }

    public void setClass(Service service) {
        this.client = client;
        this.service = service;
        initializareTabel();
        tabel.setItems(model);
    }
}
