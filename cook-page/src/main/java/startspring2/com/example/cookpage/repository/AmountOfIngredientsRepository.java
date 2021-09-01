package startspring2.com.example.cookpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import startspring2.com.example.cookpage.model.AmountOfIngredients;

import java.util.List;

@Repository
public interface AmountOfIngredientsRepository extends JpaRepository<AmountOfIngredients, List<Integer>> {

    List<AmountOfIngredients> findAllByRecipeId(Integer id);
}
