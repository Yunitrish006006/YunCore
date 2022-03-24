package yuncore.extendor.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapelessRecipe;

public class extra_foods {
    public static void init() {
        apple_pie();
    }
    private static void apple_pie() {
        ShapelessRecipe apple_pie_recipe = new ShapelessRecipe(NamespacedKey.minecraft("apple_pie"), wrapper.apple_pie());
        apple_pie_recipe.addIngredient(Material.EGG);
        apple_pie_recipe.addIngredient(Material.SUGAR);
        apple_pie_recipe.addIngredient(Material.WHEAT);
        apple_pie_recipe.addIngredient(Material.APPLE);
        Bukkit.getServer().addRecipe(apple_pie_recipe);
    }
}
