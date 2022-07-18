package dev.bree.datadumper.datagen;

import dev.bree.datadumper.datagen.provider.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataDumperDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(ItemDataProvider::new);
        fabricDataGenerator.addProvider(FoodDataProvider::new);
        fabricDataGenerator.addProvider(AttributeDataProvider::new);
        fabricDataGenerator.addProvider(RegistriesDataProvider::new);
        fabricDataGenerator.addProvider(EnchantmentDataProvider::new);
    }
}
