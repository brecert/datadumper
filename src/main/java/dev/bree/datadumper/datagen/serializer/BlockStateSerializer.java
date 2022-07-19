package dev.bree.datadumper.datagen.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.block.BlockState;

import java.lang.reflect.Type;

public class BlockStateSerializer implements JsonSerializer<BlockState> {
    @Override
    public JsonElement serialize(BlockState blockState, Type type, JsonSerializationContext jsonSerializationContext) {
        final var json = new JsonObject();
        json.add("material", jsonSerializationContext.serialize(blockState.getMaterial()));
        // todo: rename this
        json.addProperty("isCollisionLargerThanCube", blockState.exceedsCube());
        json.addProperty("lightEmission", blockState.getLuminance());
        json.addProperty("isAir", blockState.isAir());
        json.add("renderType", jsonSerializationContext.serialize(blockState.getRenderType()));
        json.addProperty("emitsRedstonePower", blockState.emitsRedstonePower());
        json.addProperty("emitsAnalogRedstonePower", blockState.emitsRedstonePower());
        json.add("pistonBehvaior", jsonSerializationContext.serialize(blockState.getPistonBehavior()));
        json.addProperty("isOpaque", blockState.isOpaque());
//        todo: JSON mappings -> output using Reflection
//        blockState.streamTags()
        json.addProperty("hasBlockEntity", blockState.hasBlockEntity());
//        json.add("getFluidState", jsonSerializationContext.serialize(blockState.getFluidState()));
        json.addProperty("randomlyTicks", blockState.hasRandomTicks());
        json.add("soundGroup", jsonSerializationContext.serialize(blockState.getSoundGroup()));
        json.addProperty("isToolRequired", blockState.isToolRequired());
//        json.add("offsetType", jsonSerializationContext.serialize(blockState.getOffsetType()));
        return json;
    }
}
