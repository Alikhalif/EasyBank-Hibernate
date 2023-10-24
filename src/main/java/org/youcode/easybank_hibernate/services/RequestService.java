package org.youcode.easybank_hibernate.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.youcode.easybank_hibernate.dao.RequestDao;
import org.youcode.easybank_hibernate.entities.Request;
import org.youcode.easybank_hibernate.enums.STATE;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RequestService {

    @Inject
    private RequestDao requestDao;

    public Request createRequest(Request request) throws Exception{
        if (request == null) {
            throw new Exception("Employee cannot be null");
        }else {
            return requestDao.create(request).get();
        }
    }

    public Request getRequestByID(Integer number) {
        Optional<Request> retrievedRequest = requestDao.findByID(number);
        return retrievedRequest.orElse(null);
    }

    public List<Request> getAllRequests() {
        return requestDao.getAll();
    }

    public boolean updateRequestState(Integer number, STATE state) {
        if (number.toString().isEmpty() || requestDao.findByID(number).isEmpty()) {
            return false;
        }else {
            return requestDao.updateState(number, state);
        }
    }

    public boolean deleteRequest(Integer id) {
        if (id.toString().isEmpty() || requestDao.findByID(id).isEmpty()) {
            return false;
        }else {
            return requestDao.delete(id);
        }
    }

}
