package org.youcode.easybank_hibernate.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.youcode.easybank_hibernate.dao.daoImpl.RequestDaoImpl;
import org.youcode.easybank_hibernate.entities.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RequestServiceTest {
    @Mock
    private RequestDaoImpl requestDao;
    private RequestService requestService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        requestService = new RequestService();
        requestService.setRequestService(requestDao);
    }

    @Test
    public void testCreateRequest(){
        Request request = new Request();

        when(requestDao.create(request)).thenReturn(Optional.of(request));
        try{
            Request requestCreate = requestService.createRequest(request);
            assertSame(request, requestCreate);
        }catch (Exception e){
            fail("Exception should not be thrown");
        }
    }


    @Test
    public void testFinfByIdRequest(){
        Request request = new Request();
        int id = 1;

        when(requestDao.findByID(id)).thenReturn(Optional.of(request));
        Request requestFind = requestService.getRequestByID(id);
        assertSame(request, requestFind);
        verify(requestDao).findByID(id);
    }


    @Test
    public void testAllRequest(){
        List<Request> requests = new ArrayList<>();
        requests.add(new Request());
        requests.add(new Request());

        when(requestDao.getAll()).thenReturn(requests);
        List<Request> requests1 = requestService.getAllRequests();
        assertSame(requests, requests1);
    }


    @Test
    public void testDeleteRequest(){
        int id = 1;

        when(requestDao.findByID(id)).thenReturn(Optional.of(new Request()));
        when(requestDao.delete(id)).thenReturn(true);

        Boolean result = requestService.deleteRequest(id);
        verify(requestDao).findByID(id);
        verify(requestDao).delete(id);

        assertTrue(result);
    }
}
