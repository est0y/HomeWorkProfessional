package ru.otus.crm.model;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("phone")
public class Phone {
    @Id
    private final Long id;
    @Nonnull
    private final String number;

    private Phone() {
        this(null);
    }

    @PersistenceCreator
    private Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Phone(String number) {
        this(null, number);
    }

    @Override
    public String toString() {
        return "{" + "id:" + id +
                ", number='" + number + '\'' +
                "}";
    }
}
