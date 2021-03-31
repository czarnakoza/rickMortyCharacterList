package marekh.test.rickmortyjsonapp

import android.os.AsyncTask
import java.net.URL

class DownloadJSON(private val listener: DataDownloaded): AsyncTask<String, Void, String>() {

    interface DataDownloaded {
        fun datadownloaded(data: String)
    }

    override fun doInBackground(vararg params: String?): String {
        if(params[0] == null) return ""

        return try {
            URL(params[0]).readText()
        } catch (e: Exception) {
            ""
        }
    }

    override fun onPostExecute(result: String) {
        listener.datadownloaded(result)
    }
}