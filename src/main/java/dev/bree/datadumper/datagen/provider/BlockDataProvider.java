package dev.bree.datadumper.datagen.provider;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.bree.datadumper.datagen.serializer.BlockSerializer;
import dev.bree.datadumper.datagen.serializer.IdentifierSerializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class BlockDataProvider implements DataProvider {
    protected final FabricDataGenerator dataGenerator;

    public BlockDataProvider(FabricDataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public String getName() {
        return "Attribute";
    }

    @Override
    public void run(DataWriter writer) throws IOException {
        final var attributes = new HashMap<String, JsonObject>();
        final var gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Identifier.class, new IdentifierSerializer());
        gsonBuilder.registerTypeHierarchyAdapter(Block.class, new BlockSerializer());
        final var gson = gsonBuilder.create();

        for (final var id : Registry.BLOCK.getIds()) {
            final var block = Registry.BLOCK.get(id);
            final var namespace = attributes.computeIfAbsent(id.getNamespace(), (_namespace) -> new JsonObject());
            namespace.add(id.toString(), gson.toJsonTree(block));
        }

        for (final var entry : attributes.entrySet()) {
            DataProvider.writeToPath(writer, entry.getValue(), getOutputPath(entry.getKey()));
        }
    }

    private Path getOutputPath(String namespace) {
        return dataGenerator.getOutput().resolve("data/%s/blocks.json".formatted(namespace));
    }
}
