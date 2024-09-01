package ru.astondevs.attractionservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.astondevs.attractionservice.dto.attraction.AttractionDto;
import ru.astondevs.attractionservice.dto.attraction.NewAttractionForm;
import ru.astondevs.attractionservice.dto.attraction.UpdateAttractionForm;
import ru.astondevs.attractionservice.model.Attraction;

import java.util.List;

/**
 * Сервис отвечающий за обработку сущности {@link Attraction}
 */
public interface AttractionService {

    /**
     * Создает новую достопримечательность в базе данных на основе данных из формы.
     * @param form данные для создания новой достопримечательности
     * @return возвращает сущность с id и данными
     */
    AttractionDto createAttraction(NewAttractionForm form);

    /**
     * Возвращает постраничный список достопримечательностей.
     * Список может быть отсортирован по названию в алфавитном или обратном ему порядке.
     * Список может быть отфильтрован по типу достопримечательности.
     * @param nameSort параметр сортировки:
     *                 если null - не сортирует,
     *                 если ASC - в алфавитном порядке,
     *                 если DESC - в обратном алфавитном порядке
     * @param type тип достопримечательности:
     *             если null - не фильтрует,
     *             если не null - возвращает только достопримечательности этого типа
     * @param pageable информация о запрашиваемой странице
     * @return страницу с достопримечательностями
     */
    Page<AttractionDto> getAllAttractions(Sort.Direction nameSort, Attraction.Type type, Pageable pageable);

    /**
     * Возвращает достопримечательности связанные с определенным населенным пунктом.
     * @param settlementId id населенного пункта
     * @return список достопримечательностей
     */
    List<AttractionDto> getSettlementAttractions(Long settlementId);

    /**
     * Обновляет данные достопримечательности в базе данных по данным из формы.
     * @param attractionId id достопримечательности
     * @param form новые данные достопримечательности
     * @return dto с обновленными данными
     */
    AttractionDto updateAttraction(Long attractionId, UpdateAttractionForm form);

    /**
     * Удаляет достопримечательность по id.
     * @param attractionId id
     */
    void deleteAttraction(Long attractionId);
}
