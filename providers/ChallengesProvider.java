package fr.omega2028.challenge.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.omega2028.challenge.Challenge;
import fr.omega2028.challenge.enums.CategorieEnum;
import fr.omega2028.challenge.objects.ServerChallengeData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.function.BiConsumer;

public class ChallengesProvider implements InventoryProvider {


    @Override
    public void init(Player player, InventoryContents contents) {
        //Mise en place de la décoration de l'interface
        Pagination pagination = contents.pagination();
        for (int slot : Arrays.asList(2, 3, 5, 6, 18, 26, 38, 39, 41, 42)) {
            contents.set(slot / 9, slot % 9,
                    ClickableItem.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));
        }
        for (int slot : Arrays.asList(0, 1, 7, 8, 9, 17, 27, 35, 37, 43, 44)) {
            contents.set(slot / 9, slot % 9,
                    ClickableItem.empty(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        }
        pagination.setItemsPerPage(17);

        //Mise en place des boutons fonctionnels de l'interface
        ItemStack arrowRight = new ItemStack(Material.ARROW);
        ItemMeta itAR = arrowRight.getItemMeta();
        itAR.setDisplayName("Page Suivante");
        arrowRight.setItemMeta(itAR);

        ItemStack arrowLeft = new ItemStack(Material.ARROW);
        ItemMeta itAL = arrowLeft.getItemMeta();
        itAL.setDisplayName("Page Précédente");
        arrowLeft.setItemMeta(itAL);
        if (!(pagination.isFirst() && pagination.isLast())) {
            if (pagination.isLast()) {
                contents.set(4, 3, ClickableItem.of(arrowLeft, e ->
                        Challenge.getInstance().getChallengesChooseGUI()
                        .open(player, pagination.previous().getPage())));
            } else if (pagination.isFirst()) {
                contents.set(4, 5, ClickableItem.of(arrowRight, e ->
                        Challenge.getInstance().getChallengesChooseGUI()
                        .open(player, pagination.next().getPage())));
            }
            contents.set(4, 3, ClickableItem.of(arrowLeft, e ->
                    Challenge.getInstance().getChallengesChooseGUI()
                            .open(player, pagination.previous().getPage())));
            contents.set(4, 5, ClickableItem.of(arrowRight, e ->
                    Challenge.getInstance().getChallengesChooseGUI()
                            .open(player, pagination.next().getPage())));
        }

        ItemStack returnButton = new ItemStack(Material.BARRIER);
        ItemMeta itReturn = returnButton.getItemMeta();
        itReturn.setDisplayName("Retour");
        returnButton.setItemMeta(itReturn);
        contents.set(4,0,ClickableItem.of(returnButton, event ->
                Challenge.getInstance().getChallengeMenu().open(player)));


        //Génération des challenges sous forme d'items en utilisant les données du modèle
        List<ClickableItem> chalsTmp = new ArrayList<>();
        makeItems(player, Challenge.getInstance().getDataManager().getDataCache()
                .get(player.getUniqueId()).getClickedItems(), (item, name) -> {
            chalsTmp.add(ClickableItem.of(item, action -> {
                onClick(player, name);
            }));
        });
        ClickableItem[] challenge = new ClickableItem[chalsTmp.size()];
        int cpt = 0;
        for (ClickableItem it : chalsTmp) {
            challenge[cpt] = chalsTmp.get(cpt);
            cpt ++;
        }
        pagination.setItems(challenge);
        Bukkit.getLogger().info(challenge.toString());
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private String progressBar(int progression, int total) {
        double prog = (double) progression;
        double tot = (double) total;
        double pourcentage = (prog / tot) * 100;
        String greenBar = "";
        double cpt = 0;
        int nbBars = 0;
        while (cpt < pourcentage) {
            greenBar = greenBar.concat("|");
            cpt = cpt + 2.5;
            nbBars++;
        }
        greenBar = ChatColor.GREEN + greenBar;
        if (36 - nbBars == 0) {
            return greenBar + ChatColor.WHITE + " - " + ChatColor.GRAY + (int)pourcentage + "%";
        }
        String redBars = "";
        for (int i = 0; i< 36 - nbBars; i++) {
            redBars = redBars.concat("|");
        }
        redBars = ChatColor.RED + redBars;
        return greenBar + redBars + ChatColor.WHITE + " - " + ChatColor.GRAY + (int)pourcentage + "%";
    }

    private void makeItems(Player player, CategorieEnum categorie, BiConsumer<ItemStack, String> consumer) {
        int cpt = 0;
        for (ServerChallengeData servChalData : Challenge.getInstance().getConfigData().getChallengeDataList()) {
            if (!servChalData.getCategorie().equals(categorie)) continue; //saute l'itération actuelle
            ItemStack challengeItem = new ItemStack(Challenge.getInstance().getConfigData().getChallengeDataList()
            .get(cpt).getIcon());
            ItemMeta chalM = challengeItem.getItemMeta();

            chalM.setDisplayName(Challenge.getInstance().getConfigData().getChallengeDataList().get(cpt)
                    .getNomChallenge());
            if (Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                    .getChallengeActif() == null) {
                chalM.setLore(List.of(
                        "",
                        ChatColor.YELLOW + ">> " + ChatColor.GRAY + "Cliquez pour " + ChatColor.GREEN + "activer ",
                        ChatColor.GRAY + "   la quête."
                ));
            } else if (Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                    .getChallengeActif().isActived()) {
                chalM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
                switch (Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                        .getChallengeActif().getDifficulte()) {
                    case BASIQUE:
                        chalM.setLore(List.of(
                                "",
                                ChatColor.GRAY + "Niveau Actuel :",
                                ChatColor.YELLOW + " >> " + ChatColor.WHITE + "Basique",
                                ChatColor.WHITE + "    Intermédiaire",
                                ChatColor.WHITE + "    Maître",
                                ChatColor.GRAY + "_________________",
                                ChatColor.YELLOW + " | " + ChatColor.GRAY + Challenge.getInstance()
                                        .getDataManager().getDataCache().get(player.getUniqueId())
                                        .getPlayerChallengeProgression().get(cpt).getActions()
                                        + ChatColor.WHITE + "/" + Challenge.getInstance().getConfigData()
                                        .getChallengeDataList().get(cpt).getBasique(),
                                ChatColor.YELLOW + " | " + progressBar(
                                        Challenge.getInstance()
                                            .getDataManager().getDataCache().get(player.getUniqueId())
                                            .getPlayerChallengeProgression().get(cpt).getActions(),
                                        Challenge.getInstance().getConfigData()
                                                .getChallengeDataList().get(cpt).getBasique()
                                )
                        ));
                        break;
                    case INTERMEDIARE:
                        chalM.setLore(List.of(
                                "",
                                ChatColor.GRAY + "Niveau Actuel :",
                                ChatColor.WHITE + "    Basique",
                                ChatColor.YELLOW + " >> " + ChatColor.WHITE + "Intermédiaire",
                                ChatColor.WHITE + "    Maître",
                                ChatColor.GRAY + "_________________",
                                ChatColor.YELLOW + " | " + ChatColor.GRAY + Challenge.getInstance()
                                        .getDataManager().getDataCache().get(player.getUniqueId())
                                        .getPlayerChallengeProgression().get(cpt).getActions()
                                        + ChatColor.WHITE + "/" + Challenge.getInstance().getConfigData()
                                        .getChallengeDataList().get(cpt).getIntermediaire(),
                                ChatColor.YELLOW + " | " + progressBar(
                                        Challenge.getInstance()
                                                .getDataManager().getDataCache().get(player.getUniqueId())
                                                .getPlayerChallengeProgression().get(cpt).getActions(),
                                        Challenge.getInstance().getConfigData()
                                                .getChallengeDataList().get(cpt).getIntermediaire()
                                )
                        ));
                        break;
                    case MAITRE:
                        chalM.setLore(List.of(
                                "",
                                ChatColor.GRAY + "Niveau Actuel :",
                                ChatColor.WHITE + "    Basique",
                                ChatColor.WHITE + "    Intermédiaire",
                                ChatColor.YELLOW + " >> " + ChatColor.WHITE + "Maître",
                                ChatColor.GRAY + "_________________",
                                ChatColor.YELLOW + " | " + ChatColor.GRAY + Challenge.getInstance()
                                        .getDataManager().getDataCache().get(player.getUniqueId())
                                        .getPlayerChallengeProgression().get(cpt).getActions()
                                        + ChatColor.WHITE + "/" + Challenge.getInstance().getConfigData()
                                        .getChallengeDataList().get(cpt).getMaitre(),
                                ChatColor.YELLOW + " | " + progressBar(
                                        Challenge.getInstance()
                                                .getDataManager().getDataCache().get(player.getUniqueId())
                                                .getPlayerChallengeProgression().get(cpt).getActions(),
                                        Challenge.getInstance().getConfigData()
                                                .getChallengeDataList().get(cpt).getMaitre()
                                )
                        ));
                        break;
                    default:
                        break;
                }
            } else {
                chalM.setLore(List.of(
                        "",
                        ChatColor.YELLOW + ">> " + ChatColor.GRAY + "Cliquez pour " + ChatColor.GREEN + "activer ",
                        ChatColor.GRAY + "   la quête."
                ));
            }
            challengeItem.setItemMeta(chalM);
            consumer.accept(challengeItem, servChalData.getNomChallenge());
            cpt++;
        }
    }

    private void onClick(Player player, String nomChallenge) {
        Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                .activateChallenge(nomChallenge);
    }
}
