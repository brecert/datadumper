package dev.bree.datadumper.datagen.provider;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.bree.datadumper.datagen.serializer.ClampedEntityAttributeSerializer;
import dev.bree.datadumper.datagen.serializer.EntityAttributeModifierSerializer;
import dev.bree.datadumper.datagen.serializer.EntityAttributeSerializer;
import dev.bree.datadumper.datagen.serializer.IdentifierSerializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class AttributeDataProvider implements DataProvider {
    protected final FabricDataGenerator dataGenerator;

    public AttributeDataProvider(FabricDataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public String getName() {
        return "Attribute";
    }

    @Override
    public void run(DataWriter writer) throws IOException {
        final var attributes = new HashMap<String, JsonObject>();

        var gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(EntityAttribute.class, new EntityAttributeSerializer());
        gsonBuilder.registerTypeAdapter(ClampedEntityAttribute.class, new ClampedEntityAttributeSerializer());
        gsonBuilder.registerTypeAdapter(Identifier.class, new IdentifierSerializer());
        var gson = gsonBuilder.create();

        for (final var id : Registry.ATTRIBUTE.getIds()) {
            final var attribute = Registry.ATTRIBUTE.get(id);
            final var namespace = attributes.computeIfAbsent(id.getNamespace(), (_namespace) -> new JsonObject());
            namespace.add(id.toString(), gson.toJsonTree(attribute));
        }

        for (final var entry : attributes.entrySet()) {
            DataProvider.writeToPath(writer, entry.getValue(), getOutputPath(entry.getKey()));
        }
    }

    private Path getOutputPath(String namespace) {
        return dataGenerator.getOutput().resolve("data/%s/attributes.json".formatted(namespace));
    }
}
