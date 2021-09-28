package fr.omega2028.challenge.objects;

import fr.omega2028.challenge.enums.CategorieEnum;
import fr.omega2028.challenge.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    //Contient la progression d'un joueur dans ses challenges
    private List<PlayerProgressionData> playerChallengeProgression = new ArrayList<>();
    //Mémorise l'item cliqué dans le GUI pour choisir une catégorie de challenges
    private CategorieEnum clickedItems;
    //Le nom du challenge activé
    private PlayerProgressionData challengeActif;

    public List<PlayerProgressionData> getPlayerChallengeProgression() {
        return playerChallengeProgression;
    }

    //Active et sauvegarde le nom du challenge actif du joueur
    //Codes de retour :
    //-1 : Un autre challenge est déjà activé
    //0 : Le challenge est activé sans encombre
    public int activateChallenge(String nomChallenge) {
        for (PlayerProgressionData chal : playerChallengeProgression) {
            if (chal.isActived()) {
                return -1;
            }
        }
        challengeActif = ListUtil.recherchePlayer(nomChallenge,playerChallengeProgression);
        challengeActif.setActived(true);
        return 0;
    }

    public CategorieEnum getClickedItems() {
        return clickedItems;
    }

    public void setClickedItems(CategorieEnum clickedItems) {
        this.clickedItems = clickedItems;
    }

    public PlayerProgressionData getChallengeActif() {
        return challengeActif;
    }
}
