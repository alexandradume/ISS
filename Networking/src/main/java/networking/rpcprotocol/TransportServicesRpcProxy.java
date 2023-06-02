package networking.rpcprotocol;

import model.Admin;
import model.Bibliotecar;
import model.Carte;
import model.Client;
import services.Observer;
import services.Service;
import services.BibliotecaException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class TransportServicesRpcProxy implements Service {
    private String host;
    private int port;

    private Observer client;

    private Observer bibliotecar;



    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    //private ITransportObserver client;



    public TransportServicesRpcProxy(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }







    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws BibliotecaException {
        try {
            System.out.println(request);
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new BibliotecaException("Error sending object " + e);
        }

    }

    private Response readResponse() throws BibliotecaException {
        Response response = null;
        try {

            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws BibliotecaException {
        try {
            System.out.println(host);
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            System.out.println(output);
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }





    private void handleUpdate(Response response) {
        if (response.type() == ResponseType.UPDATE_TABLES) {
            System.out.println("Updating tables.. ");
            try {
                client.updateTables();
            } catch (BibliotecaException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.UPDATE_TABLES;
    }

    @Override
    public int verifyType(Client client, Observer clientt) throws BibliotecaException {
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.LOG_IN_CLIENT).data(client).build();
        System.out.println(client);
        System.out.println(req);
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            System.out.println("Hello");
            this.client = clientt;
            if (client == null)
                System.out.println("Null");
            return 1;

        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            closeConnection();
            System.out.println("Error");
            throw new BibliotecaException(err);
        }
        return 0;
    }

    @Override
    public Client findOneClient(Client client) throws BibliotecaException {
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.GET_PERSON).data(client).build();
        System.out.println(req);
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new BibliotecaException(err);
        }
        return (Client) response.data();
    }

    @Override
    public Bibliotecar findOneBibliotecar(Bibliotecar bibliotecar) throws BibliotecaException {
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.GET_BIBLIOTECAR).data(bibliotecar).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new BibliotecaException(err);
        }
        return (Bibliotecar) response.data();
    }

    @Override
    public Admin findOneAdmin(Admin admin) throws BibliotecaException {
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.GET_ADMIN).data(admin).build();
        System.out.println(req);
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new BibliotecaException(err);
        }
        return (Admin) response.data();
    }

    @Override
    public List<Carte> getCarti() throws BibliotecaException {
        Request req = new Request.Builder().type(RequestType.GET_CARTI).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new BibliotecaException(err);
        }
        return (List<Carte>) response.data();
    }

    @Override
    public List<Carte> getCartiBibliotecar() throws BibliotecaException {
        Request req = new Request.Builder().type(RequestType.GET_CARTI_BIBLIOTECAR).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new BibliotecaException(err);
        }
        return (List<Carte>) response.data();
    }

    @Override
    public List<Carte> getCartiDupaClient(Client client) throws BibliotecaException {
        Request req = new Request.Builder().type(RequestType.GET_CARTI_DUPA_CLIENT).data(client).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new BibliotecaException(err);
        }
        return (List<Carte>) response.data();
    }

    @Override
    public void updateCarte(Carte carte) throws BibliotecaException {
        System.out.println("Proxy");
        System.out.println(carte);
        Request req = new Request.Builder().type(RequestType.UPDATE_CARTE).data(carte).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new BibliotecaException(err);
        }

    }

    @Override
    public void addClient(Client client) throws BibliotecaException {
        System.out.println("Proxy");
        System.out.println(client);
        Request req = new Request.Builder().type(RequestType.ADD_CLIENT).data(client).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new BibliotecaException(err);
        }

    }

    @Override
    public void addCarte(Carte carte) throws BibliotecaException {
        System.out.println("Proxy");
        System.out.println(carte);
        Request req = new Request.Builder().type(RequestType.ADD_CARTE).data(carte).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new BibliotecaException(err);
        }
    }

    @Override
    public void deleteCarte(Carte carte) throws BibliotecaException {
        System.out.println("Proxy");
        System.out.println(carte);
        Request req = new Request.Builder().type(RequestType.DELETE_CARTE).data(carte).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new BibliotecaException(err);
        }
    }

    @Override
    public void login(Client clientt, Observer spectacleController) {

    }

    @Override
    public int verifyBibliotecar(Bibliotecar bibliotecar, Observer bibliotecarController) throws BibliotecaException {
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.LOG_IN_BIBLIOTECAR).data(bibliotecar).build();
        System.out.println(bibliotecar);
        System.out.println(req);
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            System.out.println("Hello");
            this.client = bibliotecarController;
            if (bibliotecar == null)
                System.out.println("Null");
            return 1;

        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            closeConnection();
            System.out.println("Error");
            throw new BibliotecaException(err);
        }
        return 0;
    }

    @Override
    public int verifyAdmin(Admin admin, Observer adminController) throws BibliotecaException {
        initializeConnection();
        Request req = new Request.Builder().type(RequestType.LOG_IN_ADMIN).data(admin).build();
        System.out.println(admin);
        System.out.println(req);
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            System.out.println("Hello");
            this.client = adminController;
            if (bibliotecar == null)
                System.out.println("Null");
            return 1;

        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            closeConnection();
            System.out.println("Error");
            throw new BibliotecaException(err);
        }
        return 0;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {
                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }



}
