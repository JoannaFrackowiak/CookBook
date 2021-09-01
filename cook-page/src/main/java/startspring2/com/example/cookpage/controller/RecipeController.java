package startspring2.com.example.cookpage.controller;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import startspring2.com.example.cookpage.controller.exception.AlreadyExistsException;
import startspring2.com.example.cookpage.controller.exception.BadRequestException;
import startspring2.com.example.cookpage.controller.exception.NotFoundException;
import startspring2.com.example.cookpage.model.Recipe;
import startspring2.com.example.cookpage.service.RecipeService;
import startspring2.com.example.cookpage.service.dto.CreateUpdateRecipeDto;
import startspring2.com.example.cookpage.service.dto.RecipeDto;

import java.util.List;


@RestController
@RequestMapping("api/v1/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public List<RecipeDto> showAllRecipes() {
        return recipeService.showAllRecipes();
    }

    //add search by type, time and level
    @GetMapping("/{name}")
    public RecipeDto showRecipeByName(@PathVariable String name) throws NotFoundException{
        return recipeService.showRecipeByName(name);
    }

    @PostMapping
    public RecipeDto addNewRecipe(@RequestBody CreateUpdateRecipeDto recipe) throws AlreadyExistsException, BadRequestException {
        return recipeService.addNewRecipe(recipe);
    }

    @PutMapping("/{id}")
    public RecipeDto updateRecipe(@RequestBody CreateUpdateRecipeDto recipe, @PathVariable Integer id) throws NotFoundException {
        return recipeService.updateRecipe(recipe, id);
    }

   @DeleteMapping("/{id}")
    public RecipeDto deleteRecipe(@PathVariable int id) throws NotFoundException {
       return recipeService.deleteRecipe(id);
   }
}
