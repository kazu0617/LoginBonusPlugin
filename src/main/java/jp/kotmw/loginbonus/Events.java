package jp.kotmw.loginbonus;

import jp.kotmw.loginbonus.FileDatas.BonusItem;
import jp.kotmw.loginbonus.FileDatas.PlayerDatas;
import jp.kotmw.loginbonus.FileDatas.PluginFiles;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class Events implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString();
		if(!PluginFiles.DirFile("Players", uuid).exists()) {
			PlayerDatas.createPlayerFile(uuid, p.getName());
			p.getInventory().addItem(BonusItem.getBonusItem(1));
			return;
		}
		if(PlayerDatas.isToday(uuid))
			return;
		PlayerDatas.updatePlayerFile(uuid);
		int i = PlayerDatas.getLoginCount(uuid);
		p.getInventory().addItem(BonusItem.getBonusItem(i));
	}

}
