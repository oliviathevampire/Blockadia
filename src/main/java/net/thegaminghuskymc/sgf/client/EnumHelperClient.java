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

package net.thegaminghuskymc.sgf.client;

import net.thegaminghuskymc.sandboxgame.client.audio.MusicTicker;
import net.thegaminghuskymc.sandboxgame.client.settings.GameSettings;
import net.thegaminghuskymc.sandboxgame.util.SoundEvent;
import net.thegaminghuskymc.sandboxgame.world.GameType;
import net.thegaminghuskymc.sgf.common.util.EnumHelper;
import net.thegaminghuskymc.util.Util;

import javax.annotation.Nullable;

public class EnumHelperClient extends EnumHelper
{
    private static Class<?>[][] clientTypes =
    {
        {GameType.class, int.class, String.class, String.class},
        {GameSettings.Options.class, String.class, boolean.class, boolean.class},
        {Util.EnumOS.class},
        {MusicTicker.MusicType.class, SoundEvent.class, int.class, int.class}
    };

    @Nullable
    public static GameType addGameType(String name, int id, String displayName, String shortName)
    {
        return addEnum(GameType.class, name, id, displayName, shortName);
    }

    @Nullable
    public static GameSettings.Options addOptions(String name, String langName, boolean isSlider, boolean isToggle)
    {
        return addEnum(GameSettings.Options.class, name, langName, isSlider, isToggle);
    }

    @Nullable
    public static GameSettings.Options addOptions(String name, String langName, boolean isSlider, boolean isToggle, float valMin, float valMax, float valStep)
    {
        return addEnum(GameSettings.Options.class, name,
                new Class<?>[]{String.class, boolean.class, boolean.class, float.class, float.class, float.class},
                langName, isSlider, isToggle, valMin, valMax, valStep);
    }

    @Nullable
    public static Util.EnumOS addOS2(String name)
    {
        return addEnum(Util.EnumOS.class, name);
    }

    @Nullable
    public static MusicTicker.MusicType addMusicType(String name, SoundEvent sound, int minDelay, int maxDelay)
    {
        return addEnum(MusicTicker.MusicType.class, name, sound, minDelay, maxDelay);
    }

    @Nullable
    private static <T extends Enum<? >> T addEnum(Class<T> enumType, String enumName, Object... paramValues)
    {
        return addEnum(clientTypes, enumType, enumName, paramValues);
    }
}