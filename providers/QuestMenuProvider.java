package fr.omega2028.challenge.providers;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.omega2028.challenge.Challenge;
import fr.omega2028.challenge.enums.CategorieEnum;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;


public class QuestMenuProvider implements InventoryProvider {


    @Override
    public void init(Player player, InventoryContents contents) {
        makeContour(contents);
        contents.set(1, 2, ClickableItem.of(makeAgriculture(), inventoryClickEvent -> {
            if (inventoryClickEvent.isLeftClick()) {
                Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                        .setClickedItems(CategorieEnum.AGRICULTURE);
                Challenge.getInstance().getChallengesChooseGUI().open(player);
            }
        }));
        contents.set(1, 3, ClickableItem.of(makeMineur(), inventoryClickEvent -> {
            if (inventoryClickEvent.isLeftClick()) {
                Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                        .setClickedItems(CategorieEnum.MINAGE);
                Challenge.getInstance().getChallengesChooseGUI().open(player);
            }
        }));
        contents.set(1, 5, ClickableItem.of(makeHunter(), inventoryClickEvent -> {
            if (inventoryClickEvent.isLeftClick()) {
                Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                        .setClickedItems(CategorieEnum.MOB);
                Challenge.getInstance().getChallengesChooseGUI().open(player);
            }
        }));
        contents.set(1, 6, ClickableItem.of(makeGlobal(), inventoryClickEvent -> {
            if (inventoryClickEvent.isLeftClick()) {
                Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                        .setClickedItems(CategorieEnum.GLOBAL);
                Challenge.getInstance().getChallengesChooseGUI().open(player);
            }
        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private void makeContour(InventoryContents contents) {
        ItemStack gray = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int slot : Arrays.asList(0, 1, 9, 18, 19, 7, 8, 17, 25, 26)) {
            contents.set(slot / 9, slot % 9, ClickableItem.empty(black));
        }
        for (int slot : Arrays.asList(2, 3, 5, 6, 20, 21, 23, 24)) {
            contents.set(slot / 9, slot % 9, ClickableItem.empty(gray));
        }
    }

    private ItemStack makeAgriculture() {
        ItemStack wheat = new ItemStack(Material.WHEAT);
        ItemMeta itWheat = wheat.getItemMeta();
        itWheat.setDisplayName(ChatColor.YELLOW + "Agriculture");
        itWheat.setLore(List.of(
                " ",
                ChatColor.WHITE + "En cliquant vous accédez",
                ChatColor.WHITE + "aux quêtes " + ChatColor.YELLOW + "Agriculture" + ChatColor.WHITE + " !",
                " ",
                ChatColor.YELLOW + ">> " + ChatColor.GRAY + "Cliquez pour " + ChatColor.GREEN + "ouvrir")
        );
        wheat.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        wheat.setItemMeta(itWheat);
        return wheat;
    }

    private ItemStack makeMineur() {
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta itPickaxe = pickaxe.getItemMeta();
        itPickaxe.setDisplayName(ChatColor.BLUE + "Minage");
        itPickaxe.setLore(List.of(
                " ",
                ChatColor.WHITE + "En cliquant vous accédez",
                ChatColor.WHITE + "aux quêtes " + ChatColor.BLUE + "Minage" + ChatColor.WHITE + " !",
                " ",
                ChatColor.YELLOW + ">> " + ChatColor.GRAY + "Cliquez pour " + ChatColor.GREEN + "ouvrir")
        );
        itPickaxe.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        pickaxe.setItemMeta(itPickaxe);
        return pickaxe;
    }

    private ItemStack makeHunter() {
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta it = sword.getItemMeta();
        it.setDisplayName(ChatColor.GRAY + "Mob");
        it.setLore(List.of(
                " ",
                ChatColor.WHITE + "En cliquant vous accédez",
                ChatColor.WHITE + "aux quêtes " + ChatColor.GRAY + "Mob" + ChatColor.WHITE + " !",
                " ",
                ChatColor.YELLOW + ">> " + ChatColor.GRAY + "Cliquez pour " + ChatColor.GREEN + "ouvrir")
        );
        sword.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        sword.setItemMeta(it);
        return sword;
    }

    private ItemStack makeGlobal() {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta it = compass.getItemMeta();
        it.setDisplayName(ChatColor.RED + "Global");
        it.setLore(List.of(
                " ",
                ChatColor.WHITE + "En cliquant vous accédez",
                ChatColor.WHITE + "aux quêtes " + ChatColor.RED + "Global" + ChatColor.WHITE + " !",
                " ",
                ChatColor.YELLOW + ">> " + ChatColor.GRAY + "Cliquez pour " + ChatColor.GREEN + "ouvrir")
        );
        compass.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        compass.setItemMeta(it);
        return compass;
    }

}
