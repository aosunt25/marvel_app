package edu.itesm.marvel_app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import edu.itesm.marvel_app.databinding.FragmentCartBinding
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_comic_list.*


abstract class SwipeToDelete(
    context: Context,
    direccion: Int, direccionArrastre: Int
):
    ItemTouchHelper.SimpleCallback(direccion, direccionArrastre){
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

}


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

    private fun borraDatos(comic: Comic){
        val usuario = Firebase.auth.currentUser
        val referencia = FirebaseDatabase.getInstance().getReference("comicss/${usuario.uid}/${comic.id}")
            referencia.removeValue().addOnSuccessListener {
                cargaDatos()
                Toast.makeText(getContext(), "Borrado del carrito", Toast.LENGTH_SHORT).show()
            }

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
                            var id =comic.child("id").value.toString()
                            var title = comic.child("title").value.toString()

                            var description = comic.child("description").value.toString()
                            var imageUrl = comic.child("imageUrl").value.toString()
                            val newComic =
                                Comic(id ,title, description, "$imageUrl")
                            Log.i("value", newComic.toString())
                            listaComics.add(newComic)

                        }

                        layoutManager = LinearLayoutManager(activity)
                        adapter = CartFragmentAdapter(listaComics)

                        val item = object : SwipeToDelete(
                            getContext(),
                            ItemTouchHelper.UP, ItemTouchHelper.LEFT
                        ) {
                            override fun onSwiped(
                                viewHolder: RecyclerView.ViewHolder,
                                direction: Int
                            ) {
                                super.onSwiped(viewHolder, direction)
                                val comic = listaComics[viewHolder.adapterPosition]

                                borraDatos(comic)
                            }
                        }
                        val itemTouchHelper = ItemTouchHelper(item)
                        itemTouchHelper.attachToRecyclerView(bind.list)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        //Toast.makeText(this@CartFragment,"Error al obtener datos",Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}