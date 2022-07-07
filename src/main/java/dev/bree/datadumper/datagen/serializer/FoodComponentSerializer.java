package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.item.FoodComponent;

import java.lang.reflect.Type;

public class FoodComponentSerializer implements JsonSerializer<FoodComponent> {
    @Override
    public JsonElement serialize(FoodComponent foodComponent, Type type, JsonSerializationContext jsonSerializationContext) {
        final var json = new JsonObject();
        json.addProperty("nutrition", foodComponent.getHunger());
        json.addProperty("saturation", foodComponent.getSaturationModifier());
        json.addProperty("isMeat", foodComponent.isMeat());
        json.addProperty("isFast", foodComponent.isSnack());
        json.addProperty("isAlwaysEdible", foodComponent.isAlwaysEdible());

        final var statusEffects = foodComponent.getStatusEffects().stream().map(pair -> {
            final var effect = jsonSerializationContext.serialize(pair.getFirst()).getAsJsonObject();
            effect.addProperty("chance", pair.getSecond());
            return effect;
        }).toList();

        json.add("statusEffects", jsonSerializationContext.serialize(statusEffects));
        return json;
    }
}
