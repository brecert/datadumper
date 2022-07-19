package dev.bree.datadumper.datagen.provider;

import com.google.gson.*;
import dev.bree.datadumper.datagen.serializer.EntityAttributeModifierSerializer;
import dev.bree.datadumper.datagen.serializer.EntityAttributeSerializer;
import dev.bree.datadumper.datagen.serializer.ItemSerializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
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
        gsonBuilder.registerTypeHierarchyAdapter(Item.class, new ItemSerializer());
        gsonBuilder.registerTypeAdapter(EntityAttribute.class, new EntityAttributeSerializer());
        gsonBuilder.registerTypeAdapter(EntityAttributeModifier.class, new EntityAttributeModifierSerializer());
        var gson = gsonBuilder.create();

        for (final var id : Registry.ITEM.getIds()) {
            final var item = Registry.ITEM.get(id);
            final var namespace = items.computeIfAbsent(id.getNamespace(), (_namespace) -> new JsonObject());
            namespace.add(id.toString(), gson.toJsonTree(item));
        }

        for (final var entry : items.entrySet()) {
            DataProvider.writeToPath(writer, entry.getValue(), getOutputPath(entry.getKey()));
        }
    }

    private Path getOutputPath(String namespace) {
        return dataGenerator.getOutput().resolve("data/%s/items.json".formatted(namespace));
    }
}
