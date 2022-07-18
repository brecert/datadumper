package dev.bree.datadumper.datagen;

import dev.bree.datadumper.datagen.provider.AttributeDataProvider;
import dev.bree.datadumper.datagen.provider.FoodDataProvider;
import dev.bree.datadumper.datagen.provider.ItemDataProvider;
import dev.bree.datadumper.datagen.provider.RegistriesDataProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataDumperDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(ItemDataProvider::new);
        fabricDataGenerator.addProvider(FoodDataProvider::new);
        fabricDataGenerator.addProvider(AttributeDataProvider::new);
        fabricDataGenerator.addProvider(RegistriesDataProvider::new);
    }
}
