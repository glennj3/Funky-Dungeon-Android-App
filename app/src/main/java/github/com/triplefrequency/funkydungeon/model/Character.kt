package github.com.triplefrequency.funkydungeon.model

import android.databinding.ObservableList
import android.databinding.ObservableMap
import github.com.triplefrequency.funkydungeon.repository.savableList
import github.com.triplefrequency.funkydungeon.repository.savableMap
import github.com.triplefrequency.funkydungeon.repository.saveDelegate
import java.util.*

class Character(
    authorUid: String? = null,
    override val id: String = UUID.randomUUID().toString(),
    name: String = "",
    defensePoints: Int = 10,
    hitPoints: Int = 10,
    initiative: Int = 0,
    proficiency: Int = 0,
    speed: Int = 10,
    race: String = "",
    awareness: Int = 0,
    level: Int = 1,
    cClass: String = "",
    hitDice: String = "1d8",
    xp: Int = 0,
    attributes: List<Pair<String, Int>> = listOf(
        "Strength",
        "Constitution",
        "Dexterity",
        "Intelligence",
        "Wisdom",
        "Charisma"
    ).map { it to 0 },
    proficiencies: List<String>? = null,
    attacks: List<CharacterWeapon>? = null
) : ICharacter {
    override var authorUid: String? by saveDelegate(authorUid)

    override var name: String by saveDelegate(name)

    override var defensePoints: Int by saveDelegate(defensePoints)
    override var hitPoints: Int by saveDelegate(hitPoints)

    override var initiative: Int by saveDelegate(initiative)
    override var proficiency: Int by saveDelegate(proficiency)
    override var speed: Int by saveDelegate(speed)

    override var race: String by saveDelegate(race)
    override var awareness: Int by saveDelegate(awareness)

    override var level: Int by saveDelegate(level)
    override var cClass: String by saveDelegate(cClass)
    override var hitDice: String by saveDelegate(hitDice)

    override var xp: Int by saveDelegate(xp)

    override var attributes: ObservableList<Pair<String, Int>> = savableList(attributes)

    override var proficiencies: ObservableList<String> = savableList(proficiencies)

    override var attacks: ObservableList<CharacterWeapon> = savableList(attacks)

    override fun toString() = name

    fun update(char: DatabaseCharacter) {
        authorUid = char.authorUid
        name = char.name
        defensePoints = char.defensePoints
        hitPoints = char.hitPoints
        initiative = char.initiative
        proficiency = char.proficiency
        speed = char.speed
        race = char.race
        awareness = char.awareness
        level = char.level
        cClass = char.cClass
        hitDice = char.hitDice
        xp = char.xp
        synchronized(attributes) {
            for (i in 0 until attributes.size) {
                val attr = attributes[i]
                val oAttr = char.attributes[attr.first] ?: attr.second
                attributes[i] = attr.first to oAttr
            }
        }
        synchronized(proficiencies) {
            proficiencies.clear()
            proficiencies.addAll(char.proficiencies)
        }
        synchronized(attacks) {
            attacks.clear()
            attacks.addAll(char.attacks.map { CharacterWeapon.fromDb(this, it) })
        }
    }

    val isValid
        get() = name.isNotBlank() && defensePoints >= 0 && hitPoints >= 0 &&
                initiative >= 0 && proficiency >= 0 && speed >= 0 && race.isNotBlank() && awareness >= 0 && level >= 1 && cClass.isNotBlank() &&
                hitDice.toLowerCase().matches(Regex("^\\dd(4|6|8|10|12|20)$")) && xp >= 0 && attributes.size == 6 && attributes.all { it.second >= 0 } &&
                proficiencies.all { it.isNotBlank() } && attacks.all {
            it.name.isNotBlank() && it.damageDice.toLowerCase().matches(
                Regex("^\\dd(4|6|8|10|12|20)$")
            ) && it.attribute.isNotBlank()
        }

    companion object {
        fun fromDatabase(char: DatabaseCharacter?) = char?.run {
            val c = Character(
                authorUid,
                id,
                name,
                defensePoints,
                hitPoints,
                initiative,
                proficiency,
                speed,
                race,
                awareness,
                level,
                cClass,
                hitDice,
                xp,
                attributes.map { (atr, v) -> atr to v },
                proficiencies
            )
            c.attacks = c.savableList(attacks.map { CharacterWeapon.fromDb(c, it) })
            return@run c
        }
    }
}