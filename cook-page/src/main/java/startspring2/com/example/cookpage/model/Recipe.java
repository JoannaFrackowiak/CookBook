package startspring2.com.example.cookpage.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private int time;
    private String level;
    private List<Ingredient> ingredients = new ArrayList<>();
    private String content;

    public Recipe(int id, String name, int time, String level, List<Ingredient> ingredients, String content) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.level = level;
        this.ingredients = ingredients;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", level='" + level + '\'' +
                ", ingredients=" + ingredients +
                ", content='" + content + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
