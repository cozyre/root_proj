package org.ukrida.root.data.remote

import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class BooleanIntAdapter : JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

    override fun serialize(
        src: Boolean?,
        typeOfSrc: Type?,
        context: com.google.gson.JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(if (src == true) 1 else 0)
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: com.google.gson.JsonDeserializationContext?
    ): Boolean {
        if (json == null || json.isJsonNull) return false

        val primitive = json.asJsonPrimitive

        return when {
            primitive.isBoolean -> primitive.asBoolean

            primitive.isNumber -> primitive.asInt == 1

            primitive.isString -> {
                val value = primitive.asString.lowercase()
                value == "1" || value == "true"
            }

            else -> false
        }
    }
}