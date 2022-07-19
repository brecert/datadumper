package dev.bree.datadumper.datagen.provider;

import com.google.gson.GsonBuilder;
import dev.bree.datadumper.datagen.serializer.EquipmentSlotSerializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.entity.EquipmentSlot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class EquipmentSlotProvider implements DataProvider {
    protected final FabricDataGenerator dataGenerator;

    public EquipmentSlotProvider(FabricDataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public String getName() {
        return "EquipmentSlot";
    }

    @Override
    public void run(DataWriter writer) throws IOException {
        final var gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EquipmentSlot.class, new EquipmentSlotSerializer());
        final var gson = gsonBuilder.create();

        DataProvider.writeToPath(writer, gson.toJsonTree(EquipmentSlot.values()), getOutputPath());
    }

    private Path getOutputPath() {
        return dataGenerator.getOutput().resolve("data/minecraft/equipment_slot.json");
    }
}
