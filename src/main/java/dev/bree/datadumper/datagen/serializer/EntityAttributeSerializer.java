package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;

public class EntityAttributeSerializer implements JsonSerializer<EntityAttribute> {
    @Override
    public JsonElement serialize(EntityAttribute src, Type type, JsonSerializationContext jsonSerializationContext) {
        final var id = Registry.ATTRIBUTE.getKey(src).get().getValue();
        return new JsonPrimitive(id.toString());
    }
}
