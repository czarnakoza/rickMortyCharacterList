package marekh.test.rickmortyjsonapp

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject

private const val TAG = "DataProcesser"

class JSONDataProcesser(val listener: onDataProcesed): AsyncTask<String, Void, ArrayList<CharacterData>>() {
    interface onDataProcesed {
        fun onDataProcesed(characterlsit: ArrayList<CharacterData>, next: String)
    }

    var nextpage = ""

    override fun doInBackground(vararg params: String?): ArrayList<CharacterData> {

        val characterList = ArrayList<CharacterData>()
        try {
            val jsonObject = JSONObject(params[0])
            val info = jsonObject.getJSONObject("info")
            nextpage = info.getString("next")
            val charactersArr = jsonObject.getJSONArray("results")

            for (i in 0 until charactersArr.length()) {
                val character = charactersArr.getJSONObject(i)
                val name = character.getString("name")
                val status = character.getString("status")
                val species = character.getString("species")
                val gender = character.getString("gender")
                val originObj = character.getJSONObject("origin")
                val origin = originObj.getString("name")
                val locationobj = character.getJSONObject("location")
                val location = locationobj.getString("name")
                val image = character.getString("image")

                val characterObj = CharacterData(name, status, species, gender, origin,
                                                 location, image)

                Log.d(TAG, characterObj.toString())
                characterList.add(characterObj)
            }
        } catch (e: Exception) {
            cancel(true)
        }

        return characterList
    }

    override fun onPostExecute(result: ArrayList<CharacterData>?) {
        super.onPostExecute(result)
        listener.onDataProcesed(result!!, nextpage)
    }
}