package ru.otus.crm.model;


import jakarta.annotation.Nonnull;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("address")
public class Address {
    @Id
    private final Long id;
    @Nonnull
    private final String street;

    private Address() {
        this(null, null);
    }

    public Address(String street) {
        this(null, street);
    }

    @PersistenceCreator
    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    @Override
    public String toString() {
        return "{" + "id:" + id +
                ", street='" + street + '\'' +
                "}";
    }
}
