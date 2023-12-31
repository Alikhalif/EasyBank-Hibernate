package org.youcode.easybank_hibernate.dao.daoImpl;

import org.youcode.easybank_hibernate.dao.EmployeeDao;
import org.youcode.easybank_hibernate.entities.Client;
import org.youcode.easybank_hibernate.entities.Employee;
import org.youcode.easybank_hibernate.utils.JPAUtil;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EmployeeDaoImpl implements EmployeeDao {

    private final EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    @Override
    @Transactional
    public Optional<Employee> create(Employee employee) {

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(employee);
            transaction.commit();
            return Optional.of(employee);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> update(Employee employee) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Employee existingEmployee = em.find(Employee.class, employee.getMatricule());

            if (existingEmployee != null) {
                existingEmployee.setLastName(employee.getLastName());
                existingEmployee.setFirstName(employee.getFirstName());
                existingEmployee.setBirthDate(employee.getBirthDate());
                existingEmployee.setPhone(employee.getPhone());
                existingEmployee.setAddress(employee.getAddress());
                existingEmployee.setRecruitmentDate(employee.getRecruitmentDate());
                existingEmployee.setEmail(employee.getEmail());


                em.merge(existingEmployee);
                transaction.commit();
                return Optional.of(existingEmployee);
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
    public Optional<Employee> findByID(Integer id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Employee employee = em.find(Employee.class, id);
            transaction.commit();

            return Optional.ofNullable(employee);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Employee> getAll() {
        TypedQuery<Employee> query = em.createQuery("SELECT c FROM Employee c", Employee.class);
        return query.getResultList();
    }

    @Override
    public boolean delete(Integer id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Employee employee = em.find(Employee.class, id);
            if (employee != null) {
                em.remove(employee);
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
