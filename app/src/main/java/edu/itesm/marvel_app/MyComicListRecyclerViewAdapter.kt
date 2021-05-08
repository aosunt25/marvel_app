package edu.itesm.marvel_app

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide

import edu.itesm.marvel_app.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.cart_renglon.view.*
import kotlinx.android.synthetic.main.fragment_comic_info.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyComicListRecyclerViewAdapter(
    private val values: ArrayList<Comic>
) : RecyclerView.Adapter<MyComicListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comic_renglon, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.title
        holder.descriptionView.text = item.description

        holder.itemView.setOnClickListener {
            val action = ComicListFragmentDirections.actionComicListFragment3ToComicInfoFragment2(item)
            holder?.itemView.findNavController().navigate(action)

        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        val descriptionView: TextView = view.findViewById(R.id.descripcion)
        //val portadaView: TextView = view.findViewById(R.id.portada)



        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}