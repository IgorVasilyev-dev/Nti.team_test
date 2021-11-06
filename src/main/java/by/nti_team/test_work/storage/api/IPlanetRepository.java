package by.nti_team.test_work.storage.api;

import by.nti_team.test_work.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface IPlanetRepository extends JpaRepository<Planet, Serializable> {

}
