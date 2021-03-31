package marekh.test.rickmortyjsonapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class CharacterData(var name: String, var status: String, var species: String,
                    var gender: String, var origin: String, var location: String,
                    var image: String, var isFav: Boolean = false): Parcelable {

    override fun toString(): String {
        return """NAME: $name
                 |       
                 |STATUS: $status
                 |
                 |SPECIES: $species
                 |
                 |GENDER: $gender
                 |
                 |ORIGIN: $origin
                 |
                 |LOCATION:$location
        """.trimMargin()
    }

    override fun equals(other: Any?): Boolean {
        try {
            val a: CharacterData = other as CharacterData
            return this.name.equals(a.name) && this.image.equals(a.image)
        } catch (e: Exception) {
            return false
        }

    }
}