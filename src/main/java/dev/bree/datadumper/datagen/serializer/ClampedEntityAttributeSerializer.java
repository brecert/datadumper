package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;

import java.lang.reflect.Type;

public class ClampedEntityAttributeSerializer implements JsonSerializer<ClampedEntityAttribute> {
    @Override
    public JsonElement serialize(ClampedEntityAttribute src, Type type, JsonSerializationContext jsonSerializationContext) {
        final var json = jsonSerializationContext.serialize(src, EntityAttribute.class).getAsJsonObject();
        json.addProperty("maxValue", src.getMaxValue());
        json.addProperty("minValue", src.getMaxValue());
        return json;
    }
}
