package jakubtutko.pettracking.application.port.out

import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetPrototype

interface CreatePetPort {

    fun create(petPrototype: PetPrototype): Pet
}
