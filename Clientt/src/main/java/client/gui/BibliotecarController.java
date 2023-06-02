package client.gui;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Carte;
import services.Observer;
import services.Service;
import services.BibliotecaException;

import java.util.List;

import static java.util.stream.StreamSupport.stream;

public class BibliotecarController implements Observer {
    private Service server;
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

    public void initialize(Service server) throws BibliotecaException {
        this.server = server;
        id.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        autor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tip.setCellValueFactory(new PropertyValueFactory<>("tip"));
        reloadMainTable();
    }

    private void reloadMainTable() throws BibliotecaException {
        /*List<Carte> carti = server.getCartiBibliotecar();
        for(Carte cartee: carti)
            System.out.println(cartee);
        //carti.add(carte);
        tabel.getItems().setAll(carti);*/

        Platform.runLater(()->{
            try {
                List<Carte> carti = server.getCartiBibliotecar();
                tabel.getItems().setAll(carti);
                //System.out.println(seats.size());
            } catch (BibliotecaException e) {
                System.out.println("Reload table error " + e);
            }});
    }

    public void returneaza(ActionEvent actionEvent) throws BibliotecaException {
        Carte carte = tabel.getSelectionModel().getSelectedItem();
        carte.setStatus("disponibila");
        carte.setClient("");
        server.updateCarte(carte);
        //reloadMainTable();

    }

    @Override
    public void updateTables() throws BibliotecaException {
        reloadMainTable();
    }

    //private Client client;



    /*private final ObservableList<Carte> model = FXCollections.observableArrayList();


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

        //this.service = new Service(clientRepo,bibliotecarRepo,adminRepo,carteRepository);*/
        /*id.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
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
    }*/
}
