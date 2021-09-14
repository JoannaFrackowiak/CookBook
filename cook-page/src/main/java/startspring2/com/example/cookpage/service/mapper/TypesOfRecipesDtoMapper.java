package startspring2.com.example.cookpage.service.mapper;

import org.springframework.stereotype.Component;
import startspring2.com.example.cookpage.model.TypesOfRecipes;
import startspring2.com.example.cookpage.service.dto.TypesOfRecipesDto;

@Component
public class TypesOfRecipesDtoMapper {

    public TypesOfRecipesDto toDto(TypesOfRecipes typesOfRecipes) {

        return TypesOfRecipesDto.builder()
                .id(typesOfRecipes.getId())
                .name(typesOfRecipes.getName())
                .build();
    }
}
