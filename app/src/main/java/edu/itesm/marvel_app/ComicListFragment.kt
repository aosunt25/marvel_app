package edu.itesm.marvel_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_comic_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import org.json.JSONException
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Instant
import java.time.format.DateTimeFormatter


/**
 * A fragment representing a list of Items.
 */
class ComicListFragment : Fragment() {

    private var columnCount = 1
    var comicList = ArrayList<Comic>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiCall()

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_comic_list, container, false)
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        list.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = MyComicListRecyclerViewAdapter(comicList)
        }

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ComicListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

      fun apiCall() = GlobalScope.async {
          val queue = Volley.newRequestQueue(activity)
        val timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        val hash =md5(timestamp.toString() + "029df42137a1ad8105bcf66a208bc081efbf4559" + "abae7768139eb68365c998fc37636a75")

        val url = "https://gateway.marvel.com:443/v1/public/comics?format=comic&formatType=comic&noVariants=true&limit=10&ts="+timestamp.toString()+"&apikey=abae7768139eb68365c998fc37636a75&hash="+hash


         val request = GlobalScope.async{JsonObjectRequest(Request.Method.GET, url, null,
                 Response.Listener { response ->
                     try {

                         val data = response.getJSONObject("data")
                         val results = data.getJSONArray("results")
                         for (i in 0 until results.length()) {
                             val comic = results.getJSONObject(i)
                             val title = comic.getString("title")
                             val description = comic.getString("description")
                             val imageArr = comic.getJSONArray("images")
                             var path = ""
                             for (j in 0 until imageArr.length()) {
                                 val image = imageArr.getJSONObject(j)
                                 path = image.getString("path")
                                 //Log.i("image", path.toString())
                             }
                             val newComic = Comic(title, description, path)
                             comicList.add(newComic)
                             //Log.i("name", comicList.toString())
                         }
                         list.apply {
                            adapter?.notifyDataSetChanged();
                         }
                         //Log.i("comic", comicList.toString())
                         //adapter.notifyDataSetChanged( )

                     } catch (e: JSONException) {

                         e.printStackTrace();
                     }


                 },
                 Response.ErrorListener { error ->
                     error.printStackTrace();
                     Log.i("error", error.toString())

                 }
         )}.await()

          queue.add(request);


      }

}