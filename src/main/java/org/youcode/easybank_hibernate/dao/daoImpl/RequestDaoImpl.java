package org.youcode.easybank_hibernate.dao.daoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.youcode.easybank_hibernate.dao.RequestDao;
import org.youcode.easybank_hibernate.entities.Client;
import org.youcode.easybank_hibernate.entities.Request;
import org.youcode.easybank_hibernate.enums.STATE;
import jakarta.enterprise.context.ApplicationScoped;
import org.youcode.easybank_hibernate.utils.JPAUtil;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RequestDaoImpl implements RequestDao {

    private final EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    @Override
    public Optional<Request> create(Request request) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(request);
            transaction.commit();
            return Optional.of(request);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Request> update(Request request) {
        return Optional.empty();
    }

    @Override
    public Optional<Request> findByID(Integer id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Request request = em.find(Request.class, id);
            transaction.commit();

            return Optional.ofNullable(request);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Request> getAll() {
        TypedQuery<Request> query = em.createQuery("SELECT c FROM Request c JOIN FETCH c.client", Request.class);
        return query.getResultList();
    }

    @Override
    public boolean delete(Integer id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Request request = em.find(Request.class, id);
            if (request != null) {
                em.remove(request);
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
    public boolean updateState(Integer id, STATE state) {
        return false;
    }
}
