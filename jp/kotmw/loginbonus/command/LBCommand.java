package jp.kotmw.loginbonus.command;

import jp.kotmw.loginbonus.Main;
import jp.kotmw.loginbonus.FileDatas.BonusItem;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LBCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s,
			org.bukkit.command.Command arg1, String arg2, String[] args) {
		if(args.length >= 1) {
			if((args.length == 1) && ("bonusfilecheck".equalsIgnoreCase(args[0]))) {
				BonusItem.filecheck(false);
				if(s instanceof Player) {
					((Player)s).sendMessage(Main.PPrefix + "サーバーログの方にチェック結果を出しました");
				}
			}
			if(s instanceof Player) {
				Player p = (Player)s;
				if((args.length == 2) && ("getitem".equalsIgnoreCase(args[0]))) {
					p.getInventory().addItem(BonusItem.getBonusItem(Integer.valueOf(args[1])));
				} else if((args.length >= 1) && ("addBonusItem".equalsIgnoreCase(args[0]))) {
					if(p.getItemInHand() == null
							|| p.getItemInHand().getType() == Material.AIR) {
						p.sendMessage(Main.PPrefix + "登録するアイテム持ってくださいね～");
						return false;
					}
					if(args.length == 1) {
						BonusItem.addBonusItem(p.getItemInHand());
					} else if(args.length == 2) {
						BonusItem.addBonusItem(p.getItemInHand(), args[1]);
					}
				}
			}
		}
		return false;
	}

}
