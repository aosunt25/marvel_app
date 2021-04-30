package edu.itesm.marvel_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import edu.itesm.marvel_app.dummy.DummyContent
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
    private var comicList = mutableListOf<Comic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comic_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyComicListRecyclerViewAdapter(DummyContent.ITEMS)
            }
        }
        return view
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

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    private fun apiCall(){
        // Instantiate the RequestQueue.

        val timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        val hash =md5(timestamp.toString()+"029df42137a1ad8105bcf66a208bc081efbf4559"+"abae7768139eb68365c998fc37636a75")

        val url = "https://gateway.marvel.com:443/v1/public/comics?format=comic&formatType=comic&noVariants=true&limit=10&ts="+timestamp.toString()+"&apikey=abae7768139eb68365c998fc37636a75&hash="+hash
        Log.i("hola", url.toString())


        val request =  JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener{ response ->
                    try{
                        val data =  response.getJSONObject("data")
                        val results = data.getJSONArray("results")
                        for (i in 0 until results.length()){
                            val comic = results.getJSONObject(i)
                            val title = comic.getString("title")
                            val description = comic.getString("description")
                            val imageArr = comic.getJSONArray("images")
                            var path = ""
                            for (j in 0 until imageArr.length()){
                                val image = imageArr.getJSONObject(j)
                                path = image.getString("path")
                                //Log.i("image", path.toString())
                            }
                            val newComic = Comic(title, description, path)
                            comicList.add(newComic)
                            //Log.i("name", title.toString())
                        }
                        Log.i("comic", comicList.toString())
                    }catch(e: JSONException){

                        e.printStackTrace();
                    }


                },
                Response.ErrorListener{
                    error-> error.printStackTrace();

                }

        )




    }
}