package yuncore.extendor.methods;

import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class itemStack_in {
    public static boolean isItemStackIn(ItemStack itemStack,ItemStack[] itemStacks){
        if(itemStack == null) return false;
        if(itemStack.hasItemMeta()) {
            for (ItemStack stack : itemStacks) {
                if (Objects.requireNonNull(itemStack.getItemMeta()).getLocalizedName().equals(Objects.requireNonNull(stack.getItemMeta()).getLocalizedName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
