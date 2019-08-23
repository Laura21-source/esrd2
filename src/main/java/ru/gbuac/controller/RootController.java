package ru.gbuac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/")
    public String root() {
        return "redirect:login";
    }

    @GetMapping(value = "/admin/quests")
    public String quests() {
        return "admin/quests";
    }

    @GetMapping(value = "/admin/quest")
    public String edit() {
        return "admin/quest";
    }

    @GetMapping(value = "/my-quests")
    public String myQuests() {
        return "my-quests";
    }

    @GetMapping(value = "/my-quest")
    public String myQuest() {
        return "my-quest";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/new-document")
    public String newDocument() {
        return "new-document";
    }

    @GetMapping(value = "/view-document")
    public String viewDocument() {
        return "view-document";
    }

    @GetMapping(value = "/agree-document")
    public String agreeDocument() { return "agree-document"; }

    @GetMapping(value = "/temp-list")
    public String tempList() { return "temp-list"; }

    @GetMapping(value = "/agreement")
    public String agreement() { return "agreement"; }
}
