package ru.otus.data.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address implements Cloneable {
    @Id
    @SequenceGenerator(name = "address_gen", sequenceName = "address_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @Column(name = "id")
    private Long addressId;
    private String street;

    @Override
    public String toString() {
        return "{" + "id:" + addressId +
                ", street='" + street + '\'' +
                "}";
    }

    @Override
    public Address clone() {
        return new Address(addressId, street);
    }
}
