package networking.rpcprotocol;

import networking.utils.JSONUtils;
import services.Observer;
import services.Service;
import services.BibliotecaException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRpcWorkerJSON implements Runnable, Observer {
    private Service server;
    private Socket connection;
//    private ObjectInputStream input;
//    private ObjectOutputStream output;

    private BufferedReader input;
    private PrintWriter output;
    private volatile boolean connected;

    public ClientRpcWorkerJSON(Service server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
//            input = new ObjectInputStream(connection.getInputStream());
//            output = new ObjectOutputStream(connection.getOutputStream());
//            output.flush();
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            output = new PrintWriter(connection.getOutputStream(), true);


            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                String json = input.readLine();
                System.out.println("Request:");
                System.out.println(json);
                Request request = JSONUtils.getRequest(json);

                assert request != null;
                Response response = handleRequest(request);
                if (response != null) {
                    System.out.println("Response: ");
                    System.out.println(response);
                    System.out.println("ai trimis");
                    sendResponse(response);
                }
            } catch (IOException e) {
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
        /*if (request.type() == RequestType.LOGIN) {
            System.out.println("Login request ..." + request.type());
            Person person = (Person) request.data();
            try {
                server.verifyType(person, this);
                return okResponse;
            } catch (TransportException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.LOGOUT) {
            System.out.println("Logout request");
            // LogoutRequest logReq=(LogoutRequest)request;
            Person person = (Person) request.data();
            try {
                server.logout(person, this);
                connected = false;
                return okResponse;

            } catch (TransportException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.GET_ALL_RIDES) {
            System.out.println("GET ALL SPECTACLES ...");
            try {
                List<Ride> spectacles = server.getAllRides();
                return new Response.Builder().type(ResponseType.OK).data(spectacles).build();
            } catch (RuntimeException | TransportException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.GET_RIDE) {
            System.out.println("GET ALL SPECTACLES BY DATE...");
            Ride ridee = (Ride) request.data();
            try {
                Ride rider  = server.findOneRide(ridee);
                Ride ride = new Ride(3,"Bali","2023-09-09","12:00:00", 3);
                System.out.println("Client");
                System.out.println(rider);
                return new Response.Builder().type(ResponseType.OK_RIDE).data(rider).build();
            } catch (RuntimeException | TransportException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.GET_SEATS) {
            System.out.println("GET ALL SEATS ...");
            Ride ride = (Ride) request.data();
            try {
                List<Seat> seats = server.getAllSeats(ride);
                return new Response.Builder().type(ResponseType.OK_SEATS).data(seats).build();
            } catch (RuntimeException | TransportException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.GET_PERSON) {
            System.out.println("GET PERSON...");
            Person person = (Person) request.data();
            try {
                Person personn  = server.findOnePerson(person);
                System.out.println("Client");
                return new Response.Builder().type(ResponseType.OK_PERSON).data(personn).build();
            } catch (RuntimeException | TransportException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }*/
        /*if (request.type() == RequestType.GET_PERSON) {
            System.out.println("GET PERSON...");
            Person personn = (Person) request.data();
            try {
                Person person  = server.findOnePerson(personn);
                System.out.println("Client");
                System.out.println(person);
                return new Response.Builder().type(ResponseType.OK_PERSON).data(person).build();
            } catch (RuntimeException | TransportException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.UPDATE_SEATS) {
            System.out.println("UPDATING SEATS...");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            Seat seat = (Seat) request.data();
            try {
                server.updateSeat(seat);
                return new Response.Builder().type(ResponseType.OK).build();
            } catch (RuntimeException | TransportException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.GET_MINIM) {
            System.out.println("Login request ..." + request.type());
            try {
                int minim = server.getMinim();
                return new Response.Builder().type(ResponseType.OK_MINIM).data(minim).build();
            } catch (TransportException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }*/
        return response;
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        String json = response.toJsonString();
        System.out.println(json);
        output.println(json);
    }

    @Override
    public void updateTables() throws BibliotecaException {
        Response resp = new Response.Builder().type(ResponseType.UPDATE_TABLES).build();
        System.out.println("Update tables ");
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
