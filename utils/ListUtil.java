package fr.omega2028.challenge.utils;

import fr.omega2028.challenge.objects.PlayerProgressionData;
import fr.omega2028.challenge.objects.ServerChallengeData;

import java.util.List;

public class ListUtil {
    public static ServerChallengeData rechercheServer(String nomChallenge, List<ServerChallengeData> list) {
        for (ServerChallengeData challenge : list ) {
            if (challenge.getNomChallenge().equals(nomChallenge)) {
                return challenge;
            }
        }
        return null;
    }

    public static PlayerProgressionData recherchePlayer
            (String nomChallenge, List<PlayerProgressionData> list) {
        for (PlayerProgressionData challenge : list ) {
            if (challenge.getNomChallenge().equals(nomChallenge)) {
                return challenge;
            }
        }
        return null;
    }
}
