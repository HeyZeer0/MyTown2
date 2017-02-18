package myessentials.economy.core.universalcoins;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;

import br.com.gamemods.universalcoinsserver.UniversalCoinsServer;
import br.com.gamemods.universalcoinsserver.datastore.Transaction;
import myessentials.economy.api.IEconManager;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import universalcoins.util.UniversalAccounts;

/**
 * Created by HeyZeer0 on 17/10/2016.
 */
public class UniversalCoinsEconomy implements IEconManager {


    public static Economy econ;
    private OfflinePlayer player;

    public UniversalCoinsEconomy(UUID uuid) {
        player = Bukkit.getServer().getOfflinePlayer(uuid);
    }

    public UniversalCoinsEconomy() {

    }

    @Override
    public void addToWallet(int arg0) {
        if(!TransactionUtil.hasAccount(player)) {
            try{
                TransactionUtil.addToBank(UniversalCoinsServer.cardDb.createPrimaryAccount(player.getUniqueId(), player.getName()).getNumber().toString(), arg0);
            }catch (Exception e) {}
            return;
        }
        String accountNumber = UniversalAccounts.getInstance().getOrCreatePlayerAccount(player.getUniqueId().toString());
        TransactionUtil.addToBank(accountNumber, arg0);
    }

    @Override
    public String currency(int arg0) {
        DecimalFormat format = new DecimalFormat("#,##0.00");
        String s = format.format(arg0);
        if (s.endsWith(".")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    @Override
    public Map<String, Integer> getItemTables() {
        return null;
    }

    @Override
    public String getMoneyString() {
        return new Integer(getWallet()).toString();
    }

    @Override
    public int getWallet() {
        String accountNumber = UniversalAccounts.getInstance().getPlayerAccount(player.getUniqueId().toString());
        return UniversalAccounts.getInstance().getAccountBalance(accountNumber);
    }

    @Override
    public boolean removeFromWallet(int arg0) {
        Integer inventario = new CoinUtil(player.getPlayer()).coinAmount();
        if(inventario != 0) {
            if (inventario >= (int)arg0)
            {
                new CoinUtil(player.getPlayer()).collectCoinPayment(arg0);
                return true;
            }else{
                if(getWallet() >= arg0 - inventario) {
                    if(!TransactionUtil.hasAccount(player)) {
                        return false;
                    }
                    TransactionUtil.removeFromBank(UniversalAccounts.getInstance().getPlayerAccount(player.getUniqueId().toString()), arg0 - inventario);
                    new CoinUtil(player.getPlayer()).collectCoinPayment(inventario);
                    return true;
                }
                return false;
            }
        }else{
            if(getWallet() >= arg0) {
                if(!TransactionUtil.hasAccount(player)) {
                    return false;
                }
                return TransactionUtil.removeFromBank(UniversalAccounts.getInstance().getPlayerAccount(player.getUniqueId().toString()), arg0);
            }
            return false;
        }
    }

    @Override
    public void save() {}

    @Override
    public void setPlayer(UUID arg0) {
        player = Bukkit.getServer().getOfflinePlayer(arg0);
    }

    @Override
    public void setWallet(int arg0, EntityPlayer arg1) {}


}
