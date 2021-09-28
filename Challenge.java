package fr.omega2028.challenge;

import com.google.gson.Gson;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import fr.omega2028.challenge.commands.ChallengeCommand;
import fr.omega2028.challenge.enums.ActionEnum;
import fr.omega2028.challenge.enums.CategorieEnum;
import fr.omega2028.challenge.listeners.PlayerEvent;
import fr.omega2028.challenge.managers.DataManager;
import fr.omega2028.challenge.objects.ServerChallengeData;
import fr.omega2028.challenge.objects.ServerData;
import fr.omega2028.challenge.providers.ChallengesProvider;
import fr.omega2028.challenge.providers.QuestMenuProvider;
import fr.omega2028.challenge.utils.DiscUtil;
import fr.omega2028.challenge.utils.Persist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Challenge extends JavaPlugin {

    private static Challenge instance;
    //Utils
    private Persist persist;
    private DiscUtil discUtil;
    private Gson gson;
    //Gestion de données
    private File configPath;
    private File playerProgressionPath;
    private ServerData configData;
    private DataManager dataManager;
    //SmartInventory
    private InventoryManager inventoryManager;
    private SmartInventory challengeMenu;
    private SmartInventory challengesChooseGUI;


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        //utils
        persist = new Persist(getInstance());
        discUtil = new DiscUtil();
        gson = new Gson();

        //SmartInventory
        inventoryManager = new InventoryManager(getInstance());
        inventoryManager.init();
        createInventoryChallengeChooseCategoryGUI();
        createInventoryChallengeChooseGUI();

        //Gestion de données
        CreateDirectory();
        stub();
        //loadConfig(); //COMPORTE DES PROBLEMES A CORRIGER
        Bukkit.getLogger().info("Contenu de configData : ---------------------------------------------------");
        Bukkit.getLogger().info(configData.toString());
        dataManager = new DataManager();

        //Commandes et listeners
        this.getCommand("q").setExecutor(new ChallengeCommand());
        this.getCommand("challenges").setExecutor(new ChallengeCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(), getInstance());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void stub() { // MANQUE A TESTER SI LE TOUT FONCTIONNNEEEE
        loadConfig();
        configData.getChallengeDataList().add(new ServerChallengeData("Miner1", CategorieEnum.MINAGE, Material.DIAMOND_ORE,
                ActionEnum.BREAK, 10, 20, 30, Material.DIAMOND_ORE));
        configData.getChallengeDataList().add(new ServerChallengeData("Miner2", CategorieEnum.MINAGE, Material.DIAMOND_ORE,
                ActionEnum.BREAK, 10, 20, 30, Material.DIAMOND_ORE));
        configData.getChallengeDataList().add(new ServerChallengeData("Miner3", CategorieEnum.MINAGE, Material.DIAMOND_ORE,
                ActionEnum.BREAK, 10, 20, 30, Material.DIAMOND_ORE));
        configData.getChallengeDataList().add(new ServerChallengeData("Miner4", CategorieEnum.MINAGE, Material.DIAMOND_ORE,
                ActionEnum.BREAK, 10, 20, 30, Material.DIAMOND_ORE));
        configData.getChallengeDataList().add(new ServerChallengeData("Miner5", CategorieEnum.MINAGE, Material.DIAMOND_ORE,
                ActionEnum.BREAK, 10, 20, 30, Material.DIAMOND_ORE));
        //persist.save(configData, new File(configPath.getPath() + "\\config.json"));
    }

    private  void CreateDirectory() {
        getDataFolder().mkdirs();
        this.configPath = new File(getDataFolder().getPath() + "\\Config");
        configPath.mkdir();
        this.playerProgressionPath = new File(getDataFolder().getPath() + "\\PlayerData");
        playerProgressionPath.mkdir();
    }

    private void loadConfig() {
        File configPath = new File(getConfigPath().getPath() + "\\config.json");
        Bukkit.getLogger().info(getConfigPath().getPath() + "\\config.json");
        if (!configPath.exists()) {
            Bukkit.getLogger().info("Crée un nouveau config");
            persist.save(new ServerData(), configPath);
            configData = persist.load(ServerData.class, configPath);
        }
        else {
            Bukkit.getLogger().info("Le fichier existe, je le load");
            configData = persist.load(ServerData.class, configPath);
            Bukkit.getLogger().info("ConfigData au load : " + configData.toString());
        }
    }

    private void createInventoryChallengeChooseCategoryGUI() {
        challengeMenu = SmartInventory.builder()
                .manager(getInstance().getInventoryManager())
                .size(3, 9)
                .title(ChatColor.BOLD + "Challenges")
                .provider(new QuestMenuProvider())
                .build();
    }

    private void createInventoryChallengeChooseGUI() {
        challengesChooseGUI = SmartInventory.builder()
                .manager(getInstance().getInventoryManager())
                .size(5, 9)
                .title(ChatColor.BOLD + "Challenges")
                .provider(new ChallengesProvider())
                .build();
    }

    public SmartInventory getChallengesChooseGUI() {
        return challengesChooseGUI;
    }

    public void setConfigData(ServerData configData) {
        this.configData = configData;
    }

    public ServerData getConfigData() {
        return configData;
    }

    public File getConfigPath() {
        return configPath;
    }

    public File getPlayerProgressionPath() {
        return playerProgressionPath;
    }

    public Persist getPersist() {
        return persist;
    }

    public static Challenge getInstance() {
        return instance;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public SmartInventory getChallengeMenu() {
        return challengeMenu;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
