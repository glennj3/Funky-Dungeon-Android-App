package github.com.triplefrequency.funkydungeon.model

import android.databinding.ObservableList
import android.databinding.ObservableMap
import github.com.triplefrequency.funkydungeon.repository.savableList
import github.com.triplefrequency.funkydungeon.repository.savableMap
import github.com.triplefrequency.funkydungeon.repository.saveDelegate
import java.util.*

class Character {
    val id: String by saveDelegate(UUID.randomUUID().toString())
    var name: String by saveDelegate("")

    var defensePoints: Int by saveDelegate(10)
    var hitPoints: Int by saveDelegate(10)

    var race: String by saveDelegate("")
    var awareness: Int by saveDelegate(0)

    var level: Int by saveDelegate(1)
    var cClass: String by saveDelegate("")
    var hitDice: String by saveDelegate("1d8")

    var xp: Int by saveDelegate(0)

    var attributes: ObservableMap<String, Int> = savableMap()

    var proficiencies: ObservableList<String> = savableList()

    var attacks: ObservableList<CharacterWeapon> = savableList()

    val attributeEntries
        get() = attributes.entries.sortedBy { it.key }

    override fun toString() = name
}