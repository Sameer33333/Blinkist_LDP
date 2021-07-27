package com.training.blinkist.repository;

import com.training.blinkist.modelentity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

    public interface GenreRepository extends JpaRepository<Genre, UUID> {
}
