package fr.omega2028.challenge.objects;

import fr.omega2028.challenge.enums.ActionEnum;
import fr.omega2028.challenge.enums.CategorieEnum;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class ServerChallengeData {
    int basique = 0;
    int intermediaire = 0;
    int maitre = 0;
    String nomChallenge;
    CategorieEnum categorie;
    ActionEnum typeAction;
    Material icon = Material.BARRIER;

    Material material = Material.AIR;
    EntityType entityType = EntityType.PIG;

    public ServerChallengeData(String nomChallenge, CategorieEnum categorie, Material icon, ActionEnum action,
    int basique, int intermediaire, int maitre) {
        this.nomChallenge = nomChallenge;
        this.categorie = categorie;
        this.icon = icon;
        this.typeAction = action;
        this.basique = basique;
        this.intermediaire = intermediaire;
        this.maitre = maitre;
    }
    public ServerChallengeData(String nomChallenge, CategorieEnum categorie, Material icon, ActionEnum action,
                               int basique, int intermediaire, int maitre, Material material) {
        this(nomChallenge, categorie, icon, action, basique, intermediaire, maitre);
        this.material = material;
    }

    public ServerChallengeData(String nomChallenge, CategorieEnum categorie, Material icon, ActionEnum action,
                               int basique, int intermediaire, int maitre, EntityType entityType) {
        this.entityType = entityType;

    }
    public ActionEnum getTypeAction() {
        return typeAction;
    }

    public CategorieEnum getCategorie() {
        return categorie;
    }

    public int getBasique() {
        return basique;
    }

    public int getIntermediaire() {
        return intermediaire;
    }

    public int getMaitre() {
        return maitre;
    }

    public String getNomChallenge() {
        return nomChallenge;
    }

    public Material getMaterial() {
        return material;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Material getIcon() {
        return icon;
    }
}
