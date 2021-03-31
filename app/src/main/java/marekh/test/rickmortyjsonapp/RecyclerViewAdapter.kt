package marekh.test.rickmortyjsonapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ViewHolderForAdapter(view: View) : RecyclerView.ViewHolder(view) {
    var Image: ImageView = view.findViewById(R.id.charImage)
    var charName = view.findViewById<TextView>(R.id.NameOfChar)
    var favStar = view.findViewById<ImageView>(R.id.favStar)
}

class RecyclerViewAdapter(private var charList: ArrayList<CharacterData>): RecyclerView.Adapter<ViewHolderForAdapter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderForAdapter {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.recycleritem, parent, false)
        return ViewHolderForAdapter(view)
    }

    fun loadData(newData: ArrayList<CharacterData>) {
        charList = newData
        notifyDataSetChanged()
    }

    fun getDataFromPosition(position: Int): CharacterData {
        return charList[position]
    }

    override fun onBindViewHolder(holder: ViewHolderForAdapter, position: Int) {
        val character = charList[position]
        Picasso.get().load(character.image)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(holder.Image)

        holder.charName.text = character.name
        if(character.isFav == true) {
            holder.favStar.setImageResource(R.drawable.favourite)
        } else {
            holder.favStar.setImageResource(R.drawable.not_favourite)
        }
    }





    override fun getItemCount(): Int {
        return charList.size
    }
}