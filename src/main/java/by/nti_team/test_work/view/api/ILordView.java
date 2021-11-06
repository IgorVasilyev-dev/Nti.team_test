package by.nti_team.test_work.view.api;

import by.nti_team.test_work.model.Lord;

import java.util.List;

public interface ILordView {

    /**
     * Получить список всех объектов типа Lord из БД
     * @return список объектов типа Lord
     */
    List<Lord> getAll();

    /**
     * Получить топ 10 объектов типа Lord c наименьшим значение age в порядке возрастания
     * @return список объектов типа Lord
     */
    List<Lord> getTop();

    /**
     * Получить список объектов из БД, которые не имеют ссылок на дочерние сущности типа Planet
     * @return список объектов типа Lord
     */
    List<Lord> getEmptyLords();

    /**
     * Получить сущность из БД по id
     * @param id идентификатор сущности
     * @return объект типа Lord
     */
    Lord getById(Long id);

    /**
     * добавить объект в БД
     * @param lord объект типа Lord
     */
    Lord addLord(Lord lord);

    /**
     * Обновить сущность в БД
     * @param provider объект типа Lord
     */
    Lord updateLord(Lord provider);

}
