package fr.omega2028.challenge.listeners;

import fr.omega2028.challenge.Challenge;
import fr.omega2028.challenge.enums.ActionEnum;
import fr.omega2028.challenge.objects.PlayerProgressionData;
import fr.omega2028.challenge.objects.PlayerData;
import fr.omega2028.challenge.utils.ListUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;

public class PlayerEvent implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(final PlayerJoinEvent playerJoinEvent) {
       Challenge.getInstance().getDataManager().loadPersonnalData(playerJoinEvent.getPlayer());
    }

    @EventHandler
    public void onPlayerLeaveEvent(final PlayerQuitEvent playerQuitEvent) {
        Challenge.getInstance().getDataManager().savePersonnalData(playerQuitEvent.getPlayer());
    }

    @EventHandler
    public void onPlayerBreakBlockEvent(final BlockBreakEvent blockBreakEvent) {
        Player player = blockBreakEvent.getPlayer();

        if (Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                .getChallengeActif() == null) return;

        Challenge.getInstance().getDataManager().getDataCache().get(blockBreakEvent.getPlayer().getUniqueId())
                .getChallengeActif()
                .makeAction(
                    Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                        .getChallengeActif().getNomChallenge(),
                    player,
                    ActionEnum.BREAK,
                    blockBreakEvent.getBlock().getType()
                );
    }

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent blockPlaceEvent) {
        Player player = blockPlaceEvent.getPlayer();

        if (Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                .getChallengeActif() == null) return;

        Challenge.getInstance().getDataManager().getDataCache().get(blockPlaceEvent.getPlayer().getUniqueId())
                .getChallengeActif()
                .makeAction(
                    Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                        .getChallengeActif().getNomChallenge(),
                    player,
                    ActionEnum.PLACE_BLOCK,
                    blockPlaceEvent.getBlock().getType()
                );
    }

    @EventHandler
    public void onKill(final EntityDeathEvent entityDeathEvent) {
        if (!(entityDeathEvent.getEntity().getKiller() instanceof Player)) return;
        Player player = entityDeathEvent.getEntity().getKiller();

        if (Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                .getChallengeActif() == null) return;
        Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                .getChallengeActif()
                .makeAction(
                    Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                        .getChallengeActif().getNomChallenge(),
                        player,
                    ActionEnum.KILL,
                    entityDeathEvent.getEntity().getType()
                );
    }

    @EventHandler
    public void onWalk(final PlayerMoveEvent playerMoveEvent) {
        if(playerMoveEvent.getFrom().distance(playerMoveEvent.getTo()) < 1) return;
        Player player = playerMoveEvent.getPlayer();

        if (Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                .getChallengeActif() == null) return;

        Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                .getChallengeActif()
                .makeAction(
                Challenge.getInstance().getDataManager().getDataCache().get(player.getUniqueId())
                        .getChallengeActif().getNomChallenge(),
                player,
                ActionEnum.WALK);
    }
}
