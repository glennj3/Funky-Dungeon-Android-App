package github.com.triplefrequency.funkydungeon.model

import android.databinding.ObservableList
import android.databinding.ObservableMap
import github.com.triplefrequency.funkydungeon.repository.savableList
import github.com.triplefrequency.funkydungeon.repository.savableMap
import github.com.triplefrequency.funkydungeon.repository.saveDelegate
import java.util.*

class Character(
    override val authorUid: String? = null,
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
    attributes: ObservableList<Pair<String, Int>>? = null,
    proficiencies: ObservableList<String>? = null,
    attacks: ObservableList<CharacterWeapon>? = null
) : ICharacter {
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

    override var attributes: ObservableList<Pair<String, Int>> = attributes ?: savableList()

    init {
        if (this.attributes.size != 6) {
            this.attributes.addAll(listOf("Strength", "Constitution", "Dexterity", "Intelligence", "Wisdom", "Charisma").map { it to 0 })
        }
    }

    override var proficiencies: ObservableList<String> = proficiencies ?: savableList()

    override var attacks: ObservableList<CharacterWeapon> = attacks ?: savableList()

    override fun toString() = name

    companion object {
        fun fromDatabase(char: DatabaseCharacter?) = char?.run { Character(authorUid, id, name, defensePoints, hitPoints, initiative, proficiency, speed, race, awareness, level, cClass, hitDice, xp, attributes, proficiencies, attacks) }
    }
}