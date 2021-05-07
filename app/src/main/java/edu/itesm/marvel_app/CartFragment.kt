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
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import edu.itesm.marvel_app.databinding.ActivityMainBinding
import edu.itesm.marvel_app.databinding.FragmentCartBinding
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_comic_list.*


class CartFragment : Fragment() {

    private lateinit var bind: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Para Binding con elementos del Layout
        bind = FragmentCartBinding.inflate(layoutInflater, container, false)
        return bind.root
        // Para Binding con elementos del Layout
        //return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargaDatos()
    }

    fun cargaDatos() {
        var listaComics = ArrayList<Comic>()
        var reference: DatabaseReference
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val usuario = Firebase.auth.currentUser
        reference = database.getReference("comicss/${usuario.uid}")
        activity?.let {
            bind.list.apply {
                reference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        for (comic in snapshot.children) {
                            var title = comic.child("title").value.toString()

                            var description = comic.child("description").value.toString()
                            var path = comic.child("path").value.toString()

                            val newComic = Comic(title, description, path)
                            Log.i("value", newComic.toString())
                            listaComics.add(newComic)
                        }

                        layoutManager = LinearLayoutManager(activity)
                        adapter = CartFragmentAdapter(listaComics)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        //Toast.makeText(this@CartFragment,"Error al obtener datos",Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}