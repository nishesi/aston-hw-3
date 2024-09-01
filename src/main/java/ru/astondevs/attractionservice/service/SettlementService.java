package ru.astondevs.attractionservice.service;

import ru.astondevs.attractionservice.dto.settlement.NewSettlementForm;
import ru.astondevs.attractionservice.dto.settlement.SettlementDto;
import ru.astondevs.attractionservice.dto.settlement.UpdateSettlementForm;

/**
 * Сервис отвечающий за обработку бизнес-сущности {@link ru.astondevs.attractionservice.model.Settlement}
 */
public interface SettlementService {

    /**
     * Создает населенный пункт в базе данных на основе данных из формы.
     * @param form dto содержащий данные населенного пункта
     * @return dto содержащий id и данные добавленного населенного пункта
     */
    SettlementDto createSettlement(NewSettlementForm form);

    /**
     * Обновляет данные населенного пункта в базе по id, заменяя их данными из формы.
     * @param settlementId id населенного пункта
     * @param form dto с новыми данными
     * @return dto содержащий id и данные обновленного населенного пункта
     */
    SettlementDto updateSettlement(Long settlementId, UpdateSettlementForm form);
}
