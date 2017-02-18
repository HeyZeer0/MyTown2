package myessentials.economy.api;

/**
 * Created by HeyZeer0 on 18/10/2016.
 */
public class EconomyAPI {

    private static EconomyAPI instance = new EconomyAPI();

    public static EconomyAPI getInstance()
    {
        return instance;
    }

    Economy h = new Economy("$Vault");

    public Economy getEconomy() {
        return h;
    }


}
