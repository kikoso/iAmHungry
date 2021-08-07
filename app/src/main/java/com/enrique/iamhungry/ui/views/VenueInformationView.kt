package com.enrique.iamhungry.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.enrique.iamhungry.databinding.VenueInformationViewBinding
import com.enrique.iamhungry.model.venue.view.VenueView
import com.squareup.picasso.Picasso

class VenueInformationView constructor(context: Context, attrs: AttributeSet?) :
    CardView(context, attrs) {
    private var binding: VenueInformationViewBinding =
        VenueInformationViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var onCloseItemClicked: (() -> Unit)? = null

    init {
        binding.close.setOnClickListener { onCloseItemClicked?.invoke() }
    }

    fun setOnCloseItemClickedListener(onCloseItemClicked: () -> Unit) {
        this.onCloseItemClicked = onCloseItemClicked
    }

    fun setData(venue: VenueView) {
        binding.name.text = venue.name
        binding.address.text = venue.location.address
        binding.category.text = venue.category[0].name
        binding.name.text = venue.name
        if (venue.pictureUrl.isNotEmpty()) {
            Picasso.get().load(venue.pictureUrl).placeholder(R.drawable.ic_placeholder_view).into(binding.image)
        } else {
            binding.image.setImageResource(R.drawable.ic_placeholder_view)
        }
    }
}
