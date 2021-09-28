package fr.omega2028.challenge.objects;


import java.util.ArrayList;
import java.util.List;

public class ServerData {
    //Contient toutes les données d'un challenge.
    private List<ServerChallengeData> challengeDataList = new ArrayList<>();

    public List<ServerChallengeData> getChallengeDataList() {
        return challengeDataList;
    }

    @Override
    public String toString() {
        return "ServerData{" +
                "challengeDataList=" + challengeDataList +
                '}';
    }
}
