package github.com.triplefrequency.funkydungeon.model

import github.com.triplefrequency.funkydungeon.repository.saveDelegate

class CharacterWeapon(
    character: Character,
    name: String,
    attribute: String
) {
    var name: String by character.saveDelegate(name)
    var attribute: String by character.saveDelegate(attribute)
    var damageDice: String by character.saveDelegate("1d8")
}