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


public class TransportClientRpcWorker implements Runnable, Observer {
    private Service server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public TransportClientRpcWorker(Service server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request) {
        Response response = null;
        /*if (request.type() == RequestType.VERIFY_TYPE) {
            System.out.println("Login request ..." + request.type());
            Person person = (Person) request.data();
            try {
                server.verifyType(person,this);
                return okResponse;
            } catch (TransportException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.GET_ALL_RIDES){
            System.out.println("GET ALL RIDES ...");
            try {
                List<Ride> rides =  server.getAllRides();
                return new Response.Builder().type(ResponseType.OK).data(rides).build();
            } catch (RuntimeException | TransportException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }*/

        if (request.type()== RequestType.GET_PERSON){
            System.out.println("GET PERSON ...");
            try {
                Client ride = (Client) request.data();
                Client ridee =  server.findOneClient(ride);
                return new Response.Builder().type(ResponseType.OK).data(ridee).build();
            } catch (RuntimeException | BibliotecaException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type()== RequestType.GET_BIBLIOTECAR){
            System.out.println("GET BIBLIOTECAR ...");
            try {
                Bibliotecar person = (Bibliotecar) request.data();
                Bibliotecar personn =  server.findOneBibliotecar(person);
                return new Response.Builder().type(ResponseType.OK).data(personn).build();
            } catch (RuntimeException | BibliotecaException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type()== RequestType.GET_ADMIN){
            System.out.println("GET ADMIN ...");
            try {
                Admin person = (Admin) request.data();
                Admin personn =  server.findOneAdmin(person);
                return new Response.Builder().type(ResponseType.OK).data(personn).build();
            } catch (RuntimeException | BibliotecaException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type()== RequestType.GET_CARTI){
            System.out.println("GET CARTI ...");
            try {
                List<Carte> rides =  server.getCarti();
                return new Response.Builder().type(ResponseType.OK).data(rides).build();
            } catch (RuntimeException | BibliotecaException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.GET_CARTI_BIBLIOTECAR){
            System.out.println("GET CARTI ...");
            try {
                List<Carte> rides =  server.getCartiBibliotecar();
                return new Response.Builder().type(ResponseType.OK).data(rides).build();
            } catch (RuntimeException | BibliotecaException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.GET_CARTI_DUPA_CLIENT){
            System.out.println("GET SEATS ...");
            try {
                Client client = (Client) request.data();
                List<Carte> seats =  server.getCartiDupaClient(client);
                return new Response.Builder().type(ResponseType.OK).data(seats).build();
            } catch (RuntimeException | BibliotecaException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.UPDATE_CARTE){
            System.out.println("UPDATE CARTE ...");
            try {
                Carte carte = (Carte) request.data();
                System.out.println(carte);
                server.updateCarte(carte);
                return new Response.Builder().type(ResponseType.OK).build();
            } catch (RuntimeException | BibliotecaException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.ADD_CLIENT){
            System.out.println("ADD CLIENT ...");
            try {
                Client client = (Client) request.data();
                System.out.println(client);
                server.addClient(client);
                return new Response.Builder().type(ResponseType.OK).build();
            } catch (RuntimeException | BibliotecaException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.ADD_CARTE){
            System.out.println("ADD CARTE ...");
            try {
                Carte carte = (Carte) request.data();
                System.out.println(carte);
                server.addCarte(carte);
                return new Response.Builder().type(ResponseType.OK).build();
            } catch (RuntimeException | BibliotecaException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type()== RequestType.DELETE_CARTE){
            System.out.println("DELETE CARTE ...");
            try {
                Carte carte = (Carte) request.data();
                System.out.println(carte);
                server.deleteCarte(carte);
                return new Response.Builder().type(ResponseType.OK).build();
            } catch (RuntimeException | BibliotecaException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.LOG_IN_CLIENT) {
            System.out.println("Login request ..." + request.type());
            Client client = (Client) request.data();
            try {
                server.verifyType(client, this);
                return okResponse;
            } catch (BibliotecaException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.LOG_IN_BIBLIOTECAR) {
            System.out.println("Login request ..." + request.type());
            Bibliotecar bibliotecar = (Bibliotecar) request.data();
            try {
                server.verifyBibliotecar(bibliotecar, this);
                return okResponse;
            } catch (BibliotecaException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.LOG_IN_ADMIN) {
            System.out.println("Login request ..." + request.type());
            Admin admin = (Admin) request.data();
            System.out.println(admin);
            try {
                server.verifyAdmin(admin, this);
                return okResponse;
            } catch (BibliotecaException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }



        return response;
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void updateTables() throws BibliotecaException {
        Response resp=new Response.Builder().type(ResponseType.UPDATE_TABLES).build();
        System.out.println("Update tables ");
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
