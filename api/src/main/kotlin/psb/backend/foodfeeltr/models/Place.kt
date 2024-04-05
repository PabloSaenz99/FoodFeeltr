package psb.backend.foodfeeltr.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder
import lombok.Builder

@Builder
data class Place @JsonCreator constructor(
	@JsonProperty("id") val id: String,
	@JsonProperty("displayName") val displayName: DisplayName?,
	@JsonProperty("websiteUri") val websiteUri: String?,
	@JsonProperty("rating") val rating: Float?, @JsonProperty("shortFormattedAddress") val shortFormattedAddress: String?,
	@JsonProperty("googleMapsUri") val googleMapsUri: String?, @JsonProperty("nationalPhoneNumber") val nationalPhoneNumber: String?,
	@JsonProperty("userRatingCount") val userRatingCount: Int?, @JsonProperty("accessibilityOptions") val accessibilityOptions: String?
) {

	companion object {
		fun getBasicInfo(): String {
			return "id,displayName,websiteUri,rating,shortFormattedAddress"
		}
		fun getMediumInfo(): String {
			// TODO: fix regularOpeningHours JSON parse
//			return "${getBasicInfo()},googleMapsUri,regularOpeningHours,nationalPhoneNumber"
			return "${getBasicInfo()},googleMapsUri,nationalPhoneNumber"
		}
		fun getAdvancedInfo(): String {
			// TODO: fix accessibilityOptions JSON parse
//			return "${getMediumInfo()},userRatingCount,accessibilityOptions"
			return "${getMediumInfo()},userRatingCount"
		}
	}

	@JsonPOJOBuilder
	data class DisplayName(@JsonProperty("text") val text: String, @JsonProperty("languageCode") val languageCode: String)
}