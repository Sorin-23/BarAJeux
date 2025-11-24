package projet_groupe4.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import projet_groupe4.dto.request.SubscribeClientRequest;
import projet_groupe4.dto.request.SubscribeEmployeRequest;
import projet_groupe4.dto.response.EntityCreatedResponse;
import projet_groupe4.exception.EmailAlreadyUsedException;
import projet_groupe4.service.PersonneService;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final PersonneService srv;

    public UserRestController(PersonneService srv) {
        this.srv = srv;
    }

    @PostMapping("/inscription/client")
    public EntityCreatedResponse subscribeClient(@Valid @RequestBody SubscribeClientRequest request) {
        if (this.srv.getByMail(request.getMail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }
        return new EntityCreatedResponse(this.srv.create(request).getId());
    }

    @PostMapping("/inscription/employe")
    public EntityCreatedResponse subscribeEmploye(@Valid @RequestBody SubscribeEmployeRequest request) {
        if (this.srv.getByMail(request.getMail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        }
        return new EntityCreatedResponse(this.srv.create(request).getId());
    }
}
