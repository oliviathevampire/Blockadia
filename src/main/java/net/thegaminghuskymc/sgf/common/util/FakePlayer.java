/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.thegaminghuskymc.sgf.common.util;

import net.thegaminghuskymc.authlib.GameProfile;
import net.thegaminghuskymc.sandboxgame.entity.Entity;
import net.thegaminghuskymc.sandboxgame.entity.player.EntityPlayer;
import net.thegaminghuskymc.sandboxgame.entity.player.EntityPlayerMP;
import net.thegaminghuskymc.sandboxgame.server.management.PlayerInteractionManager;
import net.thegaminghuskymc.sandboxgame.stats.StatBase;
import net.thegaminghuskymc.sandboxgame.util.DamageSource;
import net.thegaminghuskymc.sandboxgame.util.math.Vec3d;
import net.thegaminghuskymc.sandboxgame.util.text.ITextComponent;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.world.WorldServer;
import net.thegaminghuskymc.sgf.fml.common.FMLCommonHandler;

//Preliminary, simple Fake Player class
public class FakePlayer extends EntityPlayerMP
{
    public FakePlayer(WorldServer world, GameProfile name)
    {
        super(FMLCommonHandler.instance().getMinecraftServerInstance(), world, name, new PlayerInteractionManager(world));
    }

    @Override public Vec3d getPositionVector(){ return new Vec3d(0, 0, 0); }
    @Override public boolean canUseCommand(int i, String s){ return false; }
    @Override public void sendStatusMessage(ITextComponent chatComponent, boolean actionBar){}
    @Override public void sendMessage(ITextComponent component) {}
    @Override public void addStat(StatBase par1StatBase, int par2){}
    @Override public void openGui(Object mod, int modGuiId, World world, int x, int y, int z){}
    @Override public boolean isEntityInvulnerable(DamageSource source){ return true; }
    @Override public boolean canAttackPlayer(EntityPlayer player){ return false; }
    @Override public void onDeath(DamageSource source){ return; }
    @Override public void onUpdate(){ return; }
    @Override public Entity changeDimension(int dim){ return this; }
    @Override public void handleClientSettings(CPacketClientSettings pkt){ return; }
}