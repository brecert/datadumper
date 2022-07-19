package dev.bree.datadumper.datagen.provider;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.bree.datadumper.datagen.serializer.IdentifierSerializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class RegistriesDataProvider implements DataProvider {
    protected final FabricDataGenerator dataGenerator;

    public RegistriesDataProvider(FabricDataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public String getName() {
        return "Registry";
    }

    @Override
    public void run(DataWriter writer) throws IOException {
        final var registries = new HashMap<String, JsonObject>();

        var gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Identifier.class, new IdentifierSerializer());
        var gson = gsonBuilder.create();


        for (var id : Registry.REGISTRIES.getIds()) {
            final var registry = Registry.REGISTRIES.get(id);
            final var namespace = registries.computeIfAbsent(id.getNamespace(), (_namespace) -> new JsonObject());
            namespace.add(id.toString(), gson.toJsonTree(registry.getIds()));
        }

        for (final var entry : registries.entrySet()) {
            DataProvider.writeToPath(writer, entry.getValue(), getOutputPath(entry.getKey()));
        }
    }

    private Path getOutputPath(String namespace) {
        return dataGenerator.getOutput().resolve("data/%s/registries.json".formatted(namespace));
    }
}
