package persistence;


import model.Admin;

public interface AdminRepository extends Repository<String, Admin>{

    abstract Admin findOne(String username, String password);

}
