package by.nti_team.test_work.view;

import by.nti_team.test_work.model.Lord;
import by.nti_team.test_work.storage.api.ILordRepository;
import by.nti_team.test_work.view.api.ILordView;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public class LordView implements ILordView {

    private final ILordRepository repository;

    public LordView(ILordRepository repository) {
        this.repository = repository;
    }

    /**
     * Получить всех Повелителей
     * @return список всех Повелителей
     */
    @Override
    public List<Lord> getAll() {
        return this.repository.findAll();
    }

    /**
     * Получить список ТОП 10 самых молодых Повелителей
     * @return список ТОП 10 самых молодых Повелителей в порядке возрастания
     */
    @Override
    public List<Lord> getTop() {
        return this.repository.findTop10ByOrderByAgeAsc();
    }

    /**
     * Получить список Повелителей, который не управляют планетами
     * @return список объектов типа Lord
     */
    @Override
    public List<Lord> getEmptyLords() {
        return this.repository.findLordsByPlanetsIsNull();
    }

    /**
     * Получить сущность из БД по id
     * @param id идентификатор сущности
     * @return объект типа Lord
     */
    @Override
    public Lord getById(Long id) {
        return this.repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("entity with 'id = " + id + "' is not found"));
    }

    /**
     * добавить объект в БД
     * @param lord объект типа Lord
     */
    @Override
    @Transactional
    public Lord addLord(Lord lord) {
        if(this.repository.findOne(Example.of(lord)).isEmpty()) {
            return this.repository.save(lord);
        } else {
            throw new EntityExistsException("entity already exists");
        }
    }

    /**
     * Обновить сущность в БД
     * @param provider объект типа Lord
     */
    @Override
    @Transactional
    public Lord updateLord(Lord provider) {
        Lord oldLord =  this.repository.findById(provider.getId()).orElseThrow(
                () -> new EntityNotFoundException("entity with 'id = " + provider.getId() + "' is not found"));
        oldLord.setName(provider.getName());
        oldLord.setAge(provider.getAge());
        oldLord.setPlanets(provider.getPlanets());
        return this.repository.save(oldLord);
    }

}
