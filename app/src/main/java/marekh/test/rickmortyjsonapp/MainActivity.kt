package marekh.test.rickmortyjsonapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), DownloadJSON.DataDownloaded, JSONDataProcesser.onDataProcesed,
                        RecyclerItemTouch.OnRecyclerClick{

    private val recyclerViewAdapter = RecyclerViewAdapter(ArrayList())
    private val mainCharacterList = ArrayList<CharacterData>()
    private val favouriteCharacterList = ArrayList<CharacterData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.addOnItemTouchListener(RecyclerItemTouch(this, recyclerView, this))

    }

    override fun onItemClick(view: View, position: Int) {
        try {
            val character = recyclerViewAdapter.getDataFromPosition(position)
            val intent = Intent(this, CharacterDetails()::class.java)
            intent.putExtra("charDet", character)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this,"data is loading, wait a moment", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemLongClick(view: View, position: Int) {
        try {
            val character = recyclerViewAdapter.getDataFromPosition(position)
            if(character.isFav) {
                character.isFav = false
                removeFromFav(character)
                Toast.makeText(this,"character removed from favourite", Toast.LENGTH_SHORT).show()
            } else {
                character.isFav = true
                if(!isFavourite(character)) {
                    addToFav(character)
                    Toast.makeText(this,"character added to favourite", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this,"data is loading, wait a moment", Toast.LENGTH_SHORT).show()
        }
    }

    fun isFavourite(character: CharacterData): Boolean {
        return favouriteCharacterList.contains(character)
    }

    fun addToFav(character: CharacterData) {
        favouriteCharacterList.add(character)
    }

    fun removeFromFav(character: CharacterData) {
        favouriteCharacterList.remove(character)
    }

    override fun onResume() {
        val url = "https://rickandmortyapi.com/api/character"
        downloadData(url)
        super.onResume()
    }

    fun downloadData(url: String) {
        val urlToDownload = Uri.parse(url)
        val downloadJSON = DownloadJSON(this)
        downloadJSON.execute(urlToDownload.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.setGroupCheckable(R.id.grp, true, true)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.menualive -> {
                if (!item.isChecked) {
                    item.setChecked(true)
                    DataChange("https://rickandmortyapi.com/api/character/?status=alive")
                }
            }
            R.id.menudead -> {
                if (!item.isChecked) {
                    item.setChecked(true)
                    DataChange("https://rickandmortyapi.com/api/character/?status=dead")
                }
            }
            R.id.menuunknown -> {
                    if (!item.isChecked) {
                        item.setChecked(true)
                        DataChange("https://rickandmortyapi.com/api/character/?status=unknown")
                    }
            }
            R.id.menuall -> {
                    if (!item.isChecked) {
                        item.setChecked(true)
                        DataChange("https://rickandmortyapi.com/api/character")
                    }
            }
            R.id.menufav -> {
                    if (!item.isChecked) {
                        item.setChecked(true)
                        recyclerViewAdapter.loadData(favouriteCharacterList)
                    }
            }
        }

        return true
    }

    fun DataChange(newurl: String) {
        mainCharacterList.clear()
        downloadData(newurl)
    }

    override fun datadownloaded(data: String) {
        if(data.isNotEmpty()) {
            Log.d(TAG, data)
            val dataprocesser = JSONDataProcesser(this)
            dataprocesser.execute(data)
        } else {
            Log.d(TAG, "Something went wrong/no Data to process")
        }
    }

    override fun onDataProcesed(characterlsit: ArrayList<CharacterData>, next: String) {
        mainCharacterList.addAll(characterlsit)
        for(char in mainCharacterList) {
            if(favouriteCharacterList.contains(char)) {
                char.isFav = true
            }
        }
        if(next != null && next.isNotEmpty()) {
            downloadData(next)
        }
        recyclerViewAdapter.loadData(mainCharacterList)
    }

}