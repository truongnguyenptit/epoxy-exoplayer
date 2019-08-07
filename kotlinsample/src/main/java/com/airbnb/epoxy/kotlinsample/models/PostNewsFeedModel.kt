package com.airbnb.epoxy.kotlinsample.models

import android.net.Uri
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.kotlinsample.R
import com.gg.gapo.features.posts.PostNewsFeedViewHolder

@EpoxyModelClass(layout = R.layout.item_post)
abstract class PostNewsFeedModel : EpoxyModelWithHolder<PostNewsFeedViewHolder>() {

    @EpoxyAttribute
    lateinit var listener: () -> Unit

    override fun bind(holder: PostNewsFeedViewHolder) {
        holder.parent.tag = holder
        holder.bindUri(0, Uri.parse("https://video-1.gapo.vn/videos/results/8f17abb1-2acc-427b-b4b3-1b56fa4519ea/480p/file.m3u8"))
    }
}