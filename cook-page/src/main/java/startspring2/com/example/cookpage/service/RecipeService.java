package startspring2.com.example.cookpage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import startspring2.com.example.cookpage.controller.exception.AlreadyExistsException;
import startspring2.com.example.cookpage.controller.exception.BadRequestException;
import startspring2.com.example.cookpage.controller.exception.NotFoundException;
import startspring2.com.example.cookpage.model.AmountOfIngredients;
import startspring2.com.example.cookpage.model.Recipe;
import startspring2.com.example.cookpage.model.TypesOfRecipes;
import startspring2.com.example.cookpage.repository.AmountOfIngredientsRepository;
import startspring2.com.example.cookpage.repository.RecipeRepository;
import startspring2.com.example.cookpage.repository.TypesOfRecipesRepository;
import startspring2.com.example.cookpage.service.dto.AmountOfIngredientsDto;
import startspring2.com.example.cookpage.service.dto.CreateUpdateRecipeDto;
import startspring2.com.example.cookpage.service.dto.RecipeDto;
import startspring2.com.example.cookpage.service.mapper.AmountOfIngredientsDtoMapper;
import startspring2.com.example.cookpage.service.mapper.RecipeDtoMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeDtoMapper recipeDtoMapper;
    @Autowired
    private AmountOfIngredientsService amountOfIngredientsService;
    @Autowired
    private AmountOfIngredientsRepository amountOfIngredientsRepository;
    @Autowired
    private TypesOfRecipesRepository typesOfRecipesRepository;
    @Autowired
    private AmountOfIngredientsDtoMapper amountOfIngredientsDtoMapper;

    @Transactional
    public List<RecipeDto> showAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDto> recipesDto = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipesDto.add(recipeDtoMapper.toDto(recipe));
        }
        return recipesDto;
    }

    @Transactional
    public RecipeDto showRecipeByName(@PathVariable String name) throws NotFoundException {
        RecipeDto lookingForRecipeDto = null;
        for (Recipe recipe : recipeRepository.findAll()) {
            if (recipe.getName().equals(name)) {
                lookingForRecipeDto = recipeDtoMapper.toDto(recipe);
            }
        }
        if (lookingForRecipeDto == null) {
            throw new NotFoundException();
        }
        return lookingForRecipeDto;
    }

    @Transactional
    public RecipeDto addNewRecipe(@RequestBody CreateUpdateRecipeDto recipe) throws AlreadyExistsException, BadRequestException {
        List<Recipe> allRecipes = recipeRepository.findAll();
        for (Recipe recipeExist : allRecipes) {
            if (recipeExist.getName().equals(recipe.getName())) {
                throw new AlreadyExistsException();
            }
        }
        Recipe newRecipe = recipeDtoMapper.fromDto(recipe);
        Recipe savedRecipe = recipeRepository.save(newRecipe);
        amountOfIngredientsService.newAmountOfIngredients(savedRecipe.getId(), recipe.getIngredientsWithAmount());
        return recipeDtoMapper.toDto(savedRecipe);

    }

    @Transactional
    public RecipeDto updateRecipe(@RequestBody CreateUpdateRecipeDto recipe, @PathVariable int id) throws NotFoundException {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

        TypesOfRecipes typesOfRecipes = typesOfRecipesRepository.findById(recipe.getTypesOfRecipesId())
                .orElseThrow(() -> new NotFoundException());

        existingRecipe.setName(recipe.getName());
        existingRecipe.setTime(recipe.getTime());
        existingRecipe.setType(typesOfRecipes);
        existingRecipe.setLevel(recipe.getLevel());
        existingRecipe.setDetails(recipe.getDetails());

        amountOfIngredientsService.deleteAmountOfIngredients(id);
        List<AmountOfIngredientsDto> newAmountDto = amountOfIngredientsService.newAmountOfIngredients(id, recipe.getIngredientsWithAmount());
        List<AmountOfIngredients> amountOfIngredients = new ArrayList<>();
        for(AmountOfIngredientsDto amountOfIngredientsDto : newAmountDto) {
            amountOfIngredients.add(amountOfIngredientsDtoMapper.fromDto(amountOfIngredientsDto));
        }

        existingRecipe.setAmountOfIngredients(amountOfIngredients);

        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        return recipeDtoMapper.toDto(savedRecipe);
    }

    @Transactional
    public RecipeDto deleteRecipe(@PathVariable int id) throws NotFoundException {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        amountOfIngredientsService.deleteAmountOfIngredients(id);
        recipeRepository.delete(existingRecipe);
        return recipeDtoMapper.toDto(existingRecipe);
    }
}
