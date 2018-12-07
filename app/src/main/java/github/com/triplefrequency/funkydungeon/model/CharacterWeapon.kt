package github.com.triplefrequency.funkydungeon.model

import github.com.triplefrequency.funkydungeon.repository.saveDelegate

class CharacterWeapon(
    character: Character,
    name: String = "",
    attribute: String = "",
    dice: String = "1d8"
) {
    var name: String by character.saveDelegate(name)
    var attribute: String by character.saveDelegate(attribute)
    var damageDice: String by character.saveDelegate(dice)

    companion object {
        fun fromDb(c: Character, d: DatabaseCharacterWeapon) = CharacterWeapon(c, d.name, d.attribute, d.dice)
    }
}