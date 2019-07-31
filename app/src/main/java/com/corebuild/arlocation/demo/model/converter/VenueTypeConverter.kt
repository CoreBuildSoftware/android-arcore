package com.corebuild.arlocation.demo.model.converter

import com.corebuild.arlocation.demo.model.Venue
import com.corebuild.arlocation.demo.model.VenueWrapper
import com.google.gson.*
import java.lang.reflect.Type

class VenueTypeConverter : JsonDeserializer<VenueWrapper> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): VenueWrapper {
        val responseObject = json.asJsonObject

        if (validateResponse(responseObject)) {
            return VenueWrapper(getVenues(responseObject!!))
        }
        return (VenueWrapper(listOf()))
    }

    private fun validateResponse(json: JsonObject?): Boolean {
        return json != null && json.asJsonObject.getAsJsonObject("meta").getAsJsonPrimitive("code").asString == "200"
    }

    @Throws(JsonParseException::class)
    private fun getVenues(responseObject: JsonObject): List<Venue> {
        val venues = responseObject.getAsJsonObject("response").getAsJsonArray("venues")
        return venues.map<JsonElement, Venue> { getVenue(it.asJsonObject) }
    }

    @Throws(AssertionError::class)
    private fun getVenue(rawVenue: JsonObject): Venue {

        val name = rawVenue.getAsJsonPrimitive("name").asString
        val location = rawVenue.getAsJsonObject("location")

        val address = location.getAsJsonPrimitive("address")?.asString ?: "not set"
        val lat = location.getAsJsonPrimitive("lat").asString
        val long = location.getAsJsonPrimitive("lng").asString

        val categories = rawVenue.getAsJsonArray("categories").get(0).asJsonObject

        val iconURL = categories.getAsJsonObject("icon").getAsJsonPrimitive("prefix").asString + "64.png"

        return Venue(name, address, lat, long, iconURL)
    }
}