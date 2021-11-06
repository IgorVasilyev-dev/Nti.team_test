package by.nti_team.test_work.storage.api;

import by.nti_team.test_work.model.Lord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface ILordRepository extends JpaRepository<Lord, Serializable> {
    List<Lord> findTop10ByOrderByAgeAsc();
    List<Lord> findLordsByPlanetsIsNull();
}
