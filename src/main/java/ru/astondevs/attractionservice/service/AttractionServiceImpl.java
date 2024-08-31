package ru.astondevs.attractionservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;
    private final SettlementRepository settlementRepository;
    private final ServiceRepository serviceRepository;
    private final AttractionMapper attractionMapper;

    @Transactional
    @Override
    public AttractionDto createAttraction(NewAttractionForm form) {
        log.debug("start createAttraction() with: {} ", form);

        Settlement settlement = settlementRepository.getReferenceById(form.settlementId());
        Set<Service> services = form.serviceIds().stream()
            .map(serviceRepository::getReferenceById)
            .collect(Collectors.toSet());

        Attraction attraction = attractionMapper.toAttraction(form, settlement, services);
        try {
            attraction = attractionRepository.save(attraction);
        } catch (ConstraintViolationException e) {
            log.debug("finish createAttraction() with exception", e);
            throw new ServiceViolationException(400, "settlement or services not exist");
        }
        AttractionDto dto = attractionMapper.toAttractionDto(attraction, form.serviceIds());

        log.debug("finish createAttraction() with: {} ", dto);
        return dto;
    }

    @Override
    public Page<AttractionDto> getAllAttractions(Sort.Direction nameSort, Attraction.Type type, Pageable pageable) {
        log.debug("start getAllAttractions() with: {}, {}, {}", nameSort, type, pageable);

        pageable = nameSort != null
            ? PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(nameSort, "name"))
            : PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        Page<Attraction> attractions = type != null
            ? attractionRepository.findAllByType(type, pageable)
            : attractionRepository.findAll(pageable);
        Page<AttractionDto> page = attractions.map(attractionMapper::toAttractionDto);

        log.debug("finish getAllAttractions() with: {} ", page);
        return page;
    }

    @Override
    public List<AttractionDto> getSettlementAttractions(Long settlementId) {
        log.debug("start getSettlementAttractions() with: {}", settlementId);

        List<Attraction> list = attractionRepository.findAllBySettlementId(settlementId);
        List<AttractionDto> dtos = list.stream()
            .map(attractionMapper::toAttractionDto)
            .toList();

        log.debug("finish getSettlementAttractions() with: {} ", dtos);
        return dtos;
    }

    @Transactional
    @Override
    public AttractionDto updateAttraction(Long attractionId, UpdateAttractionForm form) {
        log.debug("start updateAttraction() with: {} ", form);

        Attraction attraction = attractionRepository.findById(attractionId).orElseThrow(() ->
            new ServiceViolationException(404, "attraction not found"));
        attraction.setDescription(form.description());
        attraction = attractionRepository.save(attraction);
        AttractionDto dto = attractionMapper.toAttractionDto(attraction);

        log.debug("finish updateAttraction() with: {} ", dto);
        return dto;
    }

    @Override
    public void deleteAttraction(Long attractionId) {
        log.debug("start deleteAttraction() with: {}", attractionId);
        attractionRepository.deleteById(attractionId);
    }
}
