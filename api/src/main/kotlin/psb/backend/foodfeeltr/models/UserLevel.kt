package psb.backend.foodfeeltr.models

enum class UserLevel {
	BASIC {
		override fun getInfo(): String = Place.getBasicInfo()
		override fun getOptions(): String {
			TODO("Not yet implemented")
		}
	},
	MEDIUM {
		override fun getInfo(): String = Place.getMediumInfo()
		override fun getOptions(): String {
			TODO("Not yet implemented")
		}
	},
	ADVANCED {
		override fun getInfo(): String = Place.getAdvancedInfo()
		override fun getOptions(): String {
			TODO("Not yet implemented")
		}
	};

	abstract fun getInfo(): String
	abstract fun getOptions(): String
}