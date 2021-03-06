package com.example.lab4blps.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "films")
public class Films {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "token")
    private String token;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "film_genre",
            joinColumns = @JoinColumn(name = "films_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genres_id", referencedColumnName = "id"))
    private Set<Genres> filmGenre = new HashSet<>();

}
