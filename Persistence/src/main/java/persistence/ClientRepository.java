package persistence;


import model.Client;

@org.springframework.stereotype.Repository
public interface ClientRepository extends Repository<String, Client>{

    abstract Client findOne(String username, String password);

}



