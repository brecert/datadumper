package dev.bree.datadumper.datagen;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Path;

public class ItemDataProvider implements DataProvider {
    protected final FabricDataGenerator dataGenerator;
    private final DataGenerator.PathResolver pathResolver;

    protected ItemDataProvider(FabricDataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
        this.pathResolver = dataGenerator.createPathResolver(DataGenerator.OutputType.DATA_PACK, "items");
    }

    @Override
    public String getName() {
        return "Item";
    }

    @Override
    public void run(DataWriter writer) throws IOException {
        for(Identifier id : Registry.ITEM.getIds()) {
            var json = new JsonObject();
            var item = Registry.ITEM.get(id);

            json.addProperty("id", id.toString());

                json.addProperty("maxCount", item.getMaxCount());
                json.addProperty("maxDamage", item.getMaxDamage());
                json.addProperty("translationKey", item.getTranslationKey());

                json.addProperty("isEdible", item.isFood());
                json.addProperty("isDamageable", item.isDamageable());
                json.addProperty("isEnchantable", item.getEnchantability() != 0);
                json.addProperty("isFireResistant", item.isFireproof());

                ItemGroup group = item.getGroup();
                if (group != null) {
                    json.addProperty("category", group.getName());
                }

                json.addProperty("rarity", item.getDefaultStack().getRarity().toString().toLowerCase());

            DataProvider.writeToPath(writer, json, getOutputPath(item));
        }
    }

    private Path getOutputPath(Item item) {
        var id = Registry.ITEM.getId(item);
        return dataGenerator.getOutput().resolve("data/%s/items/%s.json".formatted(id.getNamespace(), id.getPath()));
    }
}
