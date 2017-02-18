package myessentials.economy.core.universalcoins;

import br.com.gamemods.universalcoinsserver.UniversalCoinsServer;
import br.com.gamemods.universalcoinsserver.api.UniversalCoinsServerAPI;
import br.com.gamemods.universalcoinsserver.datastore.DataBaseException;
import br.com.gamemods.universalcoinsserver.datastore.PlayerData;
import br.com.gamemods.universalcoinsserver.datastore.PlayerOperator;
import br.com.gamemods.universalcoinsserver.datastore.Transaction;
import br.com.gamemods.universalcoinsserver.tile.TileCardStation;
import br.com.gamemods.universalcoinsserver.tile.TileTransactionMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import universalcoins.util.UniversalAccounts;

import java.util.UUID;

/**
 * Created by HeyZeer0 on 17/10/2016.
 */
public class TransactionUtil{

    public static boolean removeFromBank(String account, Integer value) {
        try {
            UUID id = UniversalCoinsServer.cardDb.getAccountOwner(account);
            EntityPlayer player = UniversalAccounts.findPlayer(id);
            PlayerData playerData = UniversalCoinsServer.cardDb.getPlayerData(id);

            if (playerData.hasPrimaryAccount()) {
                try{
                    Transaction transaction = new Transaction(null, Transaction.Operation.WITHDRAW_FROM_ACCOUNT_TO_MACHINE, new PlayerOperator(player), null, new Transaction.CardCoinSource(playerData.getPrimaryAccount(), -value), new ItemStack(Item.getItemById(0)));
                    UniversalCoinsServer.cardDb.takeFromAccount(playerData.getPrimaryAccount(), value, transaction);
                    return true;
                }catch(Exception e) {
                    System.out.println(e);
                    return false;
                }
            }else{
                return false;
            }
        }catch (Exception localException) {
            System.out.println(localException);
            return false;
        }
    }

    public static boolean addToBank(String account, Integer value) {
        try {
            UUID id = UniversalCoinsServer.cardDb.getAccountOwner(account);
            EntityPlayer player = UniversalAccounts.findPlayer(id);
            PlayerData playerData = UniversalCoinsServer.cardDb.getPlayerData(id);

            if (playerData.hasPrimaryAccount()) {
                try{
                    Transaction transaction = new Transaction(null, Transaction.Operation.DEPOSIT_TO_ACCOUNT_BY_API, new PlayerOperator(player), null, new Transaction.CardCoinSource(playerData.getPrimaryAccount(), value), new ItemStack(Item.getItemById(0)));
                    UniversalCoinsServer.cardDb.depositToAccount(playerData.getPrimaryAccount(), value, transaction);
                    return true;
                }catch(Exception e) {
                    System.out.println(e);
                    return false;
                }
            }else{
                return false;
            }
        }catch (Exception localException) {
            System.out.println(localException);
            return false;
        }
    }

    public static boolean hasAccount(Player p) {
        String accountNumber = UniversalAccounts.getInstance().getOrCreatePlayerAccount(p.getUniqueId().toString());
        if ((accountNumber != null) && (!accountNumber.isEmpty())) {
            return true;
        }
        if(accountNumber.equalsIgnoreCase(" ")) {
            return false;
        }
        return false;
    }

    public static boolean hasAccount(OfflinePlayer p) {
        String accountNumber = UniversalAccounts.getInstance().getOrCreatePlayerAccount(p.getUniqueId().toString());
        if ((accountNumber != null) && (!accountNumber.isEmpty())) {
            return true;
        }
        if(accountNumber.equalsIgnoreCase(" ")) {
            return false;
        }
        return false;
    }


}
