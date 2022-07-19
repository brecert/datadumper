package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.world.GameMode;

import java.lang.reflect.Type;
import java.util.HashMap;

public class GameModeSerializer implements JsonSerializer<GameMode> {
    @Override
    public JsonElement serialize(GameMode gameMode, Type type, JsonSerializationContext jsonSerializationContext) {
        final var json = new JsonObject();
        json.addProperty("id", gameMode.getId());
        json.addProperty("name", gameMode.getName());

        TranslatableTextContent longName = (TranslatableTextContent) gameMode.getTranslatableName().getContent();
        TranslatableTextContent shortName = (TranslatableTextContent) gameMode.getSimpleTranslatableName().getContent();

        final var translationKeys = new HashMap();
        translationKeys.put("longName", longName.key);
        translationKeys.put("shortName", shortName.key);

        json.add("translationKeys", jsonSerializationContext.serialize(translationKeys));
        // in future maybe add isBlockBreakingRestricted, and isSurvivalLike
        return json;
    }
}
