package mmdanggg2.doge;

import mmdanggg2.doge.blocks.DogeBlock;
import mmdanggg2.doge.items.DogeAxe;
import mmdanggg2.doge.items.DogeBoots;
import mmdanggg2.doge.items.DogeChestplate;
import mmdanggg2.doge.items.DogeHelmet;
import mmdanggg2.doge.items.DogeHoe;
import mmdanggg2.doge.items.DogeLauncher;
import mmdanggg2.doge.items.DogeLeggings;
import mmdanggg2.doge.items.DogePickaxe;
import mmdanggg2.doge.items.DogeShovel;
import mmdanggg2.doge.items.DogeSword;
import mmdanggg2.doge.items.Dogecoin;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=BasicInfo.ID, name=BasicInfo.NAME, version=BasicInfo.VER)
public class Doge {
	// The instance of your mod that Forge uses.
	@Instance(BasicInfo.NAME)
	public static Doge instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = BasicInfo.CLIENTPROXY + "ClientProxy", serverSide = BasicInfo.COMMONPROXY + "CommonProxy")
	public static CommonProxy proxy;

	//Inits
	public static int dogeArmourRenderID = proxy.addArmour("DogeArmour");
	
	public static ToolMaterial dogeToolMat;
	public static ArmorMaterial dogeArmorMat;
	
	// Doge Tools
	public static DogePickaxe dogePickaxe;
	public static DogeAxe dogeAxe;
	public static DogeShovel dogeShovel;
	public static DogeHoe dogeHoe;
	public static DogeSword dogeSword;

	// Doge Armour
	public static DogeHelmet dogeHelmet;
	public static DogeChestplate dogeChestplate;
	public static DogeLeggings dogeLeggings;
	public static DogeBoots dogeBoots;

	// Other
	public static DogeBlock dogeBlock;

	public static Dogecoin dogecoin;

	public static DogeLauncher dogeLauncher;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		// loading the configuration from its file
		config.load();
		
		int toolDurability = config.get("Doge_Tools", "ToolDurability", 780, "How many uses the tools have (Default 780)").getInt(780);
		float toolSpeed = (float) config.get("Doge_Tools", "ToolSpeed", 20.0F, "How fast the tools mine their respective blocks (Default 20.0)").getDouble(20.0F);
		float toolDamage = (float) config.get("Doge_Tools", "ToolDamage", 6.0F, "How much damage the tools do (Default 6.0)").getDouble(6.0F);
		
		// saving the configuration to its file
		config.save();
		
		dogeToolMat = EnumHelper.addToolMaterial("Doge", 3, toolDurability, toolSpeed, toolDamage, 30);
		dogeArmorMat = EnumHelper.addArmorMaterial("Doge", 30, new int[] { 5, 10, 8, 5 }, 30);
		
		DogeRegisterItems.register();
		
		DogeRegisterBlocks.register();
		
		DogeRegisterEntities.register();
		
		DogeRegisterRecipies.register();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		proxy.registerRenderers();
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
}