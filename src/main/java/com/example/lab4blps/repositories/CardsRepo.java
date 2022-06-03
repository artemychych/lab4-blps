package com.example.lab4blps.repositories;


import com.example.lab4blps.models.UserFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepo extends JpaRepository<UserFilm, Integer> {
}
