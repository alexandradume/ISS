package persistence;


import model.Enity;


public interface Repository<ID, E extends Enity<ID>> {

    void add(E elem);
    void update(ID id, E elem);
    Iterable<E> findAll();
    void delete(ID id);
}

