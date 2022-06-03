package com.example.lab4blps.repositories;


import com.example.lab4blps.models.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepo extends JpaRepository<Genres, Integer> {
}
