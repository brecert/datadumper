package dev.bree.datadumper.datagen.serializer;

import com.google.gson.*;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class IdentifierSerializer implements JsonSerializer<Identifier> {
    @Override
    public JsonElement serialize(Identifier src, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(src.toString());
    }
}
