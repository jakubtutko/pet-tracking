package jakubtutko.pettracking.application.service

import jakubtutko.pettracking.application.port.`in`.CreatePetUseCase
import jakubtutko.pettracking.application.port.out.CreatePetPort
import jakubtutko.pettracking.pet.domain.Pet
import jakubtutko.pettracking.pet.domain.PetPrototype
import org.springframework.stereotype.Service

@Service
class CreatePetService(
    private val createPetPort: CreatePetPort,
) : CreatePetUseCase {

    override fun createPet(petPrototype: PetPrototype): Pet {
        petPrototype.validate()
        return createPetPort.create(petPrototype)
    }
}
