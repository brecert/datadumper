package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.entity.effect.StatusEffect;

import java.lang.reflect.Type;

public class StatusEffectSerializer implements JsonSerializer<StatusEffect> {
    @Override
    public JsonElement serialize(StatusEffect src, Type type, JsonSerializationContext jsonSerializationContext) {
        final var json = new JsonObject();
        json.addProperty("translationKey", src.getTranslationKey());
        json.addProperty("category", src.getCategory().name().toLowerCase());
        json.addProperty("color", src.getColor());
        json.addProperty("isBeneficial", src.isBeneficial());
        json.addProperty("isInstantenous", src.isInstant());

        if (!src.getAttributeModifiers().isEmpty()) {
            json.add("attributes", jsonSerializationContext.serialize(src.getAttributeModifiers()));
        }

        return json;
    }
}
