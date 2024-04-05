package psb.backend.foodfeeltr

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Properties {
	@Value("\${MAPS_API_KEY}")
	lateinit var MAPS_API_KEY: String
}