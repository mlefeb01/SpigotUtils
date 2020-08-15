package com.github.mlefeb01.spigotutils.api.adapters;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Type;

/**
 * Type Adapter to serialize/deserialize {@link Location}
 *
 * @author Matt Lefebvre
 */
public final class LocationAdapter implements JsonDeserializer<Location>, JsonSerializer<Location> {

    @Override
    public Location deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext ) throws JsonParseException {
        final String[] args = json.getAsString().split(":");
        return new Location(
                Bukkit.getWorld(args[0]),
                Double.parseDouble(args[1]),
                Double.parseDouble(args[2]),
                Double.parseDouble(args[3]),
                Float.parseFloat(args[4]),
                Float.parseFloat(args[5])
        );
    }

    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext ) {
        return new JsonPrimitive(String.format("%s:%f:%f:%f:%f:%f",
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        ));
    }

}
