package jp.kotmw.loginbonus;

import java.io.File;

import jp.kotmw.loginbonus.FileDatas.BonusItem;
import jp.kotmw.loginbonus.FileDatas.PlayerDatas;
import jp.kotmw.loginbonus.FileDatas.PluginFiles;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main main;
	public static String PPrefix = "[LoginBonusPlugin]";
	public String filepath = getDataFolder() + File.separator;

	@Override
	public void onEnable() {
		main = this;
		getServer().getPluginManager().registerEvents(new Events(), this);
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		this.reloadConfig();
		if(!PlayerDatas.playerdatadir.exists())
			PlayerDatas.playerdatadir.mkdir();
		if(!PluginFiles.ConfigFile("LoginBonus").exists())
			BonusItem.createBonusFile();
		else
			BonusItem.filecheck(true);
	}

	@Override
	public void onDisable() {}


        @Override
	public boolean onCommand(CommandSender s, Command cmd, String lav, String[] args) {
		if(args.length >= 1) {
			if((args.length == 1) && ("bonusfilecheck".equalsIgnoreCase(args[0]))) {
				BonusItem.filecheck(false);
				if(s instanceof Player) {
					((Player)s).sendMessage(PPrefix + "サーバーログの方にチェック結果を出しました");
				}
			}
			if(s instanceof Player) {
				Player p = (Player)s;
				if((args.length == 2) && ("getItem".equalsIgnoreCase(args[0]))) {
					p.getInventory().addItem(BonusItem.getBonusItem(Integer.valueOf(args[1])));
				}
			}
		}
		return false;
	}
}
