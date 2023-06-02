package networking.utils;


import networking.rpcprotocol.TransportClientRpcWorker;
import services.Service;

import java.net.Socket;


public class TransportRpcConcurrentServer extends AbsConcurrentServer {
    private Service transportServer;
    public TransportRpcConcurrentServer(int port, Service musicFestivalServer) {
        super(port);
        this.transportServer = musicFestivalServer;
        //System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        System.out.println("hello");
        //ClientRpcWorkerJSON worker=new ClientRpcWorkerJSON(transportServer, client);
        TransportClientRpcWorker worker=new TransportClientRpcWorker(transportServer, client);
        //MusicFestivalClientRpcReflectionWorker worker=new ChatClientRpcReflectionWorker(musicFestivalServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
