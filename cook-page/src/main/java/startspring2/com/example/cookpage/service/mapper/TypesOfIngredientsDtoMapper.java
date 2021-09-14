package startspring2.com.example.cookpage.service.mapper;

import org.springframework.stereotype.Component;
import startspring2.com.example.cookpage.model.TypesOfIngredients;
import startspring2.com.example.cookpage.service.dto.TypesOfIngredientsDto;

@Component
public class TypesOfIngredientsDtoMapper {

    public TypesOfIngredientsDto toDto (TypesOfIngredients typesOfIngredients) {

        return TypesOfIngredientsDto.builder()
                .id(typesOfIngredients.getId())
                .name(typesOfIngredients.getName())
                .build();
    }
}
