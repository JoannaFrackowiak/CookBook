package startspring2.com.example.cookpage.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import startspring2.com.example.cookpage.controller.exception.BadRequestException;
import startspring2.com.example.cookpage.controller.exception.NotFoundException;
import startspring2.com.example.cookpage.model.AmountOfIngredients;
import startspring2.com.example.cookpage.model.Recipe;
import startspring2.com.example.cookpage.repository.RecipeRepository;
import startspring2.com.example.cookpage.service.dto.AmountOfIngredientsDto;

@Component
public class AmountOfIngredientsDtoMapper {

    @Autowired
    private RecipeRepository recipeRepository;

    public AmountOfIngredientsDto toDto(AmountOfIngredients amount) {

        return AmountOfIngredientsDto.builder()
                .id(amount.getId())
                .ingredientId(amount.getIngredientId())
                .recipeId(amount.getRecipe().getId())
                .amount(amount.getAmount())
                .build();
    }

    public AmountOfIngredients fromDto(AmountOfIngredientsDto amount) {

        Recipe recipe = recipeRepository.getOne(amount.getRecipeId());

        return AmountOfIngredients.builder()
                .id(amount.getId())
                .ingredientId(amount.getIngredientId())
                .recipe(recipe)
                .amount(amount.getAmount())
                .build();
    }

}
