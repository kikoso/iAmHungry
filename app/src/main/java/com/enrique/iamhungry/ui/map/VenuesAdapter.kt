package com.enrique.iamhungry.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enrique.iamhungry.databinding.VenueItemBinding
import com.enrique.iamhungry.model.venue.view.VenueView
import com.squareup.picasso.Picasso

class VenuesAdapter(private val onVenueSelected: (venue: VenueView) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {

    var venues = mutableListOf<VenueView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(VenueItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(venues[position])
        holder.itemView.setOnClickListener {
            onVenueSelected(venues[position])
            venues.removeAt(position)

            // Hello hacks. Calling notifyItemRemoved does not update the array. We need to call also
            // notifyItemRangeChanged (and of course notifyItemRemoved for the nice animation)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, venues.size)
        }
    }

    override fun getItemCount(): Int = venues.size

}

class ViewHolder(private val binding: VenueItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: VenueView) {
        binding.name.text = data.name
        binding.category.text = data.category[0].name
        binding.address.text = data.location.address
        if (data.pictureUrl.isNotEmpty()) {
            Picasso.get().load(data.pictureUrl).into(binding.image)
        }
    }
}

