package dev.bree.datadumper.datagen.provider;

import com.google.gson.*;
import dev.bree.datadumper.datagen.serializer.EntityAttributeModifierSerializer;
import dev.bree.datadumper.datagen.serializer.EntityAttributeSerializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class ItemDataProvider implements DataProvider {
    protected final FabricDataGenerator dataGenerator;

    public ItemDataProvider(FabricDataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public String getName() {
        return "Item";
    }

    @Override
    public void run(DataWriter writer) throws IOException {
        final var items = new HashMap<String, JsonObject>();

        var gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EntityAttributeModifier.class, new EntityAttributeModifierSerializer());
        gsonBuilder.registerTypeAdapter(EntityAttribute.class, new EntityAttributeSerializer());
        var gson = gsonBuilder.create();

        for (final var id : Registry.ITEM.getIds()) {
            final var json = new JsonObject();
            final var item = Registry.ITEM.get(id);

            json.addProperty("id", id.toString());

            json.addProperty("maxCount", item.getMaxCount());
            json.addProperty("maxDamage", item.getMaxDamage());
            json.addProperty("translationKey", item.getTranslationKey());

            json.addProperty("isEdible", item.isFood());
            json.addProperty("isDamageable", item.isDamageable());
            json.addProperty("isFireResistant", item.isFireproof());

            json.addProperty("enchantability", item.getEnchantability());

            var defaultAttributes = new JsonObject();
            for (final var slot : EquipmentSlot.values()) {
                final var attributeMap = item.getDefaultStack().getAttributeModifiers(slot);

                if (attributeMap.isEmpty()) continue;

                var attributesJSON = new JsonObject();
                for (final var attributeName : attributeMap.keys()) {
                    final var modifiers = attributeMap.get(attributeName);
                    final var attributeId = Registry.ATTRIBUTE.getKey(attributeName).get().getValue();
                    attributesJSON.add(attributeId.toString(), gson.toJsonTree(modifiers));
                }
                defaultAttributes.add(slot.getName(), attributesJSON);
            }
            json.add("attributes", defaultAttributes);

            ItemGroup group = item.getGroup();
            if (group != null) {
                json.addProperty("category", group.getName());
            }

            json.addProperty("rarity", item.getDefaultStack().getRarity().toString().toLowerCase());

            items.computeIfAbsent(id.getNamespace(), (namespace) -> new JsonObject()).add(id.toString(), json);
        }

        for (final var entry : items.entrySet()) {
            DataProvider.writeToPath(writer, entry.getValue(), getOutputPath(entry.getKey()));
        }
    }

    private Path getOutputPath(String namespace) {
        return dataGenerator.getOutput().resolve("data/%s/items.json".formatted(namespace));
    }
}
