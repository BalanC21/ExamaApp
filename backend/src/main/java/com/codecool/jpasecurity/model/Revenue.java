package com.codecool.jpasecurity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Revenue {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Long amount;
    @OneToOne
    private User owner;
    @NotNull
    private boolean isRecursive;
    @NotNull
    private int renewalDayOfMonth;
}
