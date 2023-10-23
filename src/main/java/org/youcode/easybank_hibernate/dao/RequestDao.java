package org.youcode.easybank_hibernate.dao;

import org.youcode.easybank_hibernate.entities.Client;
import org.youcode.easybank_hibernate.entities.Request;
import org.youcode.easybank_hibernate.enums.STATE;

import java.util.Optional;

public interface RequestDao extends IData<Request, Integer>{

    public boolean updateState(Integer id, STATE state);

}
