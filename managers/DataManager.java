package fr.omega2028.challenge.managers;

import fr.omega2028.challenge.Challenge;
import fr.omega2028.challenge.objects.PlayerData;
import fr.omega2028.challenge.objects.PlayerProgressionData;
import fr.omega2028.challenge.objects.ServerChallengeData;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class DataManager {
    //Relie un joueur à ses informations
    private final Map<UUID, PlayerData> dataCache;

    public DataManager() {
        dataCache = new HashMap<>();
    }

    //Insère dans le dataCache un nouveau joueur
    public void createPersonnalData(Player player) {
        if (dataCache.containsKey(player.getUniqueId())) return;
        dataCache.put(player.getUniqueId(), new PlayerData());
        for (ServerChallengeData chalServData : Challenge.getInstance().getConfigData().getChallengeDataList()) {
            Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                    .getPlayerChallengeProgression()
                    .add(new PlayerProgressionData(chalServData.getNomChallenge()));
        }
    }

    public void loadPersonnalData(Player player) {
        File playerPath = new File(Challenge.getInstance().getPlayerProgressionPath() + "\\"
                + player.getUniqueId() + ".json");
        if (!playerPath.exists()) {
            createPersonnalData(player);
            return;
        }
        PlayerData loadedPlayerData = Challenge.getInstance().getPersist().load(PlayerData.class,
                playerPath);
        dataCache.put(player.getUniqueId(), loadedPlayerData);
    }

    public void savePersonnalData(Player player) {
        File playerPath = new File( Challenge.getInstance().getDataFolder().getPath() + "\\PlayerData\\"
                + player.getUniqueId() + ".json");
        Challenge.getInstance().getPersist().save(dataCache.get(player.getUniqueId()),playerPath);
    }

    public Map<UUID, PlayerData> getDataCache() {
        return dataCache;
    }
}
