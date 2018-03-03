package net.thegaminghuskymc.sandboxgame.client.resources.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.thegaminghuskymc.sandboxgame.registries.IRegistry;
import net.thegaminghuskymc.sandboxgame.registries.RegistrySimple;
import net.thegaminghuskymc.sandboxgame.util.text.ITextComponent;
import net.thegaminghuskymc.sandboxgame.util.text.Style;
import net.thegaminghuskymc.sandboxgame.util.EnumTypeAdapterFactory;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MetadataSerializer {
    private final IRegistry<String, Registration<? extends IMetadataSection>> metadataSectionSerializerRegistry = new RegistrySimple<String, Registration<? extends IMetadataSection>>();
    private final GsonBuilder gsonBuilder = new GsonBuilder();
    /**
     * Cached Gson instance. Set to null when more sections are registered, and then re-created from the builder.
     */
    private Gson gson;

    public MetadataSerializer() {
        this.gsonBuilder.registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer());
        this.gsonBuilder.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
        this.gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
    }

    public <T extends IMetadataSection> void registerMetadataSectionType(IMetadataSectionSerializer<T> metadataSectionSerializer, Class<T> clazz) {
        this.metadataSectionSerializerRegistry.putObject(metadataSectionSerializer.getSectionName(), new MetadataSerializer.Registration(metadataSectionSerializer, clazz));
        this.gsonBuilder.registerTypeAdapter(clazz, metadataSectionSerializer);
        this.gson = null;
    }

    public <T extends IMetadataSection> T parseMetadataSection(String sectionName, JsonObject json) {
        if (sectionName == null) {
            throw new IllegalArgumentException("Metadata section name cannot be null");
        } else if (!json.has(sectionName)) {
            return (T) null;
        } else if (!json.get(sectionName).isJsonObject()) {
            throw new IllegalArgumentException("Invalid metadata for '" + sectionName + "' - expected object, found " + json.get(sectionName));
        } else {
            MetadataSerializer.Registration<?> registration = (MetadataSerializer.Registration) this.metadataSectionSerializerRegistry.getObject(sectionName);

            if (registration == null) {
                throw new IllegalArgumentException("Don't know how to handle metadata section '" + sectionName + "'");
            } else {
                return (T) (this.getGson().fromJson(json.getAsJsonObject(sectionName), registration.clazz));
            }
        }
    }

    /**
     * Returns a Gson instance with type adapters registered for metadata sections.
     */
    private Gson getGson() {
        if (this.gson == null) {
            this.gson = this.gsonBuilder.create();
        }

        return this.gson;
    }

    @SideOnly(Side.CLIENT)
    class Registration<T extends IMetadataSection> {
        /**
         * The IMetadataSectionSerializer associated with the class registered
         */
        final IMetadataSectionSerializer<T> section;
        /**
         * The class registered
         */
        final Class<T> clazz;

        private Registration(IMetadataSectionSerializer<T> metadataSectionSerializer, Class<T> clazzToRegister) {
            this.section = metadataSectionSerializer;
            this.clazz = clazzToRegister;
        }
    }
}