package dev.bree.datadumper.datagen.serializer;

import com.google.gson.*;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;

public class EntityAttributeSerializer implements JsonSerializer<EntityAttribute> {
    @Override
    public JsonElement serialize(EntityAttribute src, Type type, JsonSerializationContext jsonSerializationContext) {
        final var json = new JsonObject();
        final var id = Registry.ATTRIBUTE.getKey(src).get().getValue();

        json.add("id", jsonSerializationContext.serialize(id));
        json.addProperty("defaultValue", src.getDefaultValue());
        json.addProperty("translationKey", src.getTranslationKey());

        return json;
    }
}
