package startspring2.com.example.cookpage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import startspring2.com.example.cookpage.controller.exception.AlreadyExistsException;
import startspring2.com.example.cookpage.controller.exception.BadRequestException;
import startspring2.com.example.cookpage.controller.exception.NotFoundException;
import startspring2.com.example.cookpage.model.AmountOfIngredients;
import startspring2.com.example.cookpage.model.Recipe;
import startspring2.com.example.cookpage.model.RecipeLevel;
import startspring2.com.example.cookpage.model.TypesOfRecipes;
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
    public RecipeDto showRecipeByName(String name) throws NotFoundException {
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
    public List<RecipeDto> showRecipeByLevel(RecipeLevel level) throws NotFoundException {
        List<RecipeDto> recipesWithTheLevelDto = new ArrayList<>();
        List<Recipe> recipes = recipeRepository.findRecipesByLevel(level);
        for (Recipe recipe : recipes) {
            recipesWithTheLevelDto.add(recipeDtoMapper.toDto(recipe));
        }
        if (recipesWithTheLevelDto == null) {
            throw new NotFoundException();
        }
        return recipesWithTheLevelDto;


    }

    @Transactional
    public List<RecipeDto> showRecipeByType(String typeName) {
        List<RecipeDto> recipesWithTheTypeDto = new ArrayList<>();
        for (Recipe recipe : recipeRepository.findRecipesByType_Name(typeName)) {
            recipesWithTheTypeDto.add(recipeDtoMapper.toDto(recipe));
        }
        return recipesWithTheTypeDto;
    }

    @Transactional
    public List<RecipeDto> showRecipeByTime(Integer time) {
        List<RecipeDto> recipesWithTheTimeDto = new ArrayList<>();
        if (time < 61) {
            for (Recipe recipe : recipeRepository.findRecipesByTimeIsLessThanEqual(time)) {
                recipesWithTheTimeDto.add(recipeDtoMapper.toDto(recipe));
            }
        } else {
            for (Recipe recipe : recipeRepository.findRecipesByTimeIsGreaterThanEqual(time)) {
                recipesWithTheTimeDto.add(recipeDtoMapper.toDto(recipe));
            }
        }
        return recipesWithTheTimeDto;
    }

    @Transactional
    public RecipeDto showRecipeById(Integer id) throws NotFoundException {
        return recipeDtoMapper.toDto(recipeRepository.findById(id).orElseThrow(() -> new NotFoundException()));
    }

    @Transactional
    public List<RecipeDto> showRecipeByIngredient(List<AmountOfIngredientsDto> amountList) {
        List<Integer> recipesListId = new ArrayList<>();
        for (AmountOfIngredientsDto amount : amountList) {
            recipesListId.add(amount.getRecipeId());
        }
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        for (Integer id : recipesListId) {
            recipeDtoList.add(recipeDtoMapper.toDto(recipeRepository.getOne(id)));
        }
        return recipeDtoList;
    }

    @Transactional
    public RecipeDto addNewRecipe(CreateUpdateRecipeDto recipe) throws AlreadyExistsException, BadRequestException {
        List<Recipe> allRecipes = recipeRepository.findAll();
        for (Recipe recipeExist : allRecipes) {
            if (recipeExist.getName().equals(recipe.getName())) {
                throw new AlreadyExistsException();
            }
        }
        if (recipe.getName().isEmpty() || recipe.getTime() == null || recipe.getTime() == 0 || recipe.getDetails().isEmpty()) {
            throw new BadRequestException();
        }

        Recipe newRecipe = recipeDtoMapper.fromDto(recipe);
        Recipe savedRecipe = recipeRepository.save(newRecipe);
        List<AmountOfIngredientsDto> amountOfIngredientsDtoList = amountOfIngredientsService.newAmountOfIngredients(savedRecipe.getId(), recipe.getIngredientsWithAmount());
        return recipeDtoMapper.toDto(savedRecipe, amountOfIngredientsDtoList);

    }

    @Transactional
    public RecipeDto updateRecipe(CreateUpdateRecipeDto recipe, int id) throws NotFoundException, BadRequestException, AlreadyExistsException {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

        TypesOfRecipes typesOfRecipes = typesOfRecipesRepository.findById(recipe.getTypeOfRecipeId())
                .orElseThrow(() -> new NotFoundException());

        if (recipe.getName().isEmpty() || recipe.getTime() == null || recipe.getTime() == 0 || recipe.getDetails().isEmpty()) {
            throw new BadRequestException();
        }

        if (recipeRepository.findRecipeByName(recipe.getName()) != null) {
            throw new AlreadyExistsException();
        }

        existingRecipe.setName(recipe.getName());
        existingRecipe.setTime(recipe.getTime());
        existingRecipe.setType(typesOfRecipes);
        existingRecipe.setLevel(recipe.getLevel());
        existingRecipe.setDetails(recipe.getDetails());

        amountOfIngredientsService.deleteAmountOfIngredients(id);
        List<AmountOfIngredientsDto> newAmountDto = amountOfIngredientsService.newAmountOfIngredients(id, recipe.getIngredientsWithAmount());
        List<AmountOfIngredients> amountOfIngredients = new ArrayList<>();
        for (AmountOfIngredientsDto amountOfIngredientsDto : newAmountDto) {
            amountOfIngredients.add(amountOfIngredientsDtoMapper.fromDto(amountOfIngredientsDto, existingRecipe));
        }

        existingRecipe.setAmountOfIngredients(amountOfIngredients);

        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        return recipeDtoMapper.toDto(savedRecipe);
    }

    @Transactional
    public RecipeDto deleteRecipe(int id) throws NotFoundException {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        amountOfIngredientsService.deleteAmountOfIngredients(id);
        recipeRepository.delete(existingRecipe);
        return recipeDtoMapper.toDto(existingRecipe);
    }

    public List<RecipeDto> showRecipeByLevelAndTime(RecipeLevel level, Integer time) {
        List<RecipeDto> recipeList = new ArrayList<>();
        if (time < 61) {
            for (Recipe recipe : recipeRepository.findRecipesByLevelAndTimeIsLessThanEqual(level, time)) {
                recipeList.add(recipeDtoMapper.toDto(recipe));
            }
        } else {
            for (Recipe recipe : recipeRepository.findRecipesByLevelAndTimeIsGreaterThanEqual(level, time)) {
                recipeList.add(recipeDtoMapper.toDto(recipe));
            }
        }
        return recipeList;
    }

    public List<RecipeDto> showRecipeByTimeAndType(Integer time, String typeName) {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        if (time < 61) {
            for (Recipe recipe : recipeRepository.findRecipesByType_NameAndTimeIsLessThanEqual(typeName, time)) {
                recipeDtoList.add(recipeDtoMapper.toDto(recipe));
            }
        } else {
            for (Recipe recipe : recipeRepository.findRecipesByType_NameAndTimeIsGreaterThanEqual(typeName, time)) {
                recipeDtoList.add(recipeDtoMapper.toDto(recipe));
            }
        }
        return recipeDtoList;
    }

    public List<RecipeDto> showRecipeByLevelAndType(RecipeLevel level, String typeName) {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        for (Recipe recipe : recipeRepository.findRecipesByType_NameAndLevel(typeName, level)) {
            recipeDtoList.add(recipeDtoMapper.toDto(recipe));
        }
        return recipeDtoList;
    }

    public List<RecipeDto> showRecipeByTimeAndLevelAndType(Integer time, RecipeLevel level, String typeName) {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        if (time < 61) {
            for (Recipe recipe : recipeRepository.findRecipesByLevelAndType_NameAndTimeIsLessThanEqual(level, typeName, time)) {
                recipeDtoList.add(recipeDtoMapper.toDto(recipe));
            }
        } else {
            for (Recipe recipe : recipeRepository.findRecipesByLevelAndType_NameAndTimeIsGreaterThanEqual(level, typeName, time)) {
                recipeDtoList.add(recipeDtoMapper.toDto(recipe));
            }
        }
        return recipeDtoList;
    }
}
