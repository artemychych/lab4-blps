package com.example.lab4blps.models;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import org.camunda.bpm.engine.IdentityService;

@Entity
@Getter
@Setter
@Table(name = "userFilm")
public class UserFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "userId")
    String userId;

    @Column(name = "filmId")
    Integer filmId;
}
