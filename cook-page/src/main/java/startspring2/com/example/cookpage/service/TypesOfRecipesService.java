package startspring2.com.example.cookpage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import startspring2.com.example.cookpage.model.TypesOfRecipes;
import startspring2.com.example.cookpage.repository.TypesOfRecipesRepository;
import startspring2.com.example.cookpage.service.dto.TypesOfRecipesDto;
import startspring2.com.example.cookpage.service.mapper.TypesOfRecipesDtoMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypesOfRecipesService {

    @Autowired
    private TypesOfRecipesRepository typesOfRecipesRepository;
    @Autowired
    private TypesOfRecipesDtoMapper typesOfRecipesDtoMapper;

    @Transactional
    public TypesOfRecipesDto whichType(Integer typeId) {
        return typesOfRecipesDtoMapper.toDto(typesOfRecipesRepository.findTypesOfRecipesById(typeId));

    }

    @Transactional
    public List<TypesOfRecipesDto> getAllTypes() {
        List<TypesOfRecipes> typesOfRecipes = typesOfRecipesRepository.findAll();
        List<TypesOfRecipesDto> typesDto = new ArrayList<>();
        for (TypesOfRecipes type : typesOfRecipes) {
            typesDto.add(typesOfRecipesDtoMapper.toDto(type));
        }
        return typesDto;
    }

    @Transactional
    public TypesOfRecipesDto getType(String name) {
        return typesOfRecipesDtoMapper.toDto(typesOfRecipesRepository.findTypesOfRecipesByName(name));
    }
}
