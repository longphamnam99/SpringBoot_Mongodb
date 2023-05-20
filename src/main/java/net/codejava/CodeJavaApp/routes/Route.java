package net.codejava.CodeJavaApp.routes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Route {
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/student")
    public String student(Model model) {
        return "student";
    }

    @GetMapping("/major")
    public String major(Model model) {
        return "major";
    }

    @GetMapping("/class_room")
    public String class_room(Model model) {
        return "class_room";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
