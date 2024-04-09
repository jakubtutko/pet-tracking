package jakubtutko.pettracking.application.port.`in`

import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetPrototype

interface CreatePetUseCase {

    fun createPet(petPrototype: PetPrototype): Pet
}
