package dev.bree.datadumper.datagen.provider;

import com.google.gson.*;
import dev.bree.datadumper.datagen.serializer.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class FoodDataProvider implements DataProvider {
    protected final FabricDataGenerator dataGenerator;

    public FoodDataProvider(FabricDataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public String getName() {
        return "Food";
    }

    @Override
    public void run(DataWriter writer) throws IOException {
        final var foods = Registry.ITEM.stream().filter(item -> item.isFood())
                .collect(Collectors.groupingBy(
                        item -> Registry.ITEM.getId(item).getNamespace(),
                        Collectors.toMap(
                                item -> Registry.ITEM.getId(item).toString(),
                                item -> item.getFoodComponent()
                        )
                ));

        final var gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EntityAttributeModifier.class, new EntityAttributeModifierSerializer());
        gsonBuilder.registerTypeAdapter(EntityAttribute.class, new EntityAttributeSerializer());
        gsonBuilder.registerTypeAdapter(StatusEffectInstance.class, new StatusEffectInstanceSerializer());
        gsonBuilder.registerTypeAdapter(FoodComponent.class, new FoodComponentSerializer());
        gsonBuilder.registerTypeAdapter(StatusEffect.class, new StatusEffectSerializer());

        final var gson = gsonBuilder.create();
        for (final var namespace : foods.keySet()) {
            DataProvider.writeToPath(writer, gson.toJsonTree(foods.get(namespace)), getOutputPath(namespace));
        }
    }

    private Path getOutputPath(String namespace) {
        return dataGenerator.getOutput().resolve("data/%s/food.json".formatted(namespace));
    }
}
