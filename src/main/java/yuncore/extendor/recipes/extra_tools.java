package yuncore.extendor.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

public class extra_tools {
    public static void init() {
        wooden_hammer();
        stone_hammer();
        iron_hammer();
        golden_hammer();
        diamond_hammer();
        netherite_hammer();
        stone_pickaxe();
        stone_axe();
        stone_hoe();
        stone_shovel();
        stone_sword();
    }
    private static void wooden_hammer() {
        ShapedRecipe wooden_hammer_recipe = new ShapedRecipe(NamespacedKey.minecraft("wooden_hammer"), wrapper.wooden_hammer());
        wooden_hammer_recipe.shape("***","*#*"," # ");
        wooden_hammer_recipe.setIngredient('*', Material.OAK_PLANKS);
        wooden_hammer_recipe.setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(wooden_hammer_recipe);
    }
    private static void stone_hammer() {

        ShapedRecipe stone_hammer_recipe = new ShapedRecipe(NamespacedKey.minecraft("stone_hammer"), wrapper.stone_hammer());
        stone_hammer_recipe.shape("***","*#*"," # ");
        stone_hammer_recipe.setIngredient('*', wrapper.stones());
        stone_hammer_recipe.setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(stone_hammer_recipe);
    }
    private static void iron_hammer() {
        ShapedRecipe iron_hammer_recipe = new ShapedRecipe(NamespacedKey.minecraft("iron_hammer"), wrapper.iron_hammer());
        iron_hammer_recipe.shape("***","*#*"," # ");
        iron_hammer_recipe.setIngredient('*', Material.IRON_INGOT);
        iron_hammer_recipe.setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(iron_hammer_recipe);
    }
    private static void golden_hammer() {
        ShapedRecipe golden_hammer_recipe = new ShapedRecipe(NamespacedKey.minecraft("golden_hammer"), wrapper.golden_hammer());
        golden_hammer_recipe.shape("***","*#*"," # ");
        golden_hammer_recipe.setIngredient('*', Material.GOLD_INGOT);
        golden_hammer_recipe.setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(golden_hammer_recipe);
    }
    private static void diamond_hammer() {
        ShapedRecipe diamond_hammer_recipe = new ShapedRecipe(NamespacedKey.minecraft("diamond_hammer"), wrapper.diamond_hammer());
        diamond_hammer_recipe.shape("***","*#*"," # ");
        diamond_hammer_recipe.setIngredient('*', Material.DIAMOND);
        diamond_hammer_recipe.setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(diamond_hammer_recipe);
    }
    private static void netherite_hammer() {
        ShapedRecipe netherite_hammer_recipe = new ShapedRecipe(NamespacedKey.minecraft("netherite_hammer"), wrapper.netherite_hammer());
        netherite_hammer_recipe.shape("***","*#*"," # ");
        netherite_hammer_recipe.setIngredient('*', Material.NETHERITE_INGOT);
        netherite_hammer_recipe.setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(netherite_hammer_recipe);
        SmithingRecipe netherite_hammer_upgrade_recipe = new SmithingRecipe(NamespacedKey.minecraft("netherite_hammer_upgrade"), wrapper.netherite_hammer(), new RecipeChoice.ExactChoice(wrapper.diamond_hammer()),new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT));
        Bukkit.getServer().addRecipe(netherite_hammer_upgrade_recipe);
    }
    private static void stone_pickaxe() {
        ShapedRecipe stone_pickaxe = new ShapedRecipe(NamespacedKey.minecraft("minecraft.stone_pickaxe"),new ItemStack(Material.STONE_PICKAXE));
        stone_pickaxe.shape("***"," # "," # ");
        stone_pickaxe.setIngredient('*', wrapper.stones());
        stone_pickaxe.setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(stone_pickaxe);
    }
    private static void stone_axe() {
        ShapedRecipe stone_pickaxe = new ShapedRecipe(NamespacedKey.minecraft("minecraft.stone_axe"),new ItemStack(Material.STONE_AXE));
        stone_pickaxe.shape("** ","*# "," # ");
        stone_pickaxe.setIngredient('*', wrapper.stones());
        stone_pickaxe.setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(stone_pickaxe);
    }
    private static void stone_hoe() {
        ShapedRecipe stone_hoe = new ShapedRecipe(NamespacedKey.minecraft("minecraft.stone_hoe"),new ItemStack(Material.STONE_HOE));
        stone_hoe.shape("** "," # "," # ");
        stone_hoe.setIngredient('*', wrapper.stones());
        stone_hoe.setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(stone_hoe);
    }
    private static void stone_shovel() {
        ShapedRecipe stone_shovel = new ShapedRecipe(NamespacedKey.minecraft("minecraft.stone_shovel"),new ItemStack(Material.STONE_SHOVEL));
        stone_shovel.shape(" * "," # "," # ");
        stone_shovel.setIngredient('*', wrapper.stones());
        stone_shovel.setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(stone_shovel);
    }
    private static void stone_sword() {
        ShapedRecipe stone_sword = new ShapedRecipe(NamespacedKey.minecraft("minecraft.stone_sword"),new ItemStack(Material.STONE_SWORD));
        stone_sword.shape(" * "," * "," # ");
        stone_sword.setIngredient('*', wrapper.stones());
        stone_sword.setIngredient('#', Material.STICK);
        Bukkit.getServer().addRecipe(stone_sword);
    }
}
