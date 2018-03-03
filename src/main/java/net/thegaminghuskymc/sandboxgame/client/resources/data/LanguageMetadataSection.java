package net.thegaminghuskymc.sandboxgame.client.resources.data;

import net.thegaminghuskymc.sandboxgame.client.resources.Language;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import java.util.Collection;

@SideOnly(Side.CLIENT)
public class LanguageMetadataSection implements IMetadataSection {
    private final Collection<Language> languages;

    public LanguageMetadataSection(Collection<Language> languagesIn) {
        this.languages = languagesIn;
    }

    public Collection<Language> getLanguages() {
        return this.languages;
    }
}