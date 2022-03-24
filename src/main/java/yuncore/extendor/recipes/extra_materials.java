package yuncore.extendor.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import yuncore.extendor.events.OnPlayerGetItem;

public class extra_materials {
    public static void init() {chipped_emerald();}
    private static void enchanted_iron_ingot() {
        ShapelessRecipe enchanted_iron_ingot_recipe = new ShapelessRecipe(NamespacedKey.minecraft("enchanted_iron_ingot"), wrapper.enchanted_iron_ingot());
        enchanted_iron_ingot_recipe.addIngredient(Material.ENCHANTED_BOOK);
        enchanted_iron_ingot_recipe.addIngredient(Material.IRON_INGOT);
        Bukkit.getServer().addRecipe(enchanted_iron_ingot_recipe);
    }
    private static void chipped_emerald() {
        ShapelessRecipe chipped_emerald_recipe = new ShapelessRecipe(NamespacedKey.minecraft("chipped_emerald"), wrapper.chipped_emerald());
        chipped_emerald_recipe.addIngredient(Material.EMERALD);
        Bukkit.getServer().addRecipe(chipped_emerald_recipe);
    }
}
