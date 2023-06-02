package persistence;


import model.Carte;

public interface CarteRepository extends Repository<Integer, Carte>{

    abstract Carte findOne(Integer id);
    Iterable<Carte> findAllByClient(String clientt);
    Iterable<Carte> findAllInchiriate(String client);


    Iterable<Carte> findAllInchiriateBibliotecar();
}
