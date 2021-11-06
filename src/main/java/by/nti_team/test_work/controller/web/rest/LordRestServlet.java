package by.nti_team.test_work.controller.web.rest;

import by.nti_team.test_work.model.Lord;
import by.nti_team.test_work.view.api.ILordView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lords")
public class LordRestServlet {

    private final ILordView lordView;

    public LordRestServlet(ILordView lordView) {
        this.lordView = lordView;
    }

    @GetMapping(produces = {"application/json"})
    protected ResponseEntity<?> getAllLordsJson() {
        return ResponseEntity.ok().body(this.lordView.getAll());
    }

    @GetMapping(value = "/empty", produces = {"application/json"})
    protected ResponseEntity<?> getEmptyLordsJson() {
        return ResponseEntity.ok().body(this.lordView.getEmptyLords());
    }

    @GetMapping(value = "/top", produces = {"application/json"})
    protected ResponseEntity<?> getTopLordsJson() {
        return ResponseEntity.ok().body(this.lordView.getTop());
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    protected ResponseEntity<?> getLordByIdJson(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.lordView.getById(id));
    }

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    protected ResponseEntity<?> addLord(@RequestBody Lord lord) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.lordView.addLord(lord));
    }

    @PutMapping(produces = {"application/json"}, consumes = {"application/json"})
    protected ResponseEntity<?> updateLord(@RequestBody Lord lord) {
        return ResponseEntity.ok().body(this.lordView.updateLord(lord));
    }

}
