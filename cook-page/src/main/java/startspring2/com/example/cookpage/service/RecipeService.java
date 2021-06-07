package startspring2.com.example.cookpage.service;

import org.springframework.web.bind.annotation.*;
import startspring2.com.example.cookpage.controller.exception.AlreadyExistsException;
import startspring2.com.example.cookpage.controller.exception.NotFoundException;
import startspring2.com.example.cookpage.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeService {


    private List<Recipe> recipes = new ArrayList<>();
    private int counter = 0;

    @RequestMapping(method = RequestMethod.GET, value = "/recipes")
    @ResponseBody
    public List<Recipe> showRecipes(@RequestParam(required = false) String name) throws NotFoundException {
        if (name != null) {
            List<Recipe> lookingForRecipes = new ArrayList<>();
            for (Recipe recipe : recipes) {
                if (recipe.getName().equals(name)) {
                    lookingForRecipes.add(recipe);
                }
            }
            if (lookingForRecipes.isEmpty()) {
                throw new NotFoundException();
            }
            return lookingForRecipes;
        }
        return recipes;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/recipes")
    @ResponseBody
    public Recipe addNewRecipe(@RequestBody Recipe recipe) throws AlreadyExistsException {
        for (Recipe recipeExist : recipes) {
            if (recipeExist.getName().equals(recipe.getName())) {
                throw new AlreadyExistsException();
            }
        }
        recipe.setId(counter++);
        recipes.add(recipe);
        return recipe;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "recipes/{id}")
    @ResponseBody
    public Recipe changeRecipe(@RequestBody Recipe recipe, @PathVariable int id) throws NotFoundException {
        for (Recipe changeRecipe : recipes) {
            if (changeRecipe.getId() == id) {
                changeRecipe.setName(recipe.getName());
                changeRecipe.setTime(recipe.getTime());
                changeRecipe.setLevel(recipe.getLevel());
                changeRecipe.setDetails(recipe.getDetails());
                changeRecipe.setAmountOfIngredients(recipe.getAmountOfIngredients());
                return changeRecipe;
            }
        }
        throw new NotFoundException();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "recipe/{id}")
    @ResponseBody
    public Recipe deleteRecipe(@PathVariable int id) throws NotFoundException {
        for (Recipe deleteRecipe : recipes) {
            if (deleteRecipe.getId() == id) {
                return recipes.remove(id);
            }
        }
        throw new NotFoundException();
    }
}
