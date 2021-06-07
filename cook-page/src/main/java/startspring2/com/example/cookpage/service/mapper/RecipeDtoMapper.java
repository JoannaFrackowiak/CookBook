package startspring2.com.example.cookpage.service.mapper;

import org.springframework.stereotype.Component;
import startspring2.com.example.cookpage.model.AmountOfIngredients;
import startspring2.com.example.cookpage.model.Recipe;
import startspring2.com.example.cookpage.service.dto.RecipeDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeDtoMapper {

    public RecipeDto toDto(Recipe recipe) {

        List<Integer> amountOfIngredientsId = new ArrayList<>();

        for (AmountOfIngredients amount : recipe.getAmountOfIngredients()) {
            Integer amountId = amount.getId();
            amountOfIngredientsId.add(amountId);
        }

        Integer typesOfRecipesId = recipe.getType().getId();

        return RecipeDto.builder()
                .id(recipe.getId())
                .time(recipe.getTime())
                .level(recipe.getLevel())
                .name(recipe.getName())
                .details(recipe.getDetails())
                .amountOfIngredientsId(amountOfIngredientsId)
                .typesOfRecipesId(typesOfRecipesId)
                .build();
    }
}
