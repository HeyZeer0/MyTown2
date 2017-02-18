package myessentials.economy.core.universalcoins.vaultconnection;

import java.text.DecimalFormat;
import java.util.List;

import myessentials.economy.core.universalcoins.CoinUtil;
import myessentials.economy.core.universalcoins.TransactionUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import universalcoins.util.UniversalAccounts;

public class UniversalCoins_Economy
        implements Economy
{
    public static Economy econ;

    public String getName()
    {
        return "UniversalCoins-Vault";
    }

    public boolean hasBankSupport()
    {
        return false;
    }

    public EconomyResponse bankBalance(String arg0)
    {
        return null;
    }

    public EconomyResponse bankDeposit(String arg0, double arg1)
    {
        return null;
    }

    public EconomyResponse bankHas(String arg0, double arg1)
    {
        return null;
    }

    public EconomyResponse bankWithdraw(String arg0, double arg1)
    {
        return null;
    }

    public EconomyResponse createBank(String arg0, String arg1)
    {
        return null;
    }

    public EconomyResponse createBank(String arg0, OfflinePlayer arg1)
    {
        return null;
    }

    public boolean createPlayerAccount(String arg0)
    {
        @SuppressWarnings("deprecation")
        Player player = Bukkit.getPlayer(arg0);
        if (player == null) {
            return false;
        }
        return createPlayerAccount(player);
    }

    public boolean createPlayerAccount(OfflinePlayer arg0)
    {
        return UniversalAccounts.getInstance().addPlayerAccount(arg0.getUniqueId().toString());
    }

    public boolean createPlayerAccount(String arg0, String arg1)
    {
        @SuppressWarnings("deprecation")
        Player player = Bukkit.getPlayer(arg0);
        if (player == null) {
            return false;
        }
        return createPlayerAccount(player);
    }

    public boolean createPlayerAccount(OfflinePlayer arg0, String arg1)
    {
        return createPlayerAccount(arg0);
    }

    public String currencyNamePlural()
    {
        return "coins";
    }

    public String currencyNameSingular()
    {
        return "coin";
    }

    public EconomyResponse deleteBank(String arg0)
    {
        return null;
    }

    public EconomyResponse depositPlayer(String arg0, double arg1)
    {
        String accountNumber = UniversalAccounts.getInstance().getOrCreatePlayerAccount(Bukkit.getPlayer(arg0).getUniqueId().toString());
        TransactionUtil.addToBank(accountNumber, (int)arg1);
        return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
    }

    public EconomyResponse depositPlayer(OfflinePlayer arg0, double arg1)
    {
        String accountNumber = UniversalAccounts.getInstance().getOrCreatePlayerAccount(arg0.getUniqueId().toString());
        if(TransactionUtil.addToBank(accountNumber, (int)arg1)) {
            return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(0, 0, ResponseType.FAILURE, "Fail");
    }

    public EconomyResponse depositPlayer(String arg0, String arg1, double arg2)
    {
        OfflinePlayer player = Bukkit.getOfflinePlayer(arg0);
        if (player == null) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Failed to get offline player");
        }
        String accountNumber = UniversalAccounts.getInstance().getOrCreatePlayerAccount(player.getUniqueId().toString());
        if(TransactionUtil.addToBank(accountNumber, (int)arg2)) {
            return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(0, 0, ResponseType.FAILURE, "Fail");
    }

    public EconomyResponse depositPlayer(OfflinePlayer arg0, String arg1, double arg2)
    {
        return depositPlayer(arg0, arg2);
    }

    public String format(double arg0)
    {
        DecimalFormat format = new DecimalFormat("#,##0.00");
        String s = format.format(arg0);
        if (s.endsWith(".")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public int fractionalDigits()
    {
        return 0;
    }

    public double getBalance(String arg0)
    {
        @SuppressWarnings("deprecation")
        OfflinePlayer player = Bukkit.getOfflinePlayer(arg0);
        if (player == null) {
            return 0.0D;
        }
        return getBalance(player);
    }

    public double getBalance(OfflinePlayer arg0)
    {
        String accountNumber = UniversalAccounts.getInstance().getPlayerAccount(arg0.getUniqueId().toString());
        return UniversalAccounts.getInstance().getAccountBalance(accountNumber);
    }

    public double getBalance(String arg0, String arg1)
    {
        @SuppressWarnings("deprecation")
        OfflinePlayer player = Bukkit.getOfflinePlayer(arg0);
        if (player == null) {
            return 0.0D;
        }
        return getBalance(player);
    }

    public double getBalance(OfflinePlayer arg0, String arg1)
    {
        return getBalance(arg0);
    }

    public List<String> getBanks()
    {
        return null;
    }

    public boolean has(String arg0, double arg1)
    {
        @SuppressWarnings("deprecation")
        OfflinePlayer player = Bukkit.getOfflinePlayer(arg0);
        if (player == null) {
            return false;
        }
        return hasAccount(player);
    }

    public boolean has(OfflinePlayer arg0, double arg1)
    {
        int playerBalance = 0;
        String accountNumber = UniversalAccounts.getInstance().getPlayerAccount(arg0.getUniqueId().toString());
        if (UniversalAccounts.getInstance().getAccountBalance(accountNumber) >= arg1) {
            return true;
        }
        return false;
    }

    public boolean has(String arg0, String arg1, double arg2)
    {
        @SuppressWarnings("deprecation")
        OfflinePlayer player = Bukkit.getOfflinePlayer(arg0);
        if (player == null) {
            return false;
        }
        return has(player, arg2);
    }

    public boolean has(OfflinePlayer arg0, String arg1, double arg2)
    {
        return has(arg0, arg2);
    }

    public boolean hasAccount(String arg0)
    {
        @SuppressWarnings("deprecation")
        OfflinePlayer player = Bukkit.getOfflinePlayer(arg0);
        if (player == null) {
            return false;
        }
        return hasAccount(player);
    }

    public boolean hasAccount(OfflinePlayer arg0)
    {
        String accountNumber = UniversalAccounts.getInstance().getOrCreatePlayerAccount(arg0.getUniqueId().toString());
        if ((accountNumber != null) && (!accountNumber.isEmpty())) {
            return true;
        }
        return false;
    }

    public boolean hasAccount(String arg0, String arg1)
    {
        @SuppressWarnings("deprecation")
        OfflinePlayer player = Bukkit.getOfflinePlayer(arg0);
        if (player == null) {
            return false;
        }
        return hasAccount(player);
    }

    public boolean hasAccount(OfflinePlayer arg0, String arg1)
    {
        return hasAccount(arg0);
    }

    public EconomyResponse isBankMember(String arg0, String arg1)
    {
        return null;
    }

    public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1)
    {
        return null;
    }

    public EconomyResponse isBankOwner(String arg0, String arg1)
    {
        return null;
    }

    public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1)
    {
        return null;
    }

    public boolean isEnabled()
    {
        return true;
    }

    public EconomyResponse withdrawPlayer(String arg0, double arg1)
    {
        OfflinePlayer player = Bukkit.getOfflinePlayer(arg0);
        if (player == null) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Failed to get offline player");
        }
        return withdrawPlayer(player, arg1);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount)
    {
        Integer inventario = new CoinUtil(player.getPlayer()).coinAmount();
        if(inventario != 0) {
            if (inventario >= (int)amount)
            {
                new CoinUtil(player.getPlayer()).collectCoinPayment(amount);
                return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
            }else{
                if(getBalance(player) >= inventario - amount) {
                    TransactionUtil.removeFromBank(UniversalAccounts.getInstance().getPlayerAccount(player.getUniqueId().toString()), (int)amount - inventario);
                    new CoinUtil(player.getPlayer()).collectCoinPayment(inventario);
                    return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
                }
                return new EconomyResponse(0, 0, ResponseType.FAILURE, "Fail");
            }
        }else{
            if(getBalance(player) >= amount) {
                if(TransactionUtil.removeFromBank(UniversalAccounts.getInstance().getPlayerAccount(player.getUniqueId().toString()), (int)amount)) {
                    return new EconomyResponse(0, 0, ResponseType.SUCCESS, "");
                }
                return new EconomyResponse(0, 0, ResponseType.FAILURE, "fail");
        }
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "fail");
        }
    }

    public EconomyResponse withdrawPlayer(String arg0, String arg1, double arg2)
    {
        OfflinePlayer player = Bukkit.getOfflinePlayer(arg0);
        if (player == null) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "Failed to get offline player");
        }
        return withdrawPlayer(player, arg2);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer arg0, String arg1, double arg2)
    {
        return withdrawPlayer(arg0, arg2);
    }


}
