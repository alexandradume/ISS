package persistence.hibernate;

import model.Bibliotecar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import persistence.BibliotecarRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BibliotecarDBRepository implements BibliotecarRepository {

    private SessionFactory sessionFactory;

    public BibliotecarDBRepository() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }


    @Override
    public void add(Bibliotecar elem) {

    }

    @Override
    public void update(String s, Bibliotecar elem) {

    }

    @Override
    public Iterable<Bibliotecar> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Bibliotecar> criteriaQuery = criteriaBuilder.createQuery(Bibliotecar.class);
            Root<Bibliotecar> root = criteriaQuery.from(Bibliotecar.class);
            criteriaQuery.select(root);
            Query<Bibliotecar> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    @Override
    public void delete(String s) {

    }


    @Override
    public Bibliotecar findOne(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                Query<Bibliotecar> query = session.createQuery("SELECT p FROM Bibliotecar p WHERE p.cnp=:cnp", Bibliotecar.class);
                query.setParameter("cnp", password);
                List<Bibliotecar> result = query.list();
                transaction.commit();
                return result.get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
