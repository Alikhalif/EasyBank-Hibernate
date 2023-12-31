package org.youcode.easybank_hibernate.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int code;

    @ManyToOne
    @JoinColumn(name = "employeeMatricule")
    private Employee employee;

    @OneToMany(mappedBy = "client")
    private List<Request> requests;

    public Client(String lastName, String firstName, LocalDate birthDate, String phone, String address, int code, Employee employee) {
        super(lastName, firstName, birthDate, phone, address);
        this.code = code;
        this.employee = employee;
    }

    public Client(String lastName, String firstName, LocalDate birthDate, String phone, String address) {
        super(lastName, firstName, birthDate, phone, address);
    }

    public Client(String lastName, String firstName, LocalDate birthDate, String phone, String address, Employee employee) {
        super(lastName, firstName, birthDate, phone, address);
        this.employee = employee;
    }
}