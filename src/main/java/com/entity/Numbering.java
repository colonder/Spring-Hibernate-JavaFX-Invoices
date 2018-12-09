package com.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Numbering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.ORDINAL)
    private Month month;

    @Column(name = "number")
    private int number;

    public Numbering() {
        this.number = 1;
        this.month = LocalDate.now().getMonth();
    }
}
