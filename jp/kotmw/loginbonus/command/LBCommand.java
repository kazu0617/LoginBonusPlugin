package jp.kotmw.loginbonus.command;

import java.util.ArrayList;
import java.util.List;

import jp.kotmw.loginbonus.Main;
import jp.kotmw.loginbonus.FileDatas.BonusItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LBCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s,
			org.bukkit.command.Command arg1, String arg2, String[] args) {
		if(args.length >= 1) {
			if(s.hasPermission("loginbonus.command")) {
				s.sendMessage(Main.PPrefix + "権限を持っていません");
				return false;
			}
			if((args.length == 1) && ("bonusfilecheck".equalsIgnoreCase(args[0]))) {
				if(s.hasPermission("loginbonus.admin")) {
					s.sendMessage(Main.PPrefix + "権限を持っていません");
				}
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
					} else if (args.length == 2) {
						BonusItem.addBonusItem(p.getItemInHand(), args[1]);
					}
					return true;
				} else if((args.length >= 2) && ("setBonusItem".equalsIgnoreCase(args[0]))){
					if(p.getItemInHand() == null
							|| p.getItemInHand().getType() == Material.AIR) {
						p.sendMessage(Main.PPrefix + "登録するアイテム持ってくださいね～");
						return false;
					}
					int day = Integer.valueOf(args[1]);
					if(args.length == 2) {
						BonusItem.setBonusItem(p.getItemInHand(), day);
					} else if (args.length == 3) {
						BonusItem.setBonusItem(p.getItemInHand(), args[2], day);
					}
					return true;
				} else if((args.length == 2) && ("setLore".equalsIgnoreCase(args[0]))) {
					String lore[] = args[1].split(",");
					List<String> lores = new ArrayList<>();
					ItemStack is = p.getItemInHand();
					p.getInventory().remove(is);
					ItemMeta meta = is.getItemMeta();
					for(String l : lore)
						lores.add(ChatColor.translateAlternateColorCodes('&', l).replace("#",""));
					meta.setLore(lores);
					is.setItemMeta(meta);
					p.getInventory().setItemInHand(is);
				}
			}
		} else {
			if(s instanceof Player) {
				Player p = (Player)s;
				p.sendMessage(Main.PPrefix);
				p.sendMessage(ChatColor.GREEN + "コマンド一覧");
				p.sendMessage(ChatColor.YELLOW + "<>は必須，[]はあってもなくても良い");
				p.sendMessage("/loginbonus bonusfilecheck");
				p.sendMessage("/loginbonus getitem <day>");
				p.sendMessage("/loginbonus addBonusItem [name]");
				p.sendMessage("/loginbonus setBonusItem <day> [name]");
				p.sendMessage("/loginbonus setLore <lores>");
			} else {
				s.sendMessage(Main.PPrefix);
				s.sendMessage("コマンド一覧");
				s.sendMessage(ChatColor.YELLOW + "<>は必須，[]はあってもなくても良い");
				s.sendMessage("/loginbonus bonusfilecheck");
				s.sendMessage("/loginbonus getitem <day>");
				s.sendMessage("/loginbonus addBonusItem [name]");
				s.sendMessage("/loginbonus setBonusItem <day> [name]");
				s.sendMessage("/loginbonus setLore <lores>");
			}
		}
		return false;
	}

}
