package jakubtutko.pettracking.pet.domain

data class PetCount(
    val dogs: Map<TrackerType, List<Dog>>,
    val cats: Map<TrackerType, List<Cat>>,
)
