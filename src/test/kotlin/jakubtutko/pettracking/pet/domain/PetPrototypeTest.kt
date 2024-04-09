package jakubtutko.pettracking.pet.domain

import jakubtutko.pettracking.pet.domain.PetType.CAT
import jakubtutko.pettracking.pet.domain.PetType.DOG
import jakubtutko.pettracking.pet.domain.TrackerType.BIG
import jakubtutko.pettracking.pet.domain.TrackerType.SMALL
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE

class PetPrototypeTest {

    @ParameterizedTest
    @EnumSource(TrackerType::class)
    fun `dog validation does not throw exception for any tracker type`(trackerType: TrackerType) {
        val dog = PetPrototype(
            type = DOG,
            trackerType = trackerType,
            ownerId = ANY_OWNER_ID,
            inZone = ANY_IN_ZONE,
            lostTracker = null,
        )

        assertDoesNotThrow { dog.validate() }
    }

    @Test
    fun `dog validation throws exception when lost tracker is present`() {
        val dog = PetPrototype(
            type = DOG,
            trackerType = ANY_TRACKER_TYPE,
            ownerId = ANY_OWNER_ID,
            inZone = ANY_IN_ZONE,
            lostTracker = ANY_LOST_TRACKER,
        )

        assertThatThrownBy { dog.validate() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("dog does not support lostTracker property")
    }

    @ParameterizedTest
    @EnumSource(TrackerType::class, names = ["BIG"], mode = EXCLUDE)
    fun `cat validation does not throw exception for certain tracker types`(validTrackerType: TrackerType) {
        val cat = PetPrototype(
            type = CAT,
            trackerType = validTrackerType,
            ownerId = ANY_OWNER_ID,
            inZone = ANY_IN_ZONE,
            lostTracker = ANY_LOST_TRACKER,
        )

        assertDoesNotThrow { cat.validate() }
    }

    @Test
    fun `cat validation throws exception for BIG tracker type`() {
        val cat = PetPrototype(
            type = CAT,
            trackerType = BIG,
            ownerId = ANY_OWNER_ID,
            inZone = ANY_IN_ZONE,
            lostTracker = ANY_LOST_TRACKER,
        )

        assertThatThrownBy { cat.validate() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("trackerType BIG must be in SMALL and MEDIUM")
    }

    @Test
    fun `cat validation throws exception when lost tracker is not set`() {
        val cat = PetPrototype(
            type = CAT,
            trackerType = ANY_TRACKER_TYPE,
            ownerId = ANY_OWNER_ID,
            inZone = ANY_IN_ZONE,
            lostTracker = null,
        )

        assertThatThrownBy { cat.validate() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("cat requires lostTracker property")
    }
}

private val ANY_OWNER_ID = OwnerId("ownerId")
private val ANY_TRACKER_TYPE = SMALL
private const val ANY_IN_ZONE = true
private const val ANY_LOST_TRACKER = false
