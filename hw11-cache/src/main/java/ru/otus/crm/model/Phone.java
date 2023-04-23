package ru.otus.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phone")
public class Phone implements Cloneable {
    @Id
    @SequenceGenerator(name = "phone_gen", sequenceName = "phone_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_gen")
    @Column(name = "id")
    private Long phoneId;
    @Column(name = "number")
    private String number;

    @Override
    public String toString() {
        return "{" + "id:" + phoneId +
                ", number='" + number + '\'' +
                "}";
    }

    @Override
    public Phone clone() {
        return new Phone(phoneId, number);
    }
}
