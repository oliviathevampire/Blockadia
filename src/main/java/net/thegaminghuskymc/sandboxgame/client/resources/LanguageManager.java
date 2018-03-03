package net.thegaminghuskymc.sandboxgame.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.thegaminghuskymc.sandboxgame.client.resources.Locale;
import net.thegaminghuskymc.sandboxgame.client.resources.data.LanguageMetadataSection;
import net.thegaminghuskymc.sandboxgame.client.resources.data.MetadataSerializer;
import net.thegaminghuskymc.sandboxgame.util.text.translation.LanguageMap;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

@SideOnly(Side.CLIENT)
public class LanguageManager implements IResourceManagerReloadListener {
    protected static final Locale CURRENT_LOCALE = new Locale();
    private static final Logger LOGGER = LogManager.getLogger();
    private final MetadataSerializer metadataSerializer;
    private final Map<String, Language> languageMap = Maps.<String, Language>newHashMap();
    private String currentLanguage;

    public LanguageManager(MetadataSerializer theMetadataSerializerIn, String currentLanguageIn) {
        this.metadataSerializer = theMetadataSerializerIn;
        this.currentLanguage = currentLanguageIn;
        I18n.setLocale(CURRENT_LOCALE);
    }

    public void parseLanguageMetadata(List<IResourcePack> resourcesPacks) {
        this.languageMap.clear();

        for (IResourcePack iresourcepack : resourcesPacks) {
            try {
                LanguageMetadataSection languagemetadatasection = (LanguageMetadataSection) iresourcepack.getPackMetadata(this.metadataSerializer, "language");

                if (languagemetadatasection != null) {
                    for (Language language : languagemetadatasection.getLanguages()) {
                        if (!this.languageMap.containsKey(language.getLanguageCode())) {
                            this.languageMap.put(language.getLanguageCode(), language);
                        }
                    }
                }
            } catch (RuntimeException runtimeexception) {
                LOGGER.warn("Unable to parse language metadata section of resourcepack: {}", iresourcepack.getPackName(), runtimeexception);
            } catch (IOException ioexception) {
                LOGGER.warn("Unable to parse language metadata section of resourcepack: {}", iresourcepack.getPackName(), ioexception);
            }
        }
    }

    public void onResourceManagerReload(IResourceManager resourceManager) {
        List<String> list = Lists.newArrayList("en_us");

        if (!"en_us".equals(this.currentLanguage)) {
            list.add(this.currentLanguage);
        }

        CURRENT_LOCALE.loadLocaleDataFiles(resourceManager, list);
        LanguageMap.replaceWith(CURRENT_LOCALE.properties);
    }

    public boolean isCurrentLocaleUnicode() {
        return CURRENT_LOCALE.isUnicode();
    }

    public boolean isCurrentLanguageBidirectional() {
        return this.getCurrentLanguage() != null && this.getCurrentLanguage().isBidirectional();
    }

    public Language getCurrentLanguage() {
        String s = this.languageMap.containsKey(this.currentLanguage) ? this.currentLanguage : "en_us";
        return this.languageMap.get(s);
    }

    public void setCurrentLanguage(Language currentLanguageIn) {
        this.currentLanguage = currentLanguageIn.getLanguageCode();
    }

    public SortedSet<Language> getLanguages() {
        return Sets.newTreeSet(this.languageMap.values());
    }

    public Language getLanguage(String p_191960_1_) {
        return this.languageMap.get(p_191960_1_);
    }
}