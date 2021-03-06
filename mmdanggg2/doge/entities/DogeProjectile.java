package mmdanggg2.doge.entities;

import mmdanggg2.doge.Doge;
import mmdanggg2.doge.DogeInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class DogeProjectile extends EntityThrowable
{
	
	public boolean dropCoin = true;
	public float damage;
	
	public DogeProjectile(World par1World)
	{
		super(par1World);
	}
	
	public DogeProjectile(World par1World, EntityLivingBase par2EntityLivingBase)
	{
		super(par1World, par2EntityLivingBase);
		this.motionX = this.motionX * 1.5;
		this.motionY = this.motionY * 1.5;
		this.motionZ = this.motionZ * 1.5;
		damage = DogeInfo.toolDamage * 2.0F;
	}
	
	public DogeProjectile(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0.04f;
	}
	
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if (par1MovingObjectPosition.entityHit != null)
		{
			Entity hitEntity = par1MovingObjectPosition.entityHit;
			hitEntity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), this.damage);
			if (hitEntity instanceof EntityLiving) {
				EntityLiving hitEntLiving = (EntityLiving) hitEntity;
				if (hitEntLiving.getHealth() == 0)
				{
					dropCoin = false;
				}
			}
		}
		
		//        for (int j = 0; j < 8; ++j)
		//        {
		//            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		//        }
		
		if (!this.worldObj.isRemote)
		{
			if (dropCoin)
			{
				EntityItem coin = new EntityItem(this.worldObj);
				coin.setEntityItemStack(new ItemStack(Doge.dogecoin, 1));
				coin.setPosition(this.posX, this.posY, this.posZ);
				this.worldObj.spawnEntityInWorld(coin);
			}
			setDead();
		}
	}
}