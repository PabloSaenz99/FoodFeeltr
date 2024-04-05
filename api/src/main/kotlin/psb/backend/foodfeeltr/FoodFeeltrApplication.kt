package psb.backend.foodfeeltr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import psb.backend.foodfeeltr.controllers.PlaceController

@SpringBootApplication
class FoodFeeltrApplication

fun main(args: Array<String>) {
	val context = runApplication<FoodFeeltrApplication>(*args)
	val placeController = context.getBean(PlaceController::class.java)
//	placeController.getPlaceById("ChIJj61dQgK6j4AR4GeTYWZsKWw")
	placeController.getPlaceByArea()
}
