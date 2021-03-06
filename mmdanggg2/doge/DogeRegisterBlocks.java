package mmdanggg2.doge;

import mmdanggg2.doge.blocks.DogeBlock;
import mmdanggg2.doge.blocks.MiningRig;
import mmdanggg2.doge.blocks.tileentities.MiningRigTileEntity;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;

public class DogeRegisterBlocks {
	// Blocks
	public static void register() {
		Doge.dogeBlock = new DogeBlock(Material.ground);
		GameRegistry.registerBlock(Doge.dogeBlock, "dogeBlock");
		
		Doge.miningRig = new MiningRig(Material.iron);
		GameRegistry.registerBlock(Doge.miningRig, "miningRig");
		GameRegistry.registerTileEntity(MiningRigTileEntity.class, "miningRigTileEntity");
	}
}
