package org.youcode.easybank_hibernate.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.youcode.easybank_hibernate.dao.daoImpl.ClientDaoImpl;
import org.youcode.easybank_hibernate.entities.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientServiceTest {
    @Mock
    private ClientDaoImpl clientDao;
    private ClientService clientService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        clientService = new ClientService();
        clientService.setClientDao(clientDao);
    }

    @Test
    public void testCreateClient() {
        Client client = new Client();

        when(clientDao.create(client)).thenReturn(Optional.of(client));

        try {
            Client createdClient = clientService.createClient(client);
            assertSame(client, createdClient);
        }catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testCreateNullClient() {
        try {
            clientService.createClient(null);
            fail("Exception should be thrown for a null employee");
        }catch (Exception e) {
            assertEquals("Client cannot be null", e.getMessage());
        }
    }

    @Test
    public void testFindClientByID() {
        Client client = new Client();

        int clientID = 1;

        when(clientDao.findByID(clientID)).thenReturn(Optional.of(client));
        try {
            Client foundClient = clientService.findClientByID(clientID);
            assertSame(foundClient, client);
        }catch (Exception e) {
            fail("Exception should not be thrown");
        }

        verify(clientDao).findByID(clientID);
    }

    @Test
    public void testFindClientByInvalidID() {
        int invalidClientID = -1;
        try {
            clientService.findClientByID(invalidClientID);
            fail("Invalid ID exception");
        }catch (Exception e) {
            assertEquals("Client id cannot be empty or less than zero", e.getMessage());
        }
    }

    @Test
    public void testGetAllClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());
        clients.add(new Client());

        when(clientDao.getAll()).thenReturn(clients);
        List<Client> result = clientService.getAllClients();
        assertEquals(clients, result);
    }


    @Test
    public void testDeleteClientWithValidID() {
        int id = 1;

        when(clientDao.findByID(id)).thenReturn(Optional.of(new Client()));
        when(clientDao.delete(id)).thenReturn(true);
        boolean result = clientService.deleteClient(id);

        verify(clientDao).findByID(id);
        verify(clientDao).delete(id);

        assertTrue(result);
    }

    @Test
    public void testDeleteNonExistingClient(){
        int id = -1;

        when(clientDao.findByID(id)).thenReturn(Optional.empty());

        boolean result = clientService.deleteClient(id);

        verify(clientDao).findByID(id);

        assertFalse(result);
    }


    @Test
    public void testUpdateClient(){
        Client client = new Client();
        client.setCode(1);

        when(clientDao.findByID(1)).thenReturn(Optional.of(client));
        when(clientDao.update(client)).thenReturn(Optional.of(client));

        try{
            Client updatedClient = clientService.updateClient(client);
            verify(clientDao).findByID(1);
            verify(clientDao).update(client);

            assertEquals(client, updatedClient);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }



}
