package myessentials.economy.core.universalcoins;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CoinUtil {

    private final Player player;
    private final Inventory inventory;

    public CoinUtil(Player player)
    {
        this.player = player;this.inventory = player.getInventory();
    }

    private String[] itemNames = { "UNIVERSALCOINS_ITEMITEMCOIN", "UNIVERSALCOINS_ITEMITEMSMALLCOINSTACK", "UNIVERSALCOINS_ITEMITEMLARGECOINSTACK", "UNIVERSALCOINS_ITEMITEMSMALLCOINBAG", "UNIVERSALCOINS_ITEMITEMLARGECOINBAG" };

    public Integer balance() {
        Integer money = 0;
        for(ItemStack i : inventory.getContents()) {
            if(i.getTypeId() == 8708) {
                money += 1 * i.getAmount();
            }
            if(i.getTypeId() == 8709) {
                money += 729 * i.getAmount();
            }
            if(i.getTypeId() == 8711) {
                money += 81 * i.getAmount();
            }
            if(i.getTypeId() == 8715) {
                money += 8 * i.getAmount();
            }
            if(i.getTypeId() == 8714) {
                money += 6561 * i.getAmount();
            }
        }
        return money;
    }

    public double collectCoinPayment(double amount)
    {
        double coinsCollected = 0.0D;
        for (int i = 0; i < this.inventory.getSize(); i++)
        {
            int value = stackValue(this.inventory.getItem(i));
            if (value > 0)
            {
                coinsCollected += value;
                this.inventory.clear(i);
            }
            if (coinsCollected >= amount)
            {
                returnChange((int)coinsCollected - (int)amount);
                return 0.0D;
            }
        }
        return amount - coinsCollected;
    }

    public Integer coinAmount() {
        Integer coinsCollected = 0;
        for (int i = 0; i < this.inventory.getSize(); i++)
        {
            int value = stackValue(this.inventory.getItem(i));
            if (value > 0)
            {
                coinsCollected += value;
            }
        }
        return coinsCollected;
    }

    static Integer dado = 0;

    private void addItem(Integer valor) {
        if(valor >= 6561) {
            inventory.addItem(new ItemStack[] { new ItemStack(Material.getMaterial(this.itemNames[4])) });
            dado-=6561;
            return;
        }
        if(valor >= 729) {
            inventory.addItem(new ItemStack[] { new ItemStack(Material.getMaterial(this.itemNames[3])) });
            dado-=729;
            return;
        }
        if(valor >= 81) {
            inventory.addItem(new ItemStack[] { new ItemStack(Material.getMaterial(this.itemNames[2])) });
            dado-=81;
            return;
        }
        if(valor >= 9) {
            inventory.addItem(new ItemStack[] { new ItemStack(Material.getMaterial(this.itemNames[1])) });
            dado-=9;
            return;
        }
        if(valor >= 1) {
            inventory.addItem(new ItemStack[] { new ItemStack(Material.getMaterial(this.itemNames[0])) });
            dado-=1;
            return;
        }
    }

    public void returnChange(Integer valor) {
        dado = valor;
        while (dado > 0) {
            addItem(dado);
        }
    }

    /*public void returnChange(double change)
    {
        while (change > 0.0D)
        {
            int logVal = Math.min((int)(Math.log(change) / Math.log(9.0D)), 4);
            int stackSize = Math.min((int)(change / Math.pow(9.0D, logVal)), 64);

            HashMap<Integer, ItemStack> test = this.inventory.addItem(new ItemStack[] { new ItemStack(Material.getMaterial(this.itemNames[logVal])) });
            change -= stackSize * Math.pow(9.0D, logVal);
            if (test.size() > 0)
            {
                Iterator<Map.Entry<Integer, ItemStack>> it = test.entrySet().iterator();
                while (it.hasNext())
                {
                    Map.Entry<Integer, ItemStack> pair = (Entry<Integer, ItemStack>)it.next();
                    this.player.getWorld().dropItemNaturally(this.player.getLocation(), (ItemStack)pair.getValue());
                    it.remove();
                }
            }
        }
    }*/

    private int stackValue(ItemStack stack)
    {
        int[] coins = { 1, 9, 81, 729, 6561 };
        int inventoryValue = 0;
        for (int i = 0; i < coins.length; i++) {
            if ((stack != null) && (stack.getType().name().matches(this.itemNames[i]))) {
                inventoryValue += coins[i] * stack.getAmount();
            }
        }
        return inventoryValue;
    }

}
