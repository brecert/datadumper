package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;

public class BlockSerializer implements JsonSerializer<Block> {
    @Override
    public JsonElement serialize(Block block, Type type, JsonSerializationContext jsonSerializationContext) {
        final var json = new JsonObject();
        final var id = Registry.BLOCK.getId(block);
        // todo: determine if I want to use bedrock naming, https://wiki.bedrock.dev/blocks/blocks-intro.html
        json.add("id", jsonSerializationContext.serialize(id));
        json.addProperty("translationKey", block.getTranslationKey());
        json.addProperty("blastResistance", block.getBlastResistance());
        json.addProperty("isSpawnProof", !block.canMobSpawnInside());
        json.addProperty("friction", block.getSlipperiness());
        json.addProperty("speedMultiplier", block.getVelocityMultiplier());
        json.addProperty("jumpMultiplier", block.getJumpVelocityMultiplier());
        json.addProperty("hasDynamicShape", block.hasDynamicBounds());
        json.add("defaultState", jsonSerializationContext.serialize(block.getDefaultState()));
        return json;
    }
}
