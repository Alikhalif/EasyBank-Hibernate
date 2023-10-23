package org.youcode.easybank_hibernate.services;

import org.youcode.easybank_hibernate.dao.EmployeeDao;
import org.youcode.easybank_hibernate.entities.Simulation;
import jakarta.inject.Inject;

public class SimulationService {

    @Inject
    private EmployeeDao employeeDao;

    public double createSimulation(Simulation simulation) {
        double result = 0;
        try {
            if (simulation.getBorrowed_capital().toString().isEmpty() || simulation.getMonthly_payment_num().toString().isEmpty()) {
                throw new Exception("All fields need to be mentioned");
            } else {
                result = (simulation.getBorrowed_capital() * simulation.getProportional_annual_rate()/12) / (1 - Math.pow(1 + simulation.getProportional_annual_rate()/12, -simulation.getMonthly_payment_num()));
                if(simulation.getMonthly_payment() != result){
                    return 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
