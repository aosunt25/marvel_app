package edu.itesm.marvel_app


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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_comic_info.*


//
class ComicInfoFragment : Fragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    // Incluye las variables de Analytics:
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var bundle: Bundle

    private val args by navArgs<ComicInfoFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View? {
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("comicss")

        //inicializa las variables:
        analytics = FirebaseAnalytics.getInstance(context)
        bundle = Bundle()
        // Para Binding con elementos del Layout
        return inflater.inflate(R.layout.fragment_comic_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val imagen = args.comic.imageUrl.toString()+"/portrait_incredible.jpg"
        Glide.with(view?.context).load(imagen).into(imageView);

        //imageView.setImageResource(R.drawable.logomarvel)

        Log.i("Image", args.comic.imageUrl.toString()+"/portrait_incredible.jpg")
        titulo.text = args.comic.title
        descripcion.text = args.comic.description


        agregarCarrito.setOnClickListener{
            agregarCarrito()
        }

    }

    fun agregarCarrito(){
        val text = "Se agrego al carrito"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, text, duration)

        val title = titulo.text
        val description = descripcion.text

        if(title.isNotEmpty() && description.isNotBlank()) {
            val usuario = Firebase.auth.currentUser
            reference = database.getReference("comicss/${usuario.uid}")
            val id = reference.push().key
            val comic = Comic(
                title.toString(),
                description.toString(),
                imageView.toString(),
            )
            reference.child(id!!).setValue(comic)
            toast.show()
        }
        bundle.putString("edu_itesm_marvel_app", "added_comic")
        analytics.logEvent("main", bundle)

    }

}

