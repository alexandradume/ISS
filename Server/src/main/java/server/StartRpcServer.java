package server;

import networking.utils.AbstractServer;
import networking.utils.ServerException;
import networking.utils.TransportRpcConcurrentServer;

import persistence.hibernate.AdminDBRepository;
import persistence.hibernate.BibliotecarDBRepository;
import persistence.hibernate.CarteDBRepository;
import persistence.hibernate.ClientDBRepository;
import services.Service;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }

        ClientDBRepository clientRepo = new ClientDBRepository();
        BibliotecarDBRepository bibliotecarRepo = new BibliotecarDBRepository();
        AdminDBRepository adminRepo = new AdminDBRepository();
        CarteDBRepository carteRepo = new CarteDBRepository();
        /*!!!!!*/
        Service musicFestivalServerImpl=new ServiceImpl(clientRepo, bibliotecarRepo,adminRepo, carteRepo);
        int musicFestivalServerPort=defaultPort;
        try {
            musicFestivalServerPort = Integer.parseInt(serverProps.getProperty("transport.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+musicFestivalServerPort);
        AbstractServer server = new TransportRpcConcurrentServer(musicFestivalServerPort, musicFestivalServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
