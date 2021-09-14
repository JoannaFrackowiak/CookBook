package startspring2.com.example.cookpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import startspring2.com.example.cookpage.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Ingredient findIngredientById(Integer id);
}
