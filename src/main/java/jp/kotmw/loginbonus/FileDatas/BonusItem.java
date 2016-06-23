package jp.kotmw.loginbonus.FileDatas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BonusItem extends PluginFiles{

	public static String filename = "LoginBonus";

	public static void createBonusFile() {
		FileConfiguration file = new YamlConfiguration();
		List<String> lore = new ArrayList<>();
		List<String> enchant = new ArrayList<>();
		file.set("1.ItemType", Material.STONE.toString());
		file.set("1.ItemName", "LoginBonus");
		file.set("2.ItemType", Material.GOLD_NUGGET.toString());
		file.set("2.ItemName", "&6Nugget!");
		lore.add("Gold Nugget!");
		file.set("2.Lore", lore);
		file.set("3.ItemType", Material.DIAMOND_SWORD.toString());
		file.set("3.ItemName", "&bExcalibur");
		enchant.add(Enchantment.KNOCKBACK.getName()+","+3);
		enchant.add(Enchantment.FIRE_ASPECT.getName()+","+1);
		enchant.add(Enchantment.DAMAGE_ALL.getName()+","+5);
		file.set("3.Enchantment", enchant);
		SettingFiles(file, ConfigFile(filename));
	}

	public static int MaxBonusCount() {
		FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigFile(filename));
		int days = 1;
		for(days = 1; days <= 365; days++) {
			if(!file.contains(Integer.toString(days)))
				break;
		}
		return days-1;
	}

	public static ItemStack getBonusItem(int day) {
		int i = day;
		if(MaxBonusCount() < day)
			i = day - MaxBonusCount();
		FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigFile(filename));
		ItemStack item = new ItemStack(Material.getMaterial(file.getString(i+".ItemType")));
		ItemMeta meta = item.getItemMeta();
		if(file.contains(i+".ItemName"))
			meta.setDisplayName(ChatColor.RESET
					+ChatColor.translateAlternateColorCodes('&',file.getString(i+".ItemName")));
		if(file.contains(i+".Lore")) {
			List<String> l = new ArrayList<>();
			for(String lore : file.getStringList(i+".Lore"))
				l.add(ChatColor.translateAlternateColorCodes('&', lore));
			meta.setLore(l);
		}
		item.setItemMeta(meta);
		if(file.contains(i+".Enchantment"))
			for(String l : file.getStringList(i+".Enchantment")) {
				String ench[] = l.split(",");
				if(ench.length == 2)
					item.addUnsafeEnchantment(Enchantment.getByName(ench[0]), Integer.valueOf(ench[1]));
				else if(ench.length == 1)
					item.addUnsafeEnchantment(Enchantment.getByName(ench[0]), 1);
			}
		return item;
	}

	public static void filecheck(boolean replace) {
		FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigFile(filename));
		if(!replace)
			ConsoleSendmessage(ChatColor.AQUA + "チェック結果は以下の通りです");
		boolean error = false;
		for(int i = 1; i <= MaxBonusCount(); i++) {
			String itemtype = file.getString(i+".ItemType").toUpperCase();
			file.set(i+".ItemType", itemtype);
			if(!replace && !checkItem(itemtype)) {
				error = true;
				ConsoleSendmessage(i+"日目 : "+itemtype+" という名のアイテムタイプはありません");
			}
			if(file.contains(i+".Enchantment")) {
				List<String> l = new ArrayList<>();
				for(String enchlist : file.getStringList(i+".Enchantment")) {
					String ench = enchlist.split(",")[0].toUpperCase();
					if(enchlist.split(",").length == 2) {
						l.add(ench+","+enchlist.split(",")[1]);
					} else if(enchlist.split(",").length == 1)
						l.add(ench);
					if(!replace && !checkEnchantMent(ench)) {
						error = true;
						ConsoleSendmessage(i+"日目 : "+ench+" という名のエンチャントタイプはありません");
					}
				}
				file.set(i+".Enchantment", l);
			}
		}
		if(!replace)
			if(error)
				ConsoleSendmessage(ChatColor.AQUA + "以上がファイルチェックの結果です");
			else
				ConsoleSendmessage(ChatColor.AQUA + "問題はありませんでした");
		SettingFiles(file, ConfigFile(filename));
	}

	private static void ConsoleSendmessage(String text) {
		Bukkit.getConsoleSender().sendMessage(text);
	}

	private static boolean checkItem(String itemname) {
		for(Material m : Material.values()) {
			if(m.toString().equalsIgnoreCase(itemname))
				return true;
		}
		return false;
	}

	private static boolean checkEnchantMent(String enchname) {
		for(Enchantment e : Enchantment.values()) {
			if(e.getName().equalsIgnoreCase(enchname))
				return true;
		}
		return false;
	}
}
