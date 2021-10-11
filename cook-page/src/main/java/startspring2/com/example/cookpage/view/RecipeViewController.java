package startspring2.com.example.cookpage.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import startspring2.com.example.cookpage.controller.exception.AlreadyExistsException;
import startspring2.com.example.cookpage.controller.exception.BadRequestException;
import startspring2.com.example.cookpage.controller.exception.NotFoundException;
import startspring2.com.example.cookpage.model.RecipeLevel;
import startspring2.com.example.cookpage.service.AmountOfIngredientsService;
import startspring2.com.example.cookpage.service.IngredientService;
import startspring2.com.example.cookpage.service.RecipeService;
import startspring2.com.example.cookpage.service.TypesOfRecipesService;
import startspring2.com.example.cookpage.service.dto.*;

import java.util.Map;

@Controller
public class RecipeViewController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private TypesOfRecipesService typesOfRecipesService;
    @Autowired
    private AmountOfIngredientsService amountOfIngredientsService;

    @GetMapping("/selected-recipes")
    public ModelAndView selectedRecipeList(@RequestParam(name = "levelRecipe", required = false) RecipeLevel level,
                                           @RequestParam(name = "typeRecipe", required = false) String typeName,
                                           @RequestParam(name = "timeRecipe", required = false) Integer time)
            throws NotFoundException, BadRequestException {
        ModelAndView modelAndView = new ModelAndView("recipe-list");
        if (level != null && time != null && !typeName.isEmpty()) {
            modelAndView.addObject("recipeList", recipeService.showRecipeByTimeAndLevelAndType(time, level, typeName));
        } else if (level != null && time != null) {
            modelAndView.addObject("recipeList", recipeService.showRecipeByLevelAndTime(level, time));
        } else if (level != null && !typeName.isEmpty()) {
            modelAndView.addObject("recipeList", recipeService.showRecipeByLevelAndType(level, typeName));
        } else if (level != null) {
            modelAndView.addObject("recipeList", recipeService.showRecipeByLevel(level));
        } else if (!typeName.isEmpty() && time != null) {
            modelAndView.addObject("recipeList", recipeService.showRecipeByTimeAndType(time, typeName));
        } else if (!typeName.isEmpty()) {
            modelAndView.addObject("recipeList", recipeService.showRecipeByType(typeName));
        } else if (time != null) {
            modelAndView.addObject("recipeList", recipeService.showRecipeByTime(time));
        } else {
            throw new BadRequestException();
        }
        return modelAndView;
    }

    @GetMapping("/all-recipes")
    public ModelAndView allRecipeList() {
        ModelAndView modelAndView = new ModelAndView("recipe-list");
        modelAndView.addObject("recipeList", recipeService.showAllRecipes());
        return modelAndView;
    }

    @GetMapping("/recipes-with-the-ingredient")
    public ModelAndView recipeListByIngredient(@RequestParam(name = "ingredientAName") String nameA,
                                               @RequestParam(name = "ingredientBName", required = false) String nameB,
                                               @RequestParam(name = "ingredientCName", required = false) String idC) throws BadRequestException {
        ModelAndView modelAndView = new ModelAndView("recipe-list");
        modelAndView.addObject("recipeList", recipeService.showRecipeByIngredient(amountOfIngredientsService.getAmountByIngredient(nameA, nameB, idC)));
        return modelAndView;
    }

    @GetMapping("/the-recipe")
    public ModelAndView showTheRecipe(@RequestParam(name = "id") Integer id) throws NotFoundException {
        RecipeDto recipeDto = recipeService.showRecipeById(id);
        ModelAndView modelAndView = new ModelAndView("recipe-page");
        modelAndView.addObject("recipe", recipeDto);
        Map<IngredientDto, Integer> ingredientsWithAmount = ingredientService.ingredientsInRecipe(recipeDto.getAmountOfIngredientsId());
        modelAndView.addObject("ingredients", ingredientsWithAmount);
        TypesOfRecipesDto type = typesOfRecipesService.whichType(recipeDto.getTypeOfRecipeId());
        modelAndView.addObject("type", type);
        return modelAndView;
    }

    @GetMapping("/new-recipe")
    public ModelAndView displayCreateRecipe(@RequestParam(name = "howMany") Integer quantity) {
        ModelAndView modelAndView = new ModelAndView("add-recipe");
        CreateUpdateRecipeDto createUpdateRecipeDto = new CreateUpdateRecipeDto();
        modelAndView.addObject("createUpdateRecipeDto", createUpdateRecipeDto);
        modelAndView.addObject("typesList", typesOfRecipesService.getAllTypes());
        modelAndView.addObject("ingredients", ingredientService.showAllIngredients());
        modelAndView.addObject("levels", RecipeLevel.values());

        for (int i = 0; i < quantity; i++) {
            createUpdateRecipeDto.addAmount(new AmountOfIngredientsDto());
        }
        return modelAndView;
    }

    @PostMapping("/new-recipe")
    public String createRecipe(@ModelAttribute CreateUpdateRecipeDto createUpdateRecipeDto) throws AlreadyExistsException, BadRequestException {
        RecipeDto recipeDto = recipeService.addNewRecipe(createUpdateRecipeDto);
        return "redirect:/the-recipe?id=" + recipeDto.getId();
    }

    @GetMapping("/edit-recipe")
    public ModelAndView displayUpdateRecipe(@RequestParam(name = "recipeId") Integer id) throws NotFoundException {
        ModelAndView modelAndView = new ModelAndView("update-recipe");
        modelAndView.addObject("typeRecipe", typesOfRecipesService.getAllTypes());
        RecipeDto recipe = recipeService.showRecipeById(id);
        modelAndView.addObject("levels", RecipeLevel.values());
        modelAndView.addObject("ingredients", ingredientService.showAllIngredients());
        modelAndView.addObject("updateRecipe",
                new CreateUpdateRecipeDto(recipe.getName(), recipe.getTime(), recipe.getLevel(),
                        recipe.getTypeOfRecipeId(), recipe.getDetails(), amountOfIngredientsService.amountForRecipe(id)));
        modelAndView.addObject("recipeId", id);
        return modelAndView;
    }

    @PostMapping("/edit-recipe")
    public String updateRecipe(@ModelAttribute CreateUpdateRecipeDto createUpdateRecipeDto,
                               @RequestParam(name = "recipeId") Integer id) throws NotFoundException {
        RecipeDto recipeDto = recipeService.updateRecipe(createUpdateRecipeDto, id);
        return "redirect:/the-recipe?id=" + id;
    }

    @GetMapping("/delete-recipe")
    public String deleteRecipe(@RequestParam(name = "recipeId") Integer id) throws NotFoundException {
        recipeService.deleteRecipe(id);
        return "redirect:/home-page";
    }

    @GetMapping("/search")
    public String searchBy() {
        return "search-by";
    }

    @GetMapping("/search-by-name")
    public ModelAndView searchRecipe() {
        ModelAndView modelAndView = new ModelAndView("search-recipe-name");
        modelAndView.addObject("recipes", recipeService.showAllRecipes());
        return modelAndView;
    }

    @GetMapping("/search-by-ingredient")
    public ModelAndView searchRecipeByIngredient() {
        ModelAndView modelAndView = new ModelAndView("search-recipe-ingredient");
        modelAndView.addObject("ingredients", ingredientService.showAllIngredients());
        return modelAndView;
    }

    @GetMapping("/search-by-type")
    public ModelAndView searchRecipeByType() {
        ModelAndView modelAndView = new ModelAndView("search-recipe-type-level-time");
        modelAndView.addObject("types", typesOfRecipesService.getAllTypes());
        modelAndView.addObject("levels", RecipeLevel.values());
        return modelAndView;
    }

    @GetMapping("/add-new-recipe")
    public String howManyIngredients() {
        return "how-many-ingredients";
    }
}
