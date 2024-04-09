package jakubtutko.pettracking.pet.adapter.out.persistence

import jakubtutko.pettracking.pet.application.port.out.CountPetsOutOfZonePort
import jakubtutko.pettracking.pet.application.port.out.CreatePetPort
import jakubtutko.pettracking.pet.application.port.out.GetPetsPort
import jakubtutko.pettracking.pet.domain.Cat
import jakubtutko.pettracking.pet.domain.Dog
import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetCount
import jakubtutko.pettracking.pet.domain.PetFilter
import jakubtutko.pettracking.pet.domain.PetId
import jakubtutko.pettracking.pet.domain.PetPrototype
import jakubtutko.pettracking.pet.domain.PetType
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class InMemoryPetAdapter(
    private val storage: InMemoryPetStorage,
) : CreatePetPort, GetPetsPort, CountPetsOutOfZonePort {

    override fun create(petPrototype: PetPrototype): Pet {
        val pet = petPrototype.toPet()
        storage.addPet(pet)
        if (!pet.inZone) storage.increaseCount(petPrototype.type, petPrototype.trackerType)
        return pet
    }

    override fun getPets(petFilter: PetFilter): List<Pet> = storage.getPets(
        petType = petFilter.type
    )

    override fun countPetsOutOfZone(): PetCount = storage.getCounts()

    private fun PetPrototype.toPet(): Pet = when (type) {
        PetType.DOG -> toDog()
        PetType.CAT -> toCat()
    }

    private fun PetPrototype.toDog() = Dog(
        id = PetId(UUID.randomUUID()),
        trackerType = trackerType,
        ownerId = ownerId,
        inZone = inZone,
    )

    private fun PetPrototype.toCat() = Cat(
        id = PetId(UUID.randomUUID()),
        trackerType = trackerType,
        ownerId = ownerId,
        inZone = inZone,
        lostTracker = lostTracker ?: throw IllegalArgumentException("lost tracker is not set"),
    )
}
