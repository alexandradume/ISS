package services;




import model.Admin;
import model.Bibliotecar;
import model.Carte;
import model.Client;

import java.util.List;

public interface Service {
    public int verifyType(Client client, Observer clientt) throws BibliotecaException;
    Client findOneClient(Client client) throws BibliotecaException;

    Bibliotecar findOneBibliotecar(Bibliotecar bibliotecar) throws BibliotecaException;

    Admin findOneAdmin(Admin admin) throws BibliotecaException;

    List<Carte> getCarti() throws BibliotecaException;

    List<Carte> getCartiBibliotecar() throws BibliotecaException;

    List<Carte> getCartiDupaClient(Client client) throws BibliotecaException;

    void updateCarte(Carte carte) throws BibliotecaException;

    void addClient(Client client) throws BibliotecaException;

    void addCarte(Carte carte) throws BibliotecaException;

    void deleteCarte(Carte carte) throws BibliotecaException;

    void login(Client clientt, Observer spectacleController);

    int verifyBibliotecar(Bibliotecar bibliotecar, Observer bibliotecarController) throws BibliotecaException;

    int verifyAdmin(Admin admin, Observer adminController) throws BibliotecaException;
}
