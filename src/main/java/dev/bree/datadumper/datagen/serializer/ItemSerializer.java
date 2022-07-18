package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;

public class ItemSerializer implements JsonSerializer<Item> {
    @Override
    public JsonElement serialize(Item item, Type type, JsonSerializationContext jsonSerializationContext) {
        final var json = new JsonObject();
        final var id = Registry.ITEM.getId(item);

        json.addProperty("id", id.toString());

        json.addProperty("maxCount", item.getMaxCount());
        json.addProperty("maxDamage", item.getMaxDamage());
        json.addProperty("translationKey", item.getTranslationKey());

        json.addProperty("isEdible", item.isFood());
        json.addProperty("isFireResistant", item.isFireproof());

        json.addProperty("enchantability", item.getEnchantability());
        json.addProperty("rarity", item.getDefaultStack().getRarity().toString().toLowerCase());

        ItemGroup group = item.getGroup();
        if (group != null) {
            json.addProperty("category", group.getName());
        }

        var defaultAttributes = new JsonObject();
        var hasAttribute = false;
        for (final var slot : EquipmentSlot.values()) {
            final var attributeMap = item.getDefaultStack().getAttributeModifiers(slot);

            if (attributeMap.isEmpty()) continue;
            hasAttribute = true;

            var attributesJSON = new JsonObject();
            for (final var attributeName : attributeMap.keys()) {
                final var modifiers = attributeMap.get(attributeName);
                final var attributeId = Registry.ATTRIBUTE.getKey(attributeName).get().getValue();
                attributesJSON.add(attributeId.toString(), jsonSerializationContext.serialize(modifiers));
            }
            defaultAttributes.add(slot.getName(), attributesJSON);
        }
        if (hasAttribute) {
            json.add("attributes", defaultAttributes);
        }

        return json;
    }
}
