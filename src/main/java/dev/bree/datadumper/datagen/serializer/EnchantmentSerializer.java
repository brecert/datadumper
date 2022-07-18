package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;

public class EnchantmentSerializer implements JsonSerializer<Enchantment> {
    @Override
    public JsonElement serialize(Enchantment enchantment, Type type, JsonSerializationContext context) {
        final var json = new JsonObject();
        final var id = Registry.ENCHANTMENT.getId(enchantment);
        // todo: power calculations somehow
        json.add("id", context.serialize(id));
        json.add("rarity", context.serialize(enchantment.getRarity()));
        json.add("slots", context.serialize(enchantment.slotTypes));
        json.add("category", context.serialize(enchantment.type));
        json.addProperty("minLevel", enchantment.getMinLevel());
        json.addProperty("maxLevel", enchantment.getMaxLevel());
        json.addProperty("translationKey", enchantment.getTranslationKey());
        json.addProperty("isCursed", enchantment.isCursed());
        json.addProperty("isTreasure", enchantment.isTreasure());
        json.addProperty("isTradeable", enchantment.isAvailableForEnchantedBookOffer());
        json.addProperty("isDiscoverable", enchantment.isAvailableForRandomSelection());
        return json;
    }
}
