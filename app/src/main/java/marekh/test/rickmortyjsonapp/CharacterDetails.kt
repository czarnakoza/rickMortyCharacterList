package marekh.test.rickmortyjsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class CharacterDetails() : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
        val character = intent.getParcelableExtra<CharacterData>("charDet")

        findViewById<TextView>(R.id.nameview).text = character.toString()
        val image = findViewById<ImageView>(R.id.imgview)

        Picasso.get().load(character!!.image)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(image)

    }
}