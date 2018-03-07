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

package net.thegaminghuskymc.sandboxgame.engine.modding;

public enum LoaderState {

    NO_INIT("Uninitialized"),
    LOADING("Loading"),
    CONSTRUCTING("Constructing mods"),
    PRE_INITIALIZATION("Pre-initializing mods"),
    INITIALIZATION("Initializing mods"),
    POST_INITIALIZATION("Post-initializing mods"),
    AVAILABLE("Mod loading complete"),
    SERVER_ABOUT_TO_START("Server about to start"),
    SERVER_STARTING("Server starting"),
    SERVER_STARTED("Server started"),
    SERVER_STOPPING("Server stopping"),
    SERVER_STOPPED("Server stopped"),
    ERROR("Mod Loading error");


    private String name;

    LoaderState(String name) {
        this.name = name;
    }

    public LoaderState transition(boolean errored) {
        if (errored) {
            return ERROR;
        }
        // stopping -> available
        if (this == SERVER_STOPPED) {
            return AVAILABLE;
        }
        return values()[ordinal() < values().length ? ordinal() + 1 : ordinal()];
    }

    public LoaderState requiredState() {
        if (this == NO_INIT) return NO_INIT;
        return LoaderState.values()[this.ordinal() - 1];
    }

    public String getPrettyName() {
        return name;
    }

    public enum ModState {
        UNLOADED("Unloaded", "U"),
        LOADED("Loaded", "L"),
        CONSTRUCTED("Constructed", "C"),
        PRE_INITIALIZED("Pre-initialized", "H"),
        INITIALIZED("Initialized", "I"),
        POST_INITIALIZED("Post-initialized", "J"),
        AVAILABLE("Available", "A"),
        DISABLED("Disabled", "D"),
        ERROR("Error", "E");

        private String label;
        private String marker;

        ModState(String label, String marker) {
            this.label = label;
            this.marker = marker;
        }

        @Override
        public String toString() {
            return this.label;
        }

        public String getMarker() {
            return this.marker;
        }
    }
}