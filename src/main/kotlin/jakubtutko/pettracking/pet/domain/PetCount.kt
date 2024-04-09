package jakubtutko.pettracking.pet.domain

data class PetCount(
    val dogs: Map<TrackerType, Int>,
    val cats: Map<TrackerType, Int>,
)
