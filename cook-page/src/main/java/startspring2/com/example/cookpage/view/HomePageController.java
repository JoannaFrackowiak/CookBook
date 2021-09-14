package startspring2.com.example.cookpage.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/home-page")
    public String goToHomePage() {
        return "home-page";
    }

}
