package startspring2.com.example.cookpage.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import startspring2.com.example.cookpage.controller.exception.BadRequestException;
import startspring2.com.example.cookpage.model.AmountOfIngredients;
import startspring2.com.example.cookpage.model.Recipe;
import startspring2.com.example.cookpage.model.TypesOfRecipes;
import startspring2.com.example.cookpage.repository.TypesOfRecipesRepository;
import startspring2.com.example.cookpage.service.dto.AmountOfIngredientsDto;
import startspring2.com.example.cookpage.service.dto.CreateUpdateRecipeDto;
import startspring2.com.example.cookpage.service.dto.RecipeDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeDtoMapper {

    @Autowired
    private TypesOfRecipesRepository typesOfRecipesRepository;

    public RecipeDto toDto(Recipe recipe) {

        List<Integer> amountOfIngredientsId = new ArrayList<>();

        for (AmountOfIngredients amountAndIngredient : recipe.getAmountOfIngredients()) {
            Integer amountAndIngredientId = amountAndIngredient.getId();
            amountOfIngredientsId.add(amountAndIngredientId);
        }

        Integer typesOfRecipesId = recipe.getType().getId();

        return RecipeDto.builder()
                .id(recipe.getId())
                .time(recipe.getTime())
                .level(recipe.getLevel())
                .name(recipe.getName())
                .details(recipe.getDetails())
                .amountOfIngredientsId(amountOfIngredientsId)
                .typeOfRecipeId(typesOfRecipesId)
                .build();
    }

    public RecipeDto toDto(Recipe recipe, List<AmountOfIngredientsDto> amountOfIngredientsDtoList) {

        List<Integer> amountOfIngredientsId = new ArrayList<>();

        for (AmountOfIngredientsDto amountAndIngredient : amountOfIngredientsDtoList) {
            Integer amountAndIngredientId = amountAndIngredient.getId();
            amountOfIngredientsId.add(amountAndIngredientId);
        }

        Integer typesOfRecipesId = recipe.getType().getId();

        return RecipeDto.builder()
                .id(recipe.getId())
                .time(recipe.getTime())
                .level(recipe.getLevel())
                .name(recipe.getName())
                .details(recipe.getDetails())
                .amountOfIngredientsId(amountOfIngredientsId)
                .typeOfRecipeId(typesOfRecipesId)
                .build();
    }

    public Recipe fromDto(CreateUpdateRecipeDto createUpdateRecipeDto) throws BadRequestException {

        Integer idTypeOfRecipe = createUpdateRecipeDto.getTypesOfRecipesId();
        TypesOfRecipes whatTypeOfRecipe = typesOfRecipesRepository.findById(idTypeOfRecipe).orElseThrow(() -> new BadRequestException());

        return Recipe.builder()
                .time(createUpdateRecipeDto.getTime())
                .level(createUpdateRecipeDto.getLevel())
                .name(createUpdateRecipeDto.getName())
                .details(createUpdateRecipeDto.getDetails())
                .type(whatTypeOfRecipe)
                .build();


    }
}
