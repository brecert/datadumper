package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.entity.attribute.EntityAttributeModifier;

import java.lang.reflect.Type;

public class EntityAttributeModifierSerializer implements JsonSerializer<EntityAttributeModifier> {
    @Override
    public JsonElement serialize(EntityAttributeModifier src, Type type, JsonSerializationContext jsonSerializationContext) {
        var json = new JsonObject();
        json.addProperty("amount", src.getValue());
        json.addProperty("name", src.getName());
        json.addProperty("UUID", src.getId().toString());
        json.addProperty("operation", src.getOperation().toString().toLowerCase());
        return json;
    }
}
