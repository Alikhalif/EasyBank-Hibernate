package org.youcode.easybank_hibernate.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.youcode.easybank_hibernate.entities.Simulation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimulationServiceTest {
    private SimulationService simulationService;

    @BeforeEach
    public void setUp() {
        simulationService = new SimulationService();
    }

    @Test
    public void testCreateSimulation() {
        Simulation simulation = mock(Simulation.class);

        when(simulation.getBorrowed_capital()).thenReturn(1000.0);
        when(simulation.getMonthly_payment_num()).thenReturn(12);
        when(simulation.getProportional_annual_rate()).thenReturn(0.12);
        when(simulation.getMonthly_payment()).thenReturn(168.0);

        SimulationService simulationService = new SimulationService();

        double result = simulationService.createSimulation(simulation);

        assertEquals(0.0, result, 0.001);
    }

}
