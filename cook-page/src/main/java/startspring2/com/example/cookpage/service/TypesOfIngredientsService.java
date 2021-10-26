package startspring2.com.example.cookpage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import startspring2.com.example.cookpage.model.TypesOfIngredients;
import startspring2.com.example.cookpage.repository.TypesOfIngredientsRepository;
import startspring2.com.example.cookpage.service.dto.TypesOfIngredientsDto;
import startspring2.com.example.cookpage.service.mapper.TypesOfIngredientsDtoMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypesOfIngredientsService {

    @Autowired
    private TypesOfIngredientsRepository typesOfIngredientsRepository;
    @Autowired
    private TypesOfIngredientsDtoMapper typesOfIngredientsDtoMapper;

    @Transactional
    public List<TypesOfIngredientsDto> getAllTypes() {
        List<TypesOfIngredients> types = typesOfIngredientsRepository.findAll();
        List<TypesOfIngredientsDto> typesDto = new ArrayList<>();
        for(TypesOfIngredients typesOfIngredients : types) {
            typesDto.add(typesOfIngredientsDtoMapper.toDto(typesOfIngredients));
        }
        return typesDto;
    }

}
