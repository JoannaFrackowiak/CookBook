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
import startspring2.com.example.cookpage.service.IngredientService;
import startspring2.com.example.cookpage.service.RecipeService;
import startspring2.com.example.cookpage.service.TypesOfRecipesService;
import startspring2.com.example.cookpage.service.dto.*;

import java.util.List;
import java.util.Map;

@Controller
public class RecipeViewController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private TypesOfRecipesService typesOfRecipesService;

    @GetMapping("/all-recipes")
    public ModelAndView recipeList() {
        List<RecipeDto> recipeDtoList = recipeService.showAllRecipes();
        ModelAndView modelAndView = new ModelAndView("recipe-list");
        modelAndView.addObject("recipeList", recipeDtoList);
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
    public ModelAndView displayCreateRecipe() {
        ModelAndView modelAndView = new ModelAndView("add-recipe");
        CreateUpdateRecipeDto createUpdateRecipeDto = new CreateUpdateRecipeDto();
        modelAndView.addObject("createUpdateRecipeDto", createUpdateRecipeDto);
        modelAndView.addObject("typesList", typesOfRecipesService.getAllTypes());
        return modelAndView;
    }

    @PostMapping("/new-recipe")
    public String createRecipe(@ModelAttribute CreateUpdateRecipeDto createUpdateRecipeDto) throws AlreadyExistsException, BadRequestException {
//        RecipeDto recipeDto =
                recipeService.addNewRecipe(createUpdateRecipeDto);
//        return "redirect:/the-recipe?id=" + recipeDto.getId();
        return "redirect:/home-page";
    }
}
