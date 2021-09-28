package fr.omega2028.challenge.commands;

import fr.omega2028.challenge.Challenge;
import fr.omega2028.challenge.objects.ServerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ChallengeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 1 && args[0].equals("reload")){
            Challenge.getInstance().setConfigData(Challenge.getInstance().getPersist().load(ServerData.class,
                    new File(Challenge.getInstance().getConfigPath() + "\\config.json")));
            //Actualiser la liste de challenges des players
            return true;
        }
        Challenge.getInstance().getChallengeMenu().open(player);
        return true;
    }
}
