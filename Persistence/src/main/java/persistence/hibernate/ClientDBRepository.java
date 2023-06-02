package persistence.hibernate;



import model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import persistence.ClientRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ClientDBRepository implements ClientRepository {

    private SessionFactory sessionFactory;

    public ClientDBRepository() {
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
    public void add(Client elem) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(elem);
            transaction.commit();
        } catch (Exception ex) {
            System.err.println("Error saving client to the database: " + ex);
        }

    }

    @Override
    public void update(String s, Client elem) {

    }

    @Override
    public Iterable<Client> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);
            criteriaQuery.select(root);
            Query<Client> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }


    @Override
    public void delete(String s) {

    }






    @Override
    public Client findOne(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                Query<Client> query = session.createQuery("SELECT p FROM Client p WHERE p.cnp=:cnp", Client.class);
                query.setParameter("cnp", password);
                List<Client> result = query.list();
                transaction.commit();
                return result.get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
