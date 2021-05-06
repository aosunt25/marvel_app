package edu.itesm.marvel_app

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_comic_info.*
import kotlinx.android.synthetic.main.fragment_start.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ComicInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ComicInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val args by navArgs<ComicInfoFragmentArgs>()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //val image = view?.findViewById<ImageView>(R.id.imageView)

        Glide.with(view?.context).load("http://i.annihil.us/u/prod/marvel/i/mg/c/80/4bc5fe7a308d7/portrait_incredible.jpg").into(imageView);

        //imageView.setImageResource(R.drawable.logomarvel)
        Log.i("Image", args.comic.imageUrl.toString()+"/portrait_incredible.jpg")
        titulo.text = args.comic.title
        descripcion.text = args.comic.description
        //imageView = getBitmapFromURL(args.comic.imageUrl.toString()+"/portrait_incredible.jpg")


        agregarCarrito.setOnClickListener{
          agregarCarrito()
        }

    }

    fun agregarCarrito(){
        val text = "Se agrego al carrito"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(context, text, duration)
        toast.show()

        //val database = Firebase.database
        //val myRef = database.getReference("message")

       // myRef.setValue("Hello, World!")


    }

}