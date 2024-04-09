package jakubtutko.pettracking.pet.adapter.out.persistence

import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetCount
import jakubtutko.pettracking.pet.domain.PetType
import jakubtutko.pettracking.pet.domain.PetType.CAT
import jakubtutko.pettracking.pet.domain.PetType.DOG
import jakubtutko.pettracking.pet.domain.TrackerType
import org.springframework.stereotype.Component

@Component
class InMemoryPetStorage {

    private var pets: MutableSet<Pet> = HashSet()
    private var counts: MutableMap<PetType, MutableMap<TrackerType, Int>> =
        PetType.entries.associateWith { _ ->
            TrackerType.entries.associateWith { _ -> 0 }.toMutableMap()
        }.toMutableMap()

    fun addPet(pet: Pet) {
        pets.add(pet)
    }

    fun increaseCount(petType: PetType, trackerType: TrackerType) {
        counts[petType]!![trackerType] = counts[petType]!![trackerType]!! + 1
    }

    fun getPets(petType: PetType? = null): List<Pet> = pets.filter { petType == null || it.type == petType }

    fun getCounts(): PetCount = PetCount(
        dogs = counts[DOG]!!,
        cats = counts[CAT]!!,
    )

    fun clear() {
        pets = HashSet()
        counts = PetType.entries.associateWith { _ ->
            TrackerType.entries.associateWith { _ -> 0 }.toMutableMap()
        }.toMutableMap()
    }
}
