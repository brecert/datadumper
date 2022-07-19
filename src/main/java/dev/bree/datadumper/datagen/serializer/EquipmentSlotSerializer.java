package dev.bree.datadumper.datagen.serializer;

import com.google.gson.*;
import net.minecraft.entity.EquipmentSlot;

import java.lang.reflect.Type;

public class EquipmentSlotSerializer implements JsonSerializer<EquipmentSlot> {
    @Override
    public JsonElement serialize(EquipmentSlot equipmentSlot, Type type, JsonSerializationContext jsonSerializationContext) {
        final var json = new JsonObject();
        json.addProperty("id", equipmentSlot.name());
        json.add("type", jsonSerializationContext.serialize(equipmentSlot.getType()));
        json.addProperty("name", equipmentSlot.getName());
        json.addProperty("entitySlotId", equipmentSlot.getEntitySlotId());
        json.addProperty("armorStandSlotId", equipmentSlot.getArmorStandSlotId());
        return json;
    }
}
