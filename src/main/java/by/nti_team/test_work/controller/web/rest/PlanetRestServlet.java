package by.nti_team.test_work.controller.web.rest;

import by.nti_team.test_work.model.Planet;
import by.nti_team.test_work.view.api.IPlanetView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planets")
public class PlanetRestServlet {

    private  final IPlanetView planetView;

    public PlanetRestServlet(IPlanetView planetView) {
        this.planetView = planetView;
    }

    @GetMapping(produces = {"application/json"})
    protected ResponseEntity<?> getAllPlanetsJson() {
        return ResponseEntity.ok().body(this.planetView.getAll());
    }

    @DeleteMapping(produces = {"application/json"}, consumes = {"application/json"})
    protected ResponseEntity<?> destroyPlanet(@RequestBody Planet planet) {
        this.planetView.deletePlanet(planet);
        return ResponseEntity.accepted().body(planet);
    }

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    protected ResponseEntity<?> createPlanet(@RequestBody Planet planet) {
        return ResponseEntity.ok().body(this.planetView.addPlanet(planet));
    }

}
