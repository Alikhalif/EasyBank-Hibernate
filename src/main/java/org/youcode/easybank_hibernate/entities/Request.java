package org.youcode.easybank_hibernate.entities;

import org.youcode.easybank_hibernate.enums.STATE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    private LocalDate credit_date;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private STATE state;

    private String remarks;

    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "client_code")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "agency_code")
    private Agency agency;

    @Transient
    private Simulation simulation;

    public Request(LocalDate credit_date, Double amount, STATE state, String remarks, Integer duration, Simulation simulation) {
        this.credit_date = credit_date;
        this.amount = amount;
        this.state = state;
        this.remarks = remarks;
        this.duration = duration;
        this.simulation = simulation;
    }
}
