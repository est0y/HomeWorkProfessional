package ru.otus.crm.model;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@AllArgsConstructor
@Getter
@Table("client")
public class Client implements Persistable<Long> {
    @Id
    private final Long id;
    @Nonnull
    private final String name;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    @Transient
    private final boolean isNew;

    private Client() {
        this(null, null, null, null, true);
    }

    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this(id, name, address, phones, false);
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
