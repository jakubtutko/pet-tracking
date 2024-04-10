package jakubtutko.pettracking.pet.domain

import jakubtutko.pettracking.pet.domain.PetType.CAT
import jakubtutko.pettracking.pet.domain.PetType.DOG
import jakubtutko.pettracking.pet.domain.TrackerType.BIG
import jakubtutko.pettracking.pet.domain.TrackerType.SMALL
import java.util.UUID

sealed interface Pet {
    val id: PetId
    val type: PetType
    val trackerType: TrackerType
    val ownerId: OwnerId
    val inZone: Boolean
    val lostTracker: Boolean?
}

data class Dog(
    override val id: PetId,
    override val trackerType: TrackerType,
    override val ownerId: OwnerId,
    override val inZone: Boolean,
) : Pet {
    override val type: PetType = DOG
    override val lostTracker: Boolean? = null
}

data class Cat(
    override val id: PetId,
    override val trackerType: TrackerType,
    override val ownerId: OwnerId,
    override val inZone: Boolean,
    override val lostTracker: Boolean,
) : Pet {
    override val type: PetType = CAT
}

@JvmInline
value class PetId(val id: UUID)

enum class PetType {
    DOG,
    CAT,
}

enum class TrackerType {
    SMALL,
    MEDIUM,
    BIG,
}

data class PetPrototype(
    val type: PetType,
    val trackerType: TrackerType,
    val ownerId: OwnerId,
    val inZone: Boolean,
    val lostTracker: Boolean?,
) {
    fun validate() {
        when (type) {
            DOG -> validateDog()
            CAT -> validateCat()
        }
    }

    private fun validateDog() {
        require(lostTracker == null) { "dog does not support lostTracker property" }
    }

    private fun validateCat() {
        require(lostTracker != null) { "cat requires lostTracker property" }
        require(trackerType in listOf(SMALL, BIG)) { "cat trackerType $trackerType must be in SMALL and BIG" }
    }
}
