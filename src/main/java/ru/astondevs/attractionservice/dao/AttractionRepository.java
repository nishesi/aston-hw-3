package ru.astondevs.attractionservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.attractionservice.model.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
}
