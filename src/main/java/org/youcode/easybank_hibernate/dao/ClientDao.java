package org.youcode.easybank_hibernate.dao;

import org.youcode.easybank_hibernate.entities.Client;

import java.util.List;

public interface ClientDao extends IData<Client, Integer>{

    public List<Client> findByAnyAttribute(String attribute);

}