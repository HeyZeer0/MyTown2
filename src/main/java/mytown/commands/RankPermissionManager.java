package mytown.commands;

import myessentials.chat.api.ChatManager;
import myessentials.utils.PlayerUtils;
import mypermissions.permission.core.bridge.IPermissionBridge;
import mypermissions.permission.core.entities.PermissionLevel;
import mytown.new_datasource.MyTownUniverse;
import mytown.entities.Resident;
import mytown.entities.Town;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class
RankPermissionManager implements IPermissionBridge {

    @Override
    public boolean hasPermission(UUID uuid, String permission) {
        if(permission.startsWith("mytown.cmd.outsider") || permission.equals("mytown.cmd"))
            return true;

        EntityPlayer player = PlayerUtils.getPlayerFromUUID(uuid);
        Resident resident = MyTownUniverse.instance.getOrMakeResident(player);
        Town town = Commands.getTownFromResident(resident);
        if(town != null) {
            if(town.residentsMap.get(resident).permissionsContainer.hasPermission(permission) != PermissionLevel.ALLOWED) {
                ChatManager.send(player, "mytown.cmd.err.rankPerm");
                return false;
            }
        }else{
            ChatManager.send(player, "mytown.cmd.err.partOfTown");
            return false;
        }
        return true;
    }
}
