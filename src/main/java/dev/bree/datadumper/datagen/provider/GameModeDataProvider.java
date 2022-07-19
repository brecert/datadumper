package dev.bree.datadumper.datagen.provider;

import com.google.gson.GsonBuilder;
import dev.bree.datadumper.datagen.serializer.EquipmentSlotSerializer;
import dev.bree.datadumper.datagen.serializer.GameModeSerializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.world.GameMode;

import java.io.IOException;
import java.nio.file.Path;

public class GameModeDataProvider implements DataProvider {
    protected final FabricDataGenerator dataGenerator;

    public GameModeDataProvider(FabricDataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public String getName() {
        return "GameMode";
    }

    @Override
    public void run(DataWriter writer) throws IOException {
        final var gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameMode.class, new GameModeSerializer());
        final var gson = gsonBuilder.create();

        DataProvider.writeToPath(writer, gson.toJsonTree(GameMode.values()), getOutputPath());
    }

    private Path getOutputPath() {
        return dataGenerator.getOutput().resolve("data/minecraft/gamemode.json");
    }
}
