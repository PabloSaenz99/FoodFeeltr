package psb.backend.foodfeeltr.models

import java.util.UUID

data class User(val id: UUID, var name: String, var lang: Languaje, var userLevel: UserLevel)