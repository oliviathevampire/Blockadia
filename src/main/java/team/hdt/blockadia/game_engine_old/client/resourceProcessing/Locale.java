//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.client.resourceProcessing;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.hdt.blockadia.game_engine_old.common.util.JsonUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class Locale {
    private static final Gson GSON = new Gson();
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Pattern PATTERN = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    Map<String, String> properties = Maps.newHashMap();

    public Locale() {
    }

    private void loadLocaleData(InputStream p_loadLocaleData_1_) {
        JsonElement lvt_2_1_ = GSON.fromJson(new InputStreamReader(p_loadLocaleData_1_, StandardCharsets.UTF_8), JsonElement.class);
        JsonObject lvt_3_1_ = JsonUtils.getJsonObject(lvt_2_1_, "strings");
        Iterator var4 = lvt_3_1_.entrySet().iterator();

        while(var4.hasNext()) {
            Entry<String, JsonElement> lvt_5_1_ = (Entry)var4.next();
            String lvt_6_1_ = PATTERN.matcher(JsonUtils.getString(lvt_5_1_.getValue(), lvt_5_1_.getKey())).replaceAll("%$1s");
            this.properties.put(lvt_5_1_.getKey(), lvt_6_1_);
        }

    }

    private String translateKeyPrivate(String p_translateKeyPrivate_1_) {
        String lvt_2_1_ = this.properties.get(p_translateKeyPrivate_1_);
        return lvt_2_1_ == null ? p_translateKeyPrivate_1_ : lvt_2_1_;
    }

    public String formatMessage(String p_formatMessage_1_, Object[] p_formatMessage_2_) {
        String lvt_3_1_ = this.translateKeyPrivate(p_formatMessage_1_);

        try {
            return String.format(lvt_3_1_, p_formatMessage_2_);
        } catch (IllegalFormatException var5) {
            return "Format error: " + lvt_3_1_;
        }
    }

    public boolean hasKey(String p_hasKey_1_) {
        return this.properties.containsKey(p_hasKey_1_);
    }
}
