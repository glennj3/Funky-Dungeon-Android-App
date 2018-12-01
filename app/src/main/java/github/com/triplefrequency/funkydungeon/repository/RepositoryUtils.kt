package github.com.triplefrequency.funkydungeon.repository

import android.databinding.ObservableArrayList
import android.databinding.ObservableArrayMap
import android.databinding.ObservableList
import android.databinding.ObservableMap
import github.com.triplefrequency.funkydungeon.model.Character
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

fun <T> Character.saveDelegate(initialValue: T) = object : ObservableProperty<T>(initialValue) {
    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) =
        CharacterRepository.save(this@saveDelegate)
}

fun <K, V> Character.savableMap() = ObservableArrayMap<K, V>().apply {
    addOnMapChangedCallback(object : ObservableMap.OnMapChangedCallback<ObservableMap<K, V>, K, V>() {
        override fun onMapChanged(sender: ObservableMap<K, V>?, key: K?) =
            CharacterRepository.save(this@savableMap)
    })
}

fun <T> Character.savableList() = ObservableArrayList<T>().apply {
    addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
        override fun onChanged(sender: ObservableList<T>?) = CharacterRepository.save(this@savableList)
        override fun onItemRangeRemoved(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) =
            CharacterRepository.save(this@savableList)

        override fun onItemRangeMoved(
            sender: ObservableList<T>?,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) = CharacterRepository.save(this@savableList)

        override fun onItemRangeInserted(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) =
            CharacterRepository.save(this@savableList)

        override fun onItemRangeChanged(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) =
            CharacterRepository.save(this@savableList)
    })
}