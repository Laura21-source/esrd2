package ru.gbuac.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserController extends AbstractUserController {
    public static final String REST_URL = "/rest/profile/users";

    @Override
    @GetMapping
    public List<String> getAllLdapUsers() {
        return super.getAllLdapUsers();
    }
}