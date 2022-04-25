package yuncore.extendor.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class ExtraFoodsEvent implements Listener {
    @EventHandler
    public void onEatingExtraFoods(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(Objects.requireNonNull(item.getItemMeta()).hasLocalizedName()) {
            String ID = item.getItemMeta().getLocalizedName();
            switch (ID) {
                case "item.minecraft.apple_pie":
                    int random = (int)(Math.random()*120);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,random,0,true));
                case "enchanted_golden_apple":
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,20*60*3,0,true));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*60*3,0,true));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,20*60*3,3,true));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20*18,2,true));
                default:
                    break;
            }
        }
    }
}
