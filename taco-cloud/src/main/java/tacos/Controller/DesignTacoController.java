package tacos.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import org.springframework.ui.Model;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.web.Taco;
import tacos.web.TacoOrder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = tacoRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel (Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatos", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        //測試看看是不是這樣，不過輸出的內容需轉換成小寫
        //model.addAllAttributes(Arrays.stream(types).toList(), filterByType(ingredients, type));
    }
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        //在這邊對GetMapping進行細化處理，將route /design傳進來的需求，導向到design的view，
        //並且同時調用@ModelAttribute的註解，構建一個包含Ingredient的配料表並將其放到模型中。
        //這些資料會複製到servlet Request的屬性中，這樣子view在渲染網頁的時候，就可以找到它們後渲染網頁。
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {

        if (errors.hasErrors()) {
            return "design";
        }

        Taco saved = designRepo.save(taco);
        tacoOrder.addTaco(saved);

        return "redirect:/orders/current";
    }
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
