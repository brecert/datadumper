package dev.bree.datadumper.datagen.provider;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.bree.datadumper.datagen.serializer.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class EnchantmentDataProvider implements DataProvider {
    protected final FabricDataGenerator dataGenerator;

    public EnchantmentDataProvider(FabricDataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public String getName() {
        return "Enchantment";
    }

    @Override
    public void run(DataWriter writer) throws IOException {
        final var enchantments = new HashMap<String, JsonObject>();

        var gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Identifier.class, new IdentifierSerializer());
        gsonBuilder.registerTypeHierarchyAdapter(Enchantment.class, new EnchantmentSerializer());
        var gson = gsonBuilder.create();


        for (var id : Registry.ENCHANTMENT.getIds()) {
            final var enchantment = Registry.ENCHANTMENT.get(id);
            final var namespace = enchantments.computeIfAbsent(id.getNamespace(), (_namespace) -> new JsonObject());
            namespace.add(id.toString(), gson.toJsonTree(enchantment));
        }

        for (final var entry : enchantments.entrySet()) {
            DataProvider.writeToPath(writer, entry.getValue(), getOutputPath(entry.getKey()));
        }
    }

    private Path getOutputPath(String namespace) {
        return dataGenerator.getOutput().resolve("data/%s/enchantments.json".formatted(namespace));
    }
}
