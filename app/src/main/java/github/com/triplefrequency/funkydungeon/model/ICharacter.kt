package github.com.triplefrequency.funkydungeon.model

import android.databinding.ObservableList
import android.databinding.ObservableMap

interface ICharacter {
    val authorUid: String?
    val id: String
    var name: String
    var defensePoints: Int
    var hitPoints: Int
    var initiative: Int
    var proficiency: Int
    var speed: Int
    var race: String
    var awareness: Int
    var level: Int
    var cClass: String
    var hitDice: String
    var xp: Int
    var attributes: ObservableMap<String, Int>
    var proficiencies: ObservableList<String>
    var attacks: ObservableList<CharacterWeapon>
    val attributeEntries: List<MutableMap.MutableEntry<String, Int>>
}