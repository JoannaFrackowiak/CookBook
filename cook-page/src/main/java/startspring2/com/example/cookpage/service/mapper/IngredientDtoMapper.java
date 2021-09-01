package startspring2.com.example.cookpage.service.mapper;

import org.springframework.stereotype.Component;
import startspring2.com.example.cookpage.model.Ingredient;
import startspring2.com.example.cookpage.service.dto.IngredientDto;

@Component
public class IngredientDtoMapper {

    public IngredientDto toDto(Ingredient ingredient) {

        Integer typesOfIngredientsId = ingredient.getType().getId();

        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .unit(ingredient.getUnit())
                .typesOfIngredientsId(typesOfIngredientsId)
                .description(ingredient.getDescription())
                .build();
    }
}
