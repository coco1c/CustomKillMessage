package com.coco.mc.util;

import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext context) {
        Map<String, Object> serialized = new HashMap<>();

        return context.serialize(itemStack.serialize());
    }

    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!jsonElement.isJsonObject()) {
            throw new JsonParseException("Expected JsonObject but found " + jsonElement.getClass());
        }

        return null;
    }
}
