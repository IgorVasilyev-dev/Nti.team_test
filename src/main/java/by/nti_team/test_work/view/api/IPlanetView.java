package by.nti_team.test_work.view.api;

import by.nti_team.test_work.model.Planet;

import java.util.List;

public interface IPlanetView {

    /**
     * Получить весь список объектов типа Planet
     * @return объектов типа Planet
     */
    List<Planet> getAll();

    /**
     * Получить объект типа Planet по id
     * @param id ключ
     * @return объект типа Planet по id
     */
    Planet getById(Long id);

    /**
     * Добавить объект типа Planet
     * @param planet объект типа Planet
     */
    Planet addPlanet(Planet planet);

    /**
     * Удалить объект типа Planet
     * @param planet объект типа Planet
     */
    void deletePlanet(Planet planet);
}
