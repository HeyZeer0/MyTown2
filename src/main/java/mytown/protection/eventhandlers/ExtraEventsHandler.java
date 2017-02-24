package mytown.protection.eventhandlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.versioning.ComparableVersion;
import myessentials.chat.api.ChatComponentFormatted;
import myessentials.chat.api.ChatManager;
import myessentials.entities.api.ChunkPos;
import myessentials.localization.api.LocalManager;
import myessentials.utils.ChatUtils;
import myessentials.utils.WorldUtils;
import mytown.MyTown;
import mytown.api.container.ResidentRankMap;
import mytown.config.Config;
import mytown.entities.*;
import mytown.new_datasource.MyTownUniverse;
import mytown.entities.flag.FlagType;
import mytown.util.exceptions.MyTownCommandException;
import net.minecraft.item.Item;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ExplosionEvent;

import java.util.List;
import java.util.TimerTask;

/**
 * Handling any events that are not yet compatible with the most commonly used version of forge.
 */
public class ExtraEventsHandler {

    private static ExtraEventsHandler instance;
    public static ExtraEventsHandler getInstance() {
        if(instance == null)
            instance = new ExtraEventsHandler();
        return instance;
    }

    /**
     * Forge 1254 is needed for this
     */
    @SubscribeEvent
    public void onExplosion(ExplosionEvent.Start ev) {
        if(ev.world.isRemote)
            return;
        if (ev.isCanceled())
            return;
        List<ChunkPos> chunks = WorldUtils.getChunksInBox(ev.world.provider.dimensionId, (int) (ev.explosion.explosionX - ev.explosion.explosionSize - 2), (int) (ev.explosion.explosionZ - ev.explosion.explosionSize - 2), (int) (ev.explosion.explosionX + ev.explosion.explosionSize + 2), (int) (ev.explosion.explosionZ + ev.explosion.explosionSize + 2));
        for(ChunkPos chunk : chunks) {
            TownBlock block = MyTownUniverse.instance.blocks.get(ev.world.provider.dimensionId, chunk.getX(), chunk.getZ());
            if(block == null) {
                if(!(Boolean)Wild.instance.flagsContainer.getValue(FlagType.EXPLOSIONS)) {
                    ev.setCanceled(true);
                    return;
                }
            } else {
                if (!(Boolean) block.getTown().flagsContainer.getValue(FlagType.EXPLOSIONS)) {
                    ev.setCanceled(true);
                    block.getTown().notifyEveryone(MyTown.instance.LOCAL.getLocalization(FlagType.EXPLOSIONS.getTownNotificationKey()));
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    public void stick(PlayerInteractEvent e) {
        if(e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || e.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            if(e.entityPlayer == null || e.entityPlayer.getCurrentEquippedItem() == null || e.entityPlayer.getCurrentEquippedItem().getItem() == null) {
                return;
            }
            if(e.entityPlayer.getCurrentEquippedItem().getItem() == GameRegistry.findItem("minecraft", "stick")) {
                final Resident res = MyTownUniverse.instance.getOrMakeResident(e.entityPlayer);

                final TownBlock b = MyTownUniverse.instance.blocks.get(res.getPlayer().dimension, res.getPlayer().chunkCoordX, res.getPlayer().chunkCoordZ);

                if(b == null || b.getTown() == null) {
                    return;
                }

                Town town = b.getTown();

                if(town instanceof AdminTown) {
                    return;
                }

                b.getTown().townBlocksContainer.show(MyTownUniverse.instance.getOrMakeResident(e.entityPlayer));
                ResidentRankMap rankmap = (ResidentRankMap) town.residentsMap.clone();
                rankmap.remove(rankmap.getMayor());

                String upkeep = "";

                if(Config.instance.costTownUpkeep.get() <= 0) {
                    upkeep = "Desativado";
                }else{
                    upkeep = String.valueOf(Config.instance.upkeepTownDeletionDays.get() - town.bank.getDaysNotPaid());
                }

                ChatManager.send(e.entityPlayer, "mytown.format.stick.long", town.getName(), town.residentsMap.getMayor().getPlayerName(), rankmap, upkeep);

                MyTown.instance.timer.schedule(
                    new TimerTask() {
                                @Override
                                public void run() {
                                    b.getTown().townBlocksContainer.hide(res);
                                }
                            }
                , 5000);

            }
        }
    }
}
