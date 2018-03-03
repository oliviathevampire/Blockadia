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

package net.thegaminghuskymc.sgf.event.terraingen;

import com.grillecube.common.world.generator.WorldGenerator;
import net.thegaminghuskymc.sandboxgame.GameEngine;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.world.gen.IChunkGenerator;
import net.thegaminghuskymc.sandboxgame.world.gen.MapGenBase;
import net.thegaminghuskymc.sgf.fml.common.eventhandler.Event;

import java.util.Random;

public abstract class TerrainGen
{
    public static <T extends InitNoiseGensEvent.Context> T getModdedNoiseGenerators(World world, Random rand, T original)
    {
        InitNoiseGensEvent<T> event = new InitNoiseGensEvent<T>(world, rand, original);
        GameEngine.TERRAIN_GEN_BUS.post(event);
        return event.getNewValues();
    }

    public static MapGenBase getModdedMapGen(MapGenBase original, InitMapGenEvent.EventType type)
    {
        InitMapGenEvent event = new InitMapGenEvent(type, original);
        GameEngine.TERRAIN_GEN_BUS.post(event);
        return event.getNewGen();
    }

    public static boolean populate(IChunkGenerator chunkProvider, World world, Random rand, int chunkX, int chunkZ, boolean hasVillageGenerated, PopulateChunkEvent.Populate.EventType type)
    {
        PopulateChunkEvent.Populate event = new PopulateChunkEvent.Populate(chunkProvider, world, rand, chunkX, chunkZ, hasVillageGenerated, type);
        GameEngine.TERRAIN_GEN_BUS.post(event);
        return event.getResult() != Event.Result.DENY;
    }

    public static boolean decorate(World world, Random rand, BlockPos pos, DecorateBiomeEvent.Decorate.EventType type)
    {
        DecorateBiomeEvent.Decorate event = new DecorateBiomeEvent.Decorate(world, rand, pos, type);
        GameEngine.TERRAIN_GEN_BUS.post(event);
        return event.getResult() != Event.Result.DENY;
    }

    public static boolean generateOre(World world, Random rand, WorldGenerator generator, BlockPos pos, OreGenEvent.GenerateMinable.EventType type)
    {
        OreGenEvent.GenerateMinable event = new OreGenEvent.GenerateMinable(world, rand, generator, pos, type);
        GameEngine.ORE_GEN_BUS.post(event);
        return event.getResult() != Event.Result.DENY;
    }

    public static boolean saplingGrowTree(World world, Random rand, BlockPos pos)
    {
        SaplingGrowTreeEvent event = new SaplingGrowTreeEvent(world, rand, pos);
        GameEngine.TERRAIN_GEN_BUS.post(event);
        return event.getResult() != Event.Result.DENY;
    }
}