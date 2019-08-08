package com.airbnb.epoxy.kotlinsample.models

import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.kotlinsample.R
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
            Uri.parse("https://video-1.gapo.vn/videos/results/8f17abb1-2acc-427b-b4b3-1b56fa4519ea/480p/file.m3u8")
        )

        val photos = ArrayList<CollageData>()

        photos.add(
            CollageData(
                "https://image-1.gapo.vn/images/cb566725-d98b-4b36-88ad-6f2735986345.jpeg",
                "",
                0,
                1280,
                1920,
                false
            )
        )
        photos.add(
            CollageData(
                "https://image-1.gapo.vn/images/cb566725-d98b-4b36-88ad-6f2735986345.jpeg",
                "",
                1,
                1280,
                1920,
                false
            )
        )
        photos.add(
            CollageData(
                "https://image-1.gapo.vn/images/cb566725-d98b-4b36-88ad-6f2735986345.jpeg",
                "",
                2,
                1280,
                1920,
                false
            )
        )
        photos.add(
            CollageData(
                "https://image-1.gapo.vn/images/cb566725-d98b-4b36-88ad-6f2735986345.jpeg",
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