package yuncore.extendor.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;


public class OnPlayerGetItem implements Listener {
    @EventHandler
    public void OnCraft(CraftItemEvent event) {
        translateItem(Objects.requireNonNull(event.getRecipe().getResult()),((Player) event.getWhoClicked()).getLocale());
        translateItem(Objects.requireNonNull(event.getCurrentItem()),((Player) event.getWhoClicked()).getLocale());
    }

    @EventHandler
    public void OnPickedUp(EntityPickupItemEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String locale = player.getLocale();
            translateItem(event.getItem().getItemStack(),locale);

        }
    }

    @EventHandler
    public void OnClicked(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        translateItem(event.getCurrentItem(),((Player) event.getWhoClicked()).getLocale());
    }


    public static void translateItem(ItemStack itemStack, String locale) {
        if(itemStack == null) return;
        if(!itemStack.hasItemMeta()) return;
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(locale.equalsIgnoreCase("zh_tw")) {
            switch (Objects.requireNonNull(itemMeta).getLocalizedName()) {
                case "item.minecraft.oak_stick":
                    itemMeta.setDisplayName(ChatColor.RESET + "橡木棒");
                    break;
                case "item.minecraft.apple_pie":
                    itemMeta.setDisplayName(ChatColor.RESET + "蘋果派");
                    break;
                case "item.minecraft.wooden_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "木錘");
                    break;
                case "item.minecraft.stone_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "石錘");
                    break;
                case "item.minecraft.iron_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "鐵錘");
                    break;
                case "item.minecraft.golden_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "金錘");
                    break;
                case "item.minecraft.diamond_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "鑽石錘");
                    break;
                case "item.minecraft.netherite_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "獄髓錘");
                    break;
                case "item.minecraft.enchanted_iron_ingot":
                    itemMeta.setDisplayName(ChatColor.RESET + "魔鐵錠");
                    break;
                case "item.minecraft.chipped_emerald":
                    itemMeta.setDisplayName(ChatColor.RESET + "碎綠寶石");
                    break;
                default:
                    break;
            }
        }
        else {
            switch (Objects.requireNonNull(itemMeta).getLocalizedName()) {
                case "item.minecraft.oak_stick":
                    itemMeta.setDisplayName(ChatColor.RESET + "oak stick");
                    break;
                case "item.minecraft.apple_pie":
                    itemMeta.setDisplayName(ChatColor.RESET + "apple pie");
                    break;
                case "item.minecraft.wooden_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "wooden hammer");
                    break;
                case "item.minecraft.stone_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "stone hammer");
                    break;
                case "item.minecraft.iron_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "iron hammer");
                    break;
                case "item.minecraft.golden_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "golden hammer");
                    break;
                case "item.minecraft.diamond_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "diamond hammer");
                    break;
                case "item.minecraft.netherite_hammer":
                    itemMeta.setDisplayName(ChatColor.RESET + "netherite hammer");
                    break;
                case "item.minecraft.enchanted_iron_ingot":
                    itemMeta.setDisplayName(ChatColor.RESET + "enchanted iron ingot");
                    break;
                case "item.minecraft.chipped_emerald":
                    itemMeta.setDisplayName(ChatColor.RESET + "chipped emerald");
                    break;
                default:
                    break;
            }
        }
        itemStack.setItemMeta(itemMeta);
    }

}
