package ru.astondevs.attractionservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.attractionservice.model.Settlement;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}
