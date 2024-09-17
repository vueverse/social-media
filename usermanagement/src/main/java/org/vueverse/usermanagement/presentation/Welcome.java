package org.vueverse.usermanagement.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/welcome")
public class Welcome {
    @GetMapping("/hi")
    public String welcome() {
        return "hi bro welcome to fucking app , try ";
    }
}
