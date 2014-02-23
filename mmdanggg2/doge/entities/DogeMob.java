package mmdanggg2.doge.entities;

import mmdanggg2.doge.Doge;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class DogeMob extends EntityWolf
{
	public DogeMob(World par1World)
	{
		super(par1World);
		
	}
	
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.35D);
		
		if (this.isTamed())
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(200.0D);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(50.0D);
		}
	}
	
	@Override
	public void setTamed(boolean par1)
	{
		super.setTamed(par1);
		
		if (par1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(200.0D);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(50.0D);
		}
	}
	
	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	 @Override
	 public boolean interact(EntityPlayer par1EntityPlayer)
	 {
		 ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
		 
		 if (this.isTamed())
		 {
			 if (itemstack != null)
			 {
				 if (Item.itemsList[itemstack.itemID] instanceof ItemFood)
				 {
					 ItemFood itemfood = (ItemFood)Item.itemsList[itemstack.itemID];
					 
					 if (itemfood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F)
					 {
						 if (!par1EntityPlayer.capabilities.isCreativeMode)
						 {
							 --itemstack.stackSize;
						 }
						 
						 this.heal((float)itemfood.getHealAmount());
						 
						 if (itemstack.stackSize <= 0)
						 {
							 par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
						 }
						 
						 return true;
					 }
				 }
				 else if (itemstack.itemID == Item.dyePowder.itemID)
				 {
					 int i = BlockColored.getBlockFromDye(itemstack.getItemDamage());
					 
					 if (i != this.getCollarColor())
					 {
						 this.setCollarColor(i);
						 
						 if (!par1EntityPlayer.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
						 {
							 par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
						 }
						 
						 return true;
					 }
				 }
			 }
			 
			 if (par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack))
			 {
				 this.aiSit.setSitting(!this.isSitting());
				 this.isJumping = false;
				 this.setPathToEntity((PathEntity)null);
				 this.setTarget((Entity)null);
				 this.setAttackTarget((EntityLivingBase)null);
			 }
		 }
		 else if (itemstack != null && itemstack.itemID == Doge.dogecoin.itemID && !this.isAngry())
		 {
			 if (!par1EntityPlayer.capabilities.isCreativeMode)
			 {
				 --itemstack.stackSize;
			 }
			 
			 if (itemstack.stackSize <= 0)
			 {
				 par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
			 }
			 
			 if (!this.worldObj.isRemote)
			 {
				 this.setTamed(true);
				 this.setPathToEntity((PathEntity)null);
				 this.setAttackTarget((EntityLivingBase)null);
				 this.aiSit.setSitting(true);
				 this.setHealth(200.0F);
				 this.setOwner(par1EntityPlayer.getCommandSenderName());
				 this.playTameEffect(true);
				 this.worldObj.setEntityState(this, (byte)7);
			 }
			 
			 return true;
		 }
		 
		 return super.interact(par1EntityPlayer);
	 }
	 /**
	  * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
	  * the animal type)
	  */
	 public boolean isBreedingItem(ItemStack par1ItemStack)
	 {
		 return par1ItemStack == null ? false : (!(Item.itemsList[par1ItemStack.itemID] instanceof ItemFood)) ? false : par1ItemStack.itemID == Doge.dogecoin.itemID;
	 }
	 
	 /**
	  * Returns the item ID for the item the mob drops on death.
	  */
	 @Override
	 protected int getDropItemId()
	 {
		 return Doge.dogecoin.itemID;
	 }
	 
	 /**
	  * Will return how many at most can spawn in a chunk at once.
	  */
	 public int getMaxSpawnedInChunk()
	 {
		 return 32;
	 }
	 
	 /**
	  * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
	  */
	 public DogeMob spawnBabyAnimal(EntityAgeable par1EntityAgeable)
	 {
		 DogeMob entitywolf = new DogeMob(this.worldObj);
		 String s = this.getOwnerName();
		 
		 if (s != null && s.trim().length() > 0)
		 {
			 entitywolf.setOwner(s);
			 entitywolf.setTamed(true);
		 }
		 
		 return entitywolf;
	 }
	 
	 /**
	  * Returns true if the mob is currently able to mate with the specified mob.
	  */
	 public boolean canMateWith(EntityAnimal par1EntityAnimal)
	 {
		 if (par1EntityAnimal == this)
		 {
			 return false;
		 }
		 else if (!this.isTamed())
		 {
			 return false;
		 }
		 else if (!(par1EntityAnimal instanceof DogeMob))
		 {
			 return false;
		 }
		 else
		 {
			 DogeMob entitywolf = (DogeMob)par1EntityAnimal;
			 return !entitywolf.isTamed() ? false : (entitywolf.isSitting() ? false : this.isInLove() && entitywolf.isInLove());
		 }
	 }
}
