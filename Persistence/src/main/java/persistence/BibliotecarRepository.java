package persistence;


import model.Bibliotecar;

public interface BibliotecarRepository extends Repository<String, Bibliotecar> {

    abstract Bibliotecar findOne(String username, String password);

}