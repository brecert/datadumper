package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.lang.reflect.Type;

public class StatusEffectInstanceSerializer implements JsonSerializer<StatusEffectInstance> {
    @Override
    public JsonElement serialize(StatusEffectInstance src, Type type, JsonSerializationContext jsonSerializationContext) {
        final var json = new JsonObject();
        json.addProperty("duration", src.getDuration());
        json.addProperty("amplifier", src.getAmplifier());

        json.addProperty("shouldShowParticles", src.shouldShowParticles());
        json.addProperty("shouldShowIcon", src.shouldShowIcon());

        json.addProperty("isAmbient", src.isAmbient());
        json.addProperty("isPermanent", src.isPermanent());

        json.add("effect", jsonSerializationContext.serialize(src.getEffectType()));

        return json;
    }
}
