package org.youcode.easybank_hibernate.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.youcode.easybank_hibernate.dao.EmployeeDao;
import org.youcode.easybank_hibernate.entities.Simulation;
import jakarta.inject.Inject;

import java.text.DecimalFormat;

@ApplicationScoped
public class SimulationService {

    @Inject
    private EmployeeDao employeeDao;

    public double createSimulation(Simulation simulation) {
        double result = 0;
        double Monthly_payment = simulation.getMonthly_payment();
        DecimalFormat df = new DecimalFormat("#.00");
        try {
            if (simulation.getBorrowed_capital().toString().isEmpty() || simulation.getMonthly_payment_num().toString().isEmpty()) {
                throw new Exception("All fields need to be mentioned");
            } else {
                result = (simulation.getBorrowed_capital() * simulation.getProportional_annual_rate()/12) / (1 - Math.pow(1 + simulation.getProportional_annual_rate()/12, -simulation.getMonthly_payment_num()));
                if(Monthly_payment+2 != result){
                    System.out.println("mandly P "+Monthly_payment);
                    System.out.println("Res "+result);
                    System.out.println("dkhal");
                    return 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
