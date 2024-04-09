package jakubtutko.pettracking.pet.adapter.out.persistence

import jakubtutko.pettracking.pet.domain.Cat
import jakubtutko.pettracking.pet.domain.Dog
import jakubtutko.pettracking.pet.domain.OwnerId
import jakubtutko.pettracking.pet.domain.PetFilter
import jakubtutko.pettracking.pet.domain.PetPrototype
import jakubtutko.pettracking.pet.domain.PetType
import jakubtutko.pettracking.pet.domain.PetType.CAT
import jakubtutko.pettracking.pet.domain.PetType.DOG
import jakubtutko.pettracking.pet.domain.TrackerType
import jakubtutko.pettracking.pet.domain.TrackerType.BIG
import jakubtutko.pettracking.pet.domain.TrackerType.MEDIUM
import jakubtutko.pettracking.pet.domain.TrackerType.SMALL
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InMemoryPetAdapterTest {

    private val storage: InMemoryPetStorage = InMemoryPetStorage()
    private val sut = InMemoryPetAdapter(storage)

    @BeforeEach
    fun setUp() {
        storage.clear()
    }

    @Test
    fun `dog is stored and returned`() {
        val dog = PetPrototype(
            type = DOG,
            trackerType = ANY_TRACKER_TYPE,
            ownerId = ANY_OWNER_ID,
            inZone = ANY_IN_ZONE,
            lostTracker = null,
        )

        val createdDog = sut.create(dog)

        assertSoftly { softly ->
            softly.assertThat(createdDog).isInstanceOf(Dog::class.java)
            softly.assertThat(createdDog.id.id).isNotNull()
            softly.assertThat(createdDog.type).isEqualTo(DOG)
            softly.assertThat(createdDog.trackerType).isEqualTo(ANY_TRACKER_TYPE)
            softly.assertThat(createdDog.ownerId).isEqualTo(ANY_OWNER_ID)
            softly.assertThat(createdDog.inZone).isEqualTo(ANY_IN_ZONE)
            softly.assertThat(createdDog.lostTracker).isNull()
            softly.assertThat(storage.getPets()).containsExactly(createdDog)
        }
    }

    @Test
    fun `cat is stored and returned`() {
        val dog = PetPrototype(
            type = CAT,
            trackerType = ANY_TRACKER_TYPE,
            ownerId = ANY_OWNER_ID,
            inZone = ANY_IN_ZONE,
            lostTracker = ANY_LOST_TRACKER,
        )

        val createdCat = sut.create(dog)

        assertSoftly { softly ->
            softly.assertThat(createdCat).isInstanceOf(Cat::class.java)
            softly.assertThat(createdCat.id.id).isNotNull()
            softly.assertThat(createdCat.type).isEqualTo(CAT)
            softly.assertThat(createdCat.trackerType).isEqualTo(ANY_TRACKER_TYPE)
            softly.assertThat(createdCat.ownerId).isEqualTo(ANY_OWNER_ID)
            softly.assertThat(createdCat.inZone).isEqualTo(ANY_IN_ZONE)
            softly.assertThat(createdCat.lostTracker).isEqualTo(ANY_LOST_TRACKER)
            softly.assertThat(storage.getPets()).containsExactly(createdCat)
        }
    }

    @Test
    fun `all pets are returned`() {
        sut.create(petPrototype(DOG, SMALL))
        sut.create(petPrototype(CAT, SMALL))

        val pets = sut.getPets(petFilter = PetFilter())

        assertThat(pets).extracting<PetType> { it.type }.containsOnly(DOG, CAT)
    }

    @Test
    fun `only dog pets are returned`() {
        sut.create(petPrototype(DOG, SMALL))
        sut.create(petPrototype(CAT, SMALL))

        val pets = sut.getPets(petFilter = PetFilter(DOG))

        assertThat(pets).extracting<PetType> { it.type }.containsOnly(DOG)
    }

    @Test
    fun `only cat pets are returned`() {
        sut.create(petPrototype(DOG, SMALL))
        sut.create(petPrototype(CAT, SMALL))

        val pets = sut.getPets(petFilter = PetFilter(CAT))

        assertThat(pets).extracting<PetType> { it.type }.containsOnly(CAT)
    }

    @Test
    fun `pets are counted`() {
        sut.create(petPrototype(DOG, SMALL))
        sut.create(petPrototype(DOG, MEDIUM))
        sut.create(petPrototype(DOG, BIG))
        sut.create(petPrototype(CAT, SMALL))
        sut.create(petPrototype(CAT, MEDIUM))

        val count = sut.countPets()

        assertSoftly { softly ->
            softly.assertThat(count.dogs[SMALL]).isEqualTo(1)
            softly.assertThat(count.dogs[MEDIUM]).isEqualTo(1)
            softly.assertThat(count.dogs[BIG]).isEqualTo(1)
            softly.assertThat(count.cats[SMALL]).isEqualTo(1)
            softly.assertThat(count.cats[MEDIUM]).isEqualTo(1)
        }
    }

    @Test
    fun `no pets returns zero counts`() {
        val count = sut.countPets()

        assertSoftly { softly ->
            softly.assertThat(count.dogs[SMALL]).isEqualTo(0)
            softly.assertThat(count.dogs[MEDIUM]).isEqualTo(0)
            softly.assertThat(count.dogs[BIG]).isEqualTo(0)
            softly.assertThat(count.cats[SMALL]).isEqualTo(0)
            softly.assertThat(count.cats[MEDIUM]).isEqualTo(0)
        }
    }

    private fun petPrototype(petType: PetType, trackerType: TrackerType) = PetPrototype(
        type = petType,
        trackerType = trackerType,
        ownerId = ANY_OWNER_ID,
        inZone = ANY_IN_ZONE,
        lostTracker = if (petType == DOG) null else ANY_LOST_TRACKER,
    )
}

private val ANY_OWNER_ID = OwnerId("ownerId")
private val ANY_TRACKER_TYPE = SMALL
private const val ANY_IN_ZONE = true
private const val ANY_LOST_TRACKER = false
