package startspring2.com.example.cookpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import startspring2.com.example.cookpage.model.Recipe;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipeController {

    private List<Recipe> recipes = new ArrayList<>();

    @RequestMapping(method = RequestMethod.GET, value = "/v1/chat")
    @ResponseBody
    public String showAllRecipes() {
        return recipes.toString();
    }
}
