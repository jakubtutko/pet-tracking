package jakubtutko.pettracking.pet.application.port.`in`

import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetPrototype

interface CreatePetUseCase {

    fun create(petPrototype: PetPrototype): Pet
}
