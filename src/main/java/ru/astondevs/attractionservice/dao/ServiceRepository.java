package ru.astondevs.attractionservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.attractionservice.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
