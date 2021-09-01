package com.baloot_mahdijamshidi.classes

import androidx.room.TypeConverter
import com.baloot_mahdijamshidi.model.Source
import org.json.JSONObject

class Converters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromSource(value: Source): String {
            return "{\"id\": \""+value.id+"\",\"name\": \""+value.name+"\" }"

        }

        @TypeConverter
        @JvmStatic
        fun toSource(value: String): Source {

            val myObject = JSONObject(value)
            return Source(myObject.getString("name"), myObject.getString("id"))
        }
    }
}