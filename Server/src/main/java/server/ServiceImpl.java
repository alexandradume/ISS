package server;



import model.Admin;
import model.Bibliotecar;
import model.Carte;
import model.Client;
import persistence.hibernate.AdminDBRepository;
import persistence.hibernate.BibliotecarDBRepository;
import persistence.hibernate.CarteDBRepository;
import persistence.hibernate.ClientDBRepository;
import services.Observer;
import services.Service;
import services.BibliotecaException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    private ClientDBRepository clientRepository;

    private AdminDBRepository adminRepository;

    private BibliotecarDBRepository bibliotecarRepository;

    private CarteDBRepository carteRepository;

    private Map<String, Observer> loggedClients;
    /*SeatRepository sRepo*/
    public ServiceImpl(ClientDBRepository clientRepository, BibliotecarDBRepository bibliotecarRepository, AdminDBRepository adminRepository, CarteDBRepository carteRepository) {

        this.clientRepository = clientRepository;
        this.bibliotecarRepository = bibliotecarRepository;
        this.adminRepository = adminRepository;
        this.carteRepository = carteRepository;
        //seatRepository = sRepo;
        loggedClients = new ConcurrentHashMap<>();
    }


    private final int defaultThreadsNo = 5;

    @Override
    public  synchronized int verifyType(Client person, Observer client) throws BibliotecaException {
        System.out.println("I was there");
        System.out.println(person.getGen());
        if (loggedClients.get(person.getCnp()) != null)
            throw new BibliotecaException("User already logged in.");
        loggedClients.put(person.getCnp(), client);
       return 1;
    }


    @Override
    public Client findOneClient(Client client) throws BibliotecaException {
        return clientRepository.findOne(client.getNume(),client.getCnp());
    }

    @Override
    public Bibliotecar findOneBibliotecar(Bibliotecar bibliotecar) {
        return bibliotecarRepository.findOne(bibliotecar.getNume(),bibliotecar.getCnp());
    }

    @Override
    public Admin findOneAdmin(Admin admin) {
        return adminRepository.findOne(admin.getNume(),admin.getCnp());
    }

    @Override
    public List<Carte> getCarti() {
        return (List<Carte>) carteRepository.findAll();
    }

    @Override
    public List<Carte> getCartiBibliotecar() {
        return (List<Carte>) carteRepository.findAllInchiriateBibliotecar();
    }

    @Override
    public List<Carte> getCartiDupaClient(Client client) {
        return (List<Carte>) carteRepository.findAllByClient(client.getCnp());
    }

    @Override
    public void updateCarte(Carte carte) throws BibliotecaException {
        carteRepository.update(carte.getISBN(), carte);
        notifyClientsToUpdateTables();
    }

    @Override
    public void addClient(Client client) {
        this.clientRepository.add(client);
    }

    @Override
    public void addCarte(Carte carte) throws BibliotecaException {
        this.carteRepository.add(carte);
        notifyClientsToUpdateTables();
    }

    private void notifyClientsToUpdateTables() throws BibliotecaException {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for (Observer client : loggedClients.values()) {
            executor.execute(() -> {
                try {
                    client.updateTables();
                } catch (BibliotecaException e) {
                    System.err.println("Error notifying client to update tables " + e);
                }
            });
        }
        executor.shutdown();
    }

    @Override
    public void deleteCarte(Carte carte) throws BibliotecaException {
        this.carteRepository.delete(carte.getISBN());
        notifyClientsToUpdateTables();
    }

    @Override
    public void login(Client clientt, Observer spectacleController) {

    }

    @Override
    public int verifyBibliotecar(Bibliotecar bibliotecar, Observer bibliotecarController) throws BibliotecaException {
        System.out.println("I was there");
        System.out.println(bibliotecar.getGen());
        if (loggedClients.get(bibliotecar.getCnp()) != null)
            throw new BibliotecaException("User already logged in.");
        loggedClients.put(bibliotecar.getCnp(), bibliotecarController);
        return 1;
    }

    @Override
    public int verifyAdmin(Admin admin, Observer adminController) throws BibliotecaException {
        System.out.println(admin);
        if (loggedClients.get(admin.getCnp()) != null)
            throw new BibliotecaException("User already logged in.");
        loggedClients.put(admin.getCnp(), adminController);
        return 1;
    }


}
