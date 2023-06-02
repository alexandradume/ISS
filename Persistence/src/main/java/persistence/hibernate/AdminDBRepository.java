package persistence.hibernate;

import model.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import persistence.AdminRepository;


import java.util.List;

@Repository
public class AdminDBRepository implements AdminRepository {

    private SessionFactory sessionFactory;

    public AdminDBRepository() {
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
    public Admin findOne(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                Query<Admin> query = session.createQuery("SELECT p FROM Admin p WHERE p.cnp=:cnp", Admin.class);
                query.setParameter("cnp", password);
                List<Admin> result = query.list();
                transaction.commit();
                return result.get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public void add(Admin elem) {

    }

    @Override
    public void update(String s, Admin elem) {

    }

    @Override
    public Iterable<Admin> findAll() {
        return null;
    }

    @Override
    public void delete(String s) {

    }
}

