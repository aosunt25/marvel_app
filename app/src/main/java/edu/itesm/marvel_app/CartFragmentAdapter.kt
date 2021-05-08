package edu.itesm.marvel_app

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_comic_info.*

class CartFragmentAdapter(private val data: MutableList<Comic>?) : RecyclerView.Adapter<CartFragmentAdapter.ViewHolder>()  {


    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun bind(property: Comic){
            val title = view.findViewById<TextView>(R.id.txtitle)
            val description = view.findViewById<TextView>(R.id.txdescription)
            val imageView = view.findViewById<ImageView>(R.id.imageView)

            title.text = property.title
            description.text = property.description

            Glide.with(view.context)
                .load(property.imageUrl)
                //.circleCrop()
                .into(imageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cart_renglon, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data!![position])

    }

    fun deleteItem(index:Int){
        data?.removeAt(index)
        notifyDataSetChanged()
    }

    fun getItem(index: Int): Comic? {
        return data?.get(index)
    }

}