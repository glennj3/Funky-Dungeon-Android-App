package github.com.triplefrequency.funkydungeon.model

import android.databinding.ObservableList
import android.databinding.ObservableMap

data class DatabaseCharacter(
    override val authorUid: String?,
    override val id: String,
    override var name: String,
    override var defensePoints: Int,
    override var hitPoints: Int,
    override var initiative: Int,
    override var proficiency: Int,
    override var speed: Int,
    override var race: String,
    override var awareness: Int,
    override var level: Int,
    override var cClass: String,
    override var hitDice: String,
    override var xp: Int,
    override var attributes: ObservableMap<String, Int>,
    override var proficiencies: ObservableList<String>,
    override var attacks: ObservableList<CharacterWeapon>,
    override val attributeEntries: List<MutableMap.MutableEntry<String, Int>>
) : ICharacter