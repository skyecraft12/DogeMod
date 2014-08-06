package mmdanggg2.doge.items;

import mmdanggg2.doge.DogeInfo;
import mmdanggg2.doge.Doge;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;

public class DogeShovel extends ItemSpade {
	
	public DogeShovel(int id, EnumToolMaterial par2EnumToolMaterial) {
		super(id, par2EnumToolMaterial);
		
		setCreativeTab(Doge.dogeTab);
		setUnlocalizedName("dogeShovel");
		setTextureName(DogeInfo.NAME.toLowerCase() + ":dogeShovel");
		
	}
	
}
