package github.com.triplefrequency.funkydungeon.model

import java.util.*

data class DatabaseCharacter(
    val authorUid: String? = null,
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var defensePoints: Int = 10,
    var hitPoints: Int = 10,
    var initiative: Int = 0,
    var proficiency: Int = 0,
    var speed: Int = 10,
    var race: String = "",
    var awareness: Int = 0,
    var level: Int = 1,
    var cClass: String = "",
    var hitDice: String = "1d8",
    var xp: Int = 0,
    var attributes: List<Pair<String, Int>> = listOf(),
    var proficiencies: List<String> = listOf(),
    var attacks: List<CharacterWeapon> = listOf()
)