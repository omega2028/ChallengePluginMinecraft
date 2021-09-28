package fr.omega2028.challenge.objects;

import fr.omega2028.challenge.Challenge;
import fr.omega2028.challenge.enums.ActionEnum;
import fr.omega2028.challenge.enums.DifficulteEnum;
import fr.omega2028.challenge.utils.ListUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class PlayerProgressionData {
    //Nom du challenge
    String nomChallenge;
    //Nombre d'actions effectués dans ce challenge
    private int actions;
    //Si le challenge n'est pas activé, le nombre d'actions ne change pas
    private boolean actived = false;
    //Enumération qui sauvegarde le niveau de challenge où en est le joueur
    private DifficulteEnum difficulte = DifficulteEnum.BASIQUE;

    public PlayerProgressionData(String nomChallenge) {
        this.nomChallenge = nomChallenge;
    }
    //Fait une action.
    //nomChallenge est utilisé pour récolter les données du challenge coté serveur, il sert aussi à de l'affichage
    //player sert juste a envoyer des messages au joueurn lui donner de l'argent...
    public void makeAction (String nomChallenge, Player player, ActionEnum actionEnum, Material block){
        if (!actived) return;
        if (ListUtil.rechercheServer(nomChallenge, Challenge.getInstance().getConfigData().getChallengeDataList()) == null) {
            return;
        }
        ServerChallengeData chalData  =
                ListUtil.rechercheServer(nomChallenge,
                        Challenge.getInstance().getConfigData().getChallengeDataList());
        if (chalData.getMaterial() != block) return;
        actions = actions + 1;
        checkChalTerminated(nomChallenge, player);
    }
    public void makeAction (String nomChallenge, Player player, ActionEnum actionEnum, EntityType entityType) {
        if (!actived) return;
        if (ListUtil.rechercheServer(nomChallenge, Challenge.getInstance().getConfigData().getChallengeDataList()) == null) {
            return;
        }
        ServerChallengeData chalData  =
                ListUtil.rechercheServer(nomChallenge,
                        Challenge.getInstance().getConfigData().getChallengeDataList());
        if (chalData.getEntityType() != entityType) return;
        actions = actions + 1;
        checkChalTerminated(nomChallenge, player);
    }
    public void makeAction (String nomChallenge, Player player, ActionEnum actionEnum) {
        if (!actived) return;
        if (ListUtil.rechercheServer(nomChallenge, Challenge.getInstance().getConfigData().getChallengeDataList()) == null) {
            return;
        }
        ServerChallengeData chalData  =
                ListUtil.rechercheServer(nomChallenge,
                        Challenge.getInstance().getConfigData().getChallengeDataList());
        if (actionEnum != chalData.typeAction) return;
        actions = actions + 1;
        checkChalTerminated(nomChallenge, player);
    }
    //Regarde si le challenge est terminé
    private void checkChalTerminated (String nomChallenge, Player player){
        switch (difficulte) {
            case BASIQUE:
                if (ListUtil.rechercheServer(nomChallenge, Challenge.getInstance().getConfigData().getChallengeDataList())
                        .getBasique() >= actions) {
                    difficulte = DifficulteEnum.INTERMEDIARE;
                    player.sendMessage(ChatColor.GRAY + "Vous avez terminé le challenge "
                            + ChatColor.WHITE + nomChallenge + ChatColor.GRAY + " difficulté "
                            + ChatColor.WHITE + "basique" + ChatColor.GRAY + " !");
                    player.sendMessage(ChatColor.GRAY + "Vous pouvez sélectionner un nouveau challenge dans le "
                            + ChatColor.WHITE + "/challenges" + ChatColor.GRAY + "." );
                    // give de 25000 sparks
                    actived = false;
                    actions = 0;
                }
                break;
            case INTERMEDIARE:
                if (ListUtil.rechercheServer(nomChallenge, Challenge.getInstance().getConfigData().getChallengeDataList())
                        .getIntermediaire() >= actions) {
                    difficulte = DifficulteEnum.MAITRE;
                    player.sendMessage(ChatColor.GRAY + "Vous avez terminé le challenge "
                            + ChatColor.WHITE + nomChallenge + ChatColor.GRAY + " difficulté "
                            + ChatColor.WHITE + "intermédiaire" + ChatColor.GRAY + " !");
                    player.sendMessage(ChatColor.GRAY + "Vous pouvez sélectionner un nouveau challenge dans le "
                            + ChatColor.WHITE + "/challenges" + ChatColor.GRAY + "." );
                    //give de 50000 sparks
                    actived = false;
                    actions = 0;
                }
                break;
            case MAITRE:
                if (ListUtil.rechercheServer(nomChallenge, Challenge.getInstance().getConfigData().getChallengeDataList())
                        .getMaitre() >= actions) {
                    player.sendMessage(ChatColor.GRAY + "Vous avez terminé le challenge "
                            + ChatColor.WHITE + nomChallenge + ChatColor.GRAY + " difficulté "
                            + ChatColor.WHITE + "maître" + ChatColor.GRAY + " !");
                    player.sendMessage(ChatColor.GRAY + "Vous pouvez sélectionner un nouveau challenge dans le "
                            + ChatColor.WHITE + "/challenges" + ChatColor.GRAY + "." );
                    //give de 75000 sparks
                    actived = false;
                    actions = 0;
                }
                break;
            default:
                break;
        }
    }

    public DifficulteEnum getDifficulte() {
        return difficulte;
    }

    public int getActions() {
        return actions;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public String getNomChallenge() {
        return nomChallenge;
    }
}