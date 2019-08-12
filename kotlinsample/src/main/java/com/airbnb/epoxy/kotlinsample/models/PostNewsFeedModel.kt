package com.airbnb.epoxy.kotlinsample.models

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.kotlinsample.R
import com.airbnb.epoxy.widget.PressablePlayerSelector
import com.gg.gapo.features.posts.PostNewsFeedViewHolder
import com.gg.gapo.widget.collageview.CollageData
import okhttp3.MediaType

@EpoxyModelClass(layout = R.layout.item_post)
abstract class PostNewsFeedModel : EpoxyModelWithHolder<PostNewsFeedViewHolder>() {

    @EpoxyAttribute
    lateinit var listener: () -> Unit

    override fun bind(holder: PostNewsFeedViewHolder) {
        holder.parent.tag = holder
        holder.bindUri(
            0,
            Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        )

        val photos = ArrayList<CollageData>()

        photos.add(
            CollageData(
                "https://www.gstatic.com/webp/gallery/1.jpg",
                "",
                0,
                1280,
                1920,
                false
            )
        )
        photos.add(
            CollageData(
                "https://www.gstatic.com/webp/gallery/1.jpg",
                "",
                1,
                1280,
                1920,
                false
            )
        )
        photos.add(
            CollageData(
                "https://www.gstatic.com/webp/gallery/1.jpg",
                "",
                2,
                1280,
                1920,
                false
            )
        )
        photos.add(
            CollageData(
                "https://www.gstatic.com/webp/gallery/1.jpg",
                "",
                3,
                1280,
                1920,
                false
            )
        )

        if (photos.isNotEmpty()) {
            val isHeader = photos.size < 5
            holder.collageView.photoMargin(4)
                .photoPadding(0)
                .backgroundColor(
                    ContextCompat.getColor(
                        holder.collageView.context,
                        R.color.abc_background_cache_hint_selector_material_dark
                    )
                )
                .useFirstAsHeader(isHeader)
                .useCards(false)
                .loadPhotos(photos)
        }
    }
}