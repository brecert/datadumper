package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.entity.attribute.EntityAttribute;

import java.lang.reflect.Type;

public class EntityAttributeSerializer implements JsonSerializer<EntityAttribute> {
    @Override
    public JsonElement serialize(EntityAttribute src, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(src.getTranslationKey());
    }
}
