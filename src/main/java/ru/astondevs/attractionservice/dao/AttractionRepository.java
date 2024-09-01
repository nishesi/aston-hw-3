package ru.astondevs.attractionservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.astondevs.attractionservice.model.Attraction;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    Page<Attraction> findAllByType(Attraction.Type type, Pageable pageable);

    List<Attraction> findAllBySettlementId(Long settlementId);
}
