package ftn.kts.controller;

import ftn.kts.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/registered_users")
public class RegisteredUserController {

    private RegisteredUserService service;

    @Autowired
    public RegisteredUserController(RegisteredUserService service) {
        this.service = service;
    }
 
    //TODO after code refactoring add update method for changing name and surname


}
