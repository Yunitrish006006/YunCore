package yuncore.extendor.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Objects;

public class wood_fix {
    public static void init() {
        slabs();
        sticks();
        planks();
    }
    private static void slabs() {
        ShapedRecipe oak_slab = new ShapedRecipe(NamespacedKey.minecraft("oka_slab"),new ItemStack(Material.OAK_PLANKS));
        oak_slab.shape("#","#");
        oak_slab.setIngredient('#',Material.OAK_SLAB);
        Bukkit.getServer().addRecipe(oak_slab);
    }
    private static void sticks() {
        stick_recipe(wrapper.oak_stick(),Material.OAK_PLANKS);
        stick_recipe(wrapper.birch_stick(),Material.BIRCH_PLANKS);
        stick_recipe(wrapper.acacia_stick(),Material.ACACIA_PLANKS);
        stick_recipe(wrapper.spruce_stick(),Material.SPRUCE_PLANKS);
        stick_recipe(wrapper.jungle_stick(),Material.JUNGLE_PLANKS);
        stick_recipe(wrapper.dark_oak_stick(),Material.DARK_OAK_PLANKS);
        stick_recipe(wrapper.crimson_stick(),Material.CRIMSON_PLANKS);
        stick_recipe(wrapper.warp_stick(),Material.WARPED_PLANKS);
    }
    private static void planks() {
        ItemStack oak_stick = wrapper.oak_stick();
        ShapedRecipe oak_plank_recipe = new ShapedRecipe(NamespacedKey.minecraft("oak_plank"),new ItemStack(Material.OAK_PLANKS));
        oak_plank_recipe.shape("##","##");
        oak_plank_recipe.setIngredient('#',new RecipeChoice.ExactChoice(oak_stick));
        Bukkit.getServer().addRecipe(oak_plank_recipe);
    }

    private static void stick_recipe(ItemStack result,Material plank) {
        result.setAmount(4);
        ShapedRecipe stick_recipe = new ShapedRecipe(NamespacedKey.minecraft(Objects.requireNonNull(getLocalizedNameEnd(result))),result);
        stick_recipe.shape("#","#");
        stick_recipe.setIngredient('#',plank);
        Bukkit.getServer().addRecipe(stick_recipe);
    }

    private static String getLocalizedNameEnd(ItemStack itemStack) {
        if(!itemStack.hasItemMeta()) return "";
        if(!Objects.requireNonNull(itemStack.getItemMeta()).hasLocalizedName()) return "";
        String[] name = itemStack.getItemMeta().getLocalizedName().split("\\.");
        if(name.length >2) return name[2];
        else return null;
    }
}
