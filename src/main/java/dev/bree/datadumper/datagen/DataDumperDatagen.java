package dev.bree.datadumper.datagen;

import dev.bree.datadumper.datagen.provider.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataDumperDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(AttributeDataProvider::new);
        fabricDataGenerator.addProvider(BlockDataProvider::new);
        fabricDataGenerator.addProvider(EnchantmentDataProvider::new);
        fabricDataGenerator.addProvider(EquipmentSlotProvider::new);
        fabricDataGenerator.addProvider(FoodDataProvider::new);
        fabricDataGenerator.addProvider(ItemDataProvider::new);
        fabricDataGenerator.addProvider(RegistriesDataProvider::new);
    }
}
