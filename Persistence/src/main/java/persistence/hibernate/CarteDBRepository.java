package persistence.hibernate;

import model.Carte;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import persistence.CarteRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarteDBRepository implements CarteRepository {

    private SessionFactory sessionFactory;

    public CarteDBRepository() {
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
    public void add(Carte elem) {
        System.out.println();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(elem);
            transaction.commit();
        } catch (Exception ex) {
            System.err.println("Error saving carte to the database: " + ex);
        }

    }

    @Override
    public void update(Integer id, Carte elem) {
        System.out.println("Am intrat");
        System.out.println(id);
            try (Session session = sessionFactory.openSession()) {
                // Retrieve the Carte with the given ID
                Carte existingCarte = session.get(Carte.class, id);

                if (existingCarte != null) {
                    // Update the existingCarte object with the new values
                    existingCarte.setClient(elem.getClient());
                    existingCarte.setTip(elem.getTip());
                    existingCarte.setStatus(elem.getStatus());
                    // Update other properties as needed

                    // Save the updated Carte
                    session.beginTransaction();
                    session.update(existingCarte);
                    session.getTransaction().commit();
                }
            }
        }


    @Override
    public Iterable<Carte> findAll() {

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Carte> criteriaQuery = criteriaBuilder.createQuery(Carte.class);
            Root<Carte> root = criteriaQuery.from(Carte.class);
            criteriaQuery.select(root);
            Query<Carte> query = session.createQuery(criteriaQuery);
            List<Carte> carti = query.getResultList();

            List<Carte> cartiNonNullClient = new ArrayList<>();
            for (Carte carte : carti) {
                System.out.println(carte.getClient());
                if (Objects.equals(carte.getClient(), "") || carte.getClient() == null) {
                    cartiNonNullClient.add(carte);
                }
            }

            return cartiNonNullClient;
        }
    }



    @Override
    public void delete(Integer integer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Carte carte = session.get(Carte.class, integer);
            if (carte != null) {
                session.delete(carte);
                transaction.commit();}
        } catch (Exception ex) {
            System.err.println("Error deleting carte from the database: " + ex);
        }

    }


    @Override
    public Carte findOne(Integer id) {
        return null;
    }

    @Override
    public Iterable<Carte> findAllByClient(String clientt) {

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Carte> criteriaQuery = criteriaBuilder.createQuery(Carte.class);
            Root<Carte> root = criteriaQuery.from(Carte.class);
            criteriaQuery.select(root);
            Query<Carte> query = session.createQuery(criteriaQuery);
            List<Carte> carti = query.getResultList();

            List<Carte> cartiNonNullClient = new ArrayList<>();
            for (Carte carte : carti) {
                System.out.println(carte.getClient());
                if (Objects.equals(carte.getClient(), clientt) && !Objects.equals(carte.getStatus(), "return")) {
                    cartiNonNullClient.add(carte);
                }
            }

            return cartiNonNullClient;
        }
    }

    @Override
    public Iterable<Carte> findAllInchiriate(String client) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Carte> criteriaQuery = criteriaBuilder.createQuery(Carte.class);
            Root<Carte> root = criteriaQuery.from(Carte.class);
            criteriaQuery.select(root);
            Query<Carte> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    @Override
    public Iterable<Carte> findAllInchiriateBibliotecar() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Carte> criteriaQuery = criteriaBuilder.createQuery(Carte.class);
            Root<Carte> root = criteriaQuery.from(Carte.class);
            criteriaQuery.select(root);
            Query<Carte> query = session.createQuery(criteriaQuery);
            List<Carte> carti = query.getResultList();

            List<Carte> cartiNonNullClient = new ArrayList<>();
            for (Carte carte : carti) {
                System.out.println(carte.getClient());
                if (Objects.equals(carte.getStatus(), "return")) {
                    cartiNonNullClient.add(carte);
                }
            }

            return cartiNonNullClient;
        }
    }
}
