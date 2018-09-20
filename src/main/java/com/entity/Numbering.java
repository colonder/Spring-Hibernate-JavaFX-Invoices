package com.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

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

    @LastModifiedDate
    private LocalDate date;

    @Column(name = "number")
    private int number;
}
