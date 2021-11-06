package by.nti_team.test_work.view;

import by.nti_team.test_work.model.Planet;
import by.nti_team.test_work.storage.api.IPlanetRepository;
import by.nti_team.test_work.view.api.IPlanetView;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public class PlanetView implements IPlanetView {

    private final IPlanetRepository repository;

    public PlanetView(IPlanetRepository repository) {
        this.repository = repository;
    }

    /**
     * Получить весь список объектов типа Planet из БД
     * @return список объектов типа Planet
     */
    @Override
    public List<Planet> getAll() {
        return this.repository.findAll();
    }

    /**
     * Получить объект типа Planet по id
     * @param id ключ
     * @return объект типа Planet, если объект не найден выбрасываем исключение NotFoundEntityException
     */
    @Override
    public Planet getById(Long id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("entity with 'id = " + id + "' is not found"));
    }

    /**
     * Добавить объект типа Planet в БД
     * Если объект уже существует в БД, выбрасываем исключение EntityIsPresentException
     * @param planet объект типа Planet
     */
    @Override
    @Transactional
    public Planet addPlanet(Planet planet) {
        if(this.repository.findOne(Example.of(planet)).isEmpty()) {
           return this.repository.save(planet);
        } else {
            throw new EntityExistsException("entity already exists");
        }
    }

    /**
     * Удалить объект типа Planet из БД
     * Если объект в БД не найден, выбрасываем исключение NotFoundEntityException
     * @param planet объект типа Planet
     */
    @Override
    @Transactional
    public void deletePlanet(Planet planet) {
        this.repository.findOne(Example.of(planet)).orElseThrow(
                () -> new EntityNotFoundException("entity is not found"));
         this.repository.delete(planet);
    }
}
