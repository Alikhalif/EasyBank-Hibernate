package org.youcode.easybank_hibernate.dao.daoImpl;

import org.youcode.easybank_hibernate.dao.ClientDao;
import org.youcode.easybank_hibernate.entities.Client;
import org.youcode.easybank_hibernate.utils.JPAUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClientDaoImpl implements ClientDao {

    private final EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    @Override
    public Optional<Client> create(Client client) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(client);
            transaction.commit();
            return Optional.of(client);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Client> update(Client client) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Client existingClient = em.find(Client.class, client.getCode());

            if (existingClient != null) {
                existingClient.setLastName(client.getLastName());
                existingClient.setFirstName(client.getFirstName());
                existingClient.setBirthDate(client.getBirthDate());
                existingClient.setPhone(client.getPhone());
                existingClient.setAddress(client.getAddress());
                existingClient.setEmployee(client.getEmployee());

                em.merge(existingClient);
                transaction.commit();
                return Optional.of(existingClient);
            } else {
                transaction.rollback();
                return Optional.empty();
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public Optional<Client> findByID(Integer id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Client client = em.find(Client.class, id);
            transaction.commit();

            return Optional.ofNullable(client);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public List<Client> getAll() {
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c JOIN FETCH c.employee", Client.class);
        return query.getResultList();
    }

    @Override
    public boolean delete(Integer id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Client client = em.find(Client.class, id);
            if (client != null) {
                em.remove(client);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Client> findByAnyAttribute(String attribute) {
        return null;
    }
}
