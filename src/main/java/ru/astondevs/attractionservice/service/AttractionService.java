package ru.astondevs.attractionservice.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.attractionservice.dao.AttractionRepository;
import ru.astondevs.attractionservice.dao.ServiceRepository;
import ru.astondevs.attractionservice.dao.SettlementRepository;
import ru.astondevs.attractionservice.dto.attraction.AttractionDto;
import ru.astondevs.attractionservice.dto.attraction.NewAttractionForm;
import ru.astondevs.attractionservice.dto.attraction.UpdateAttractionForm;
import ru.astondevs.attractionservice.exception.ServiceViolationException;
import ru.astondevs.attractionservice.mapper.AttractionMapper;
import ru.astondevs.attractionservice.model.Attraction;
import ru.astondevs.attractionservice.model.Service;
import ru.astondevs.attractionservice.model.Settlement;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final SettlementRepository settlementRepository;
    private final ServiceRepository serviceRepository;
    private final AttractionMapper attractionMapper;

    @Transactional
    public AttractionDto createAttraction(NewAttractionForm form) {
        Settlement settlement = settlementRepository.getReferenceById(form.settlementId());
        Set<Service> services = form.serviceIds().stream()
            .map(serviceRepository::getReferenceById)
            .collect(Collectors.toSet());

        Attraction attraction = attractionMapper.toAttraction(form, settlement, services);
        try {
            attraction = attractionRepository.save(attraction);
        } catch (ConstraintViolationException e) {
            throw new ServiceViolationException(400, "settlement or services not exist");
        }
        return attractionMapper.toAttractionDto(attraction, form.serviceIds());
    }

    public Page<AttractionDto> getAllAttractions(Sort.Direction nameSort, Attraction.Type type, Pageable pageable) {
        pageable = nameSort != null
            ? PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(nameSort, "name"))
            : PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        Page<Attraction> attractions = type != null
            ? attractionRepository.findAllByType(type, pageable)
            : attractionRepository.findAll(pageable);
        return attractions.map(attractionMapper::toAttractionDto);
    }

    public List<AttractionDto> getSettlementAttractions(Long settlementId) {
        List<Attraction> list = attractionRepository.findAllBySettlementId(settlementId);
        return list.stream()
            .map(attractionMapper::toAttractionDto)
            .toList();
    }

    @Transactional
    public AttractionDto updateAttraction(Long attractionId, UpdateAttractionForm form) {
        Attraction attraction = attractionRepository.findById(attractionId).orElseThrow(() ->
            new ServiceViolationException(404, "attraction not found"));
        attraction.setDescription(form.description());
        attraction = attractionRepository.save(attraction);
        return attractionMapper.toAttractionDto(attraction);
    }

    public void deleteAttraction(Long attractionId) {
        attractionRepository.deleteById(attractionId);
    }
}
