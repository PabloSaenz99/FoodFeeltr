package psb.backend.foodfeeltr.controllers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForObject
import psb.backend.foodfeeltr.*
import psb.backend.foodfeeltr.Properties
import psb.backend.foodfeeltr.models.Languaje
import psb.backend.foodfeeltr.models.Place
import psb.backend.foodfeeltr.models.User
import psb.backend.foodfeeltr.models.UserLevel
import java.util.*
import kotlin.collections.HashMap

//https://stackoverflow.com/questions/46177133/http-request-in-android-with-kotlin

@RestController
class PlaceController(@Autowired val properties: Properties) {

	val currentUserTest = User(UUID.randomUUID(), "User 1", Languaje.ENGLISH, UserLevel.BASIC)

	val restTemplate: RestTemplate = RestTemplate()
	private val headers = HttpHeaders()

	init {
		headers.contentType = MediaType.APPLICATION_JSON
		headers.set("X-Goog-Api-Key", properties.MAPS_API_KEY)
	}

	@GetMapping("$LATEST_API_VERSION/$PLACE_STRING/{id}")
	fun getPlaceById(@PathVariable("id") id: String, @RequestParam withPhotos: Boolean = false): Place {
		val res = restTemplate.getForObject<Place>(formatURL(id, currentUserTest.userLevel, withPhotos))
		println(res)
		return res
	}

	@PostMapping("$LATEST_API_VERSION/$PLACE_STRING/area")
	fun getPlaceByArea(): MutableList<Place> {
		val list = mutableListOf<Place>()
		val jackson = ObjectMapper()
		headers["X-Goog-FieldMask"] = "places." + currentUserTest.userLevel.getInfo().replace(",", ",places.")
		val res = jackson.readTree(restTemplate.postForObject<String>("$PLACE_URL:searchNearby",
			HttpEntity<String>(formatPostBody(listOf("restaurant"), Triple(37.7937, -122.3965, 500.0)), headers)))
		for (p in res["places"]) {
			list.add(jackson.treeToValue(p, Place::class.java))
		}
//		println(jackson.readValue<List<Place>>(res["places"].toString(), type).toString())
//		val r1 = ObjectMapper().readValue(res, HashMap::class.java)["places"]
//		println(ObjectMapper().readValue(r1.toString(), Array<Place>::class.java))
		println(list)
		headers.remove("X-Goog-FieldMask")
		return list
	}

	fun formatPostBody(types: List<String>, location: Triple<Double, Double, Double>): String {
		var res = "{"
		if (types.isNotEmpty()) {
			res += "\"includedTypes\":["
			types.forEach { res += "\"$it\"" }
			res += "],"
		}
		res += "\"maxResultCount\":${(currentUserTest.userLevel.ordinal + 1)*5},"
		res += "\"locationRestriction\": {\"circle\": {\"center\":{" +
				"\"latitude\":${location.first}," +
				"\"longitude\":${location.second}}," +
				"\"radius\":${location.third}}}"
		return "$res}"
	}

	private fun formatURL(placeId: String, userLevel: UserLevel, withPhotos: Boolean): String {
		val with = if (withPhotos)",photos" else ""
		return "$PLACE_URL/$placeId?fields=${userLevel.getInfo()}$with&key=${properties.MAPS_API_KEY}"
	}

	val stringTest = "{\"places\":[{\"id\":\"ChIJURDKN2eAhYARN0AMzUEaiKo\",\"websiteUri\":\"http://lamarsf.com/\"}," +
			"{\"id\":\"ChIJTel9dGCAhYARQGwrTfGZ07M\",\"websiteUri\":\"https://kokkari.com/\"}]}"
}