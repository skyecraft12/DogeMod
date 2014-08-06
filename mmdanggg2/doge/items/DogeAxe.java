package mmdanggg2.doge.items;

import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;

public class DogeAxe extends ItemAxe {
	
	public DogeAxe(int id, EnumToolMaterial par2EnumToolMaterial) {
		super(id, par2EnumToolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeAxe");
		setTextureName(DogeInfo.NAME.toLowerCase() + ":dogeAxe");
		
	}
	
}
