package com.gg.gapo.features.posts

import android.net.Uri
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.kotlinsample.DebugLog
import com.airbnb.epoxy.kotlinsample.R
import com.airbnb.epoxy.kotlinsample.collageview.CollageView
import com.airbnb.epoxy.kotlinsample.helpers.KotlinEpoxyHolder
import com.airbnb.epoxy.kotlinsample.helpers.KotlinEpoxyPlayerHolder
import com.airbnb.epoxy.media.PlaybackInfo
import com.airbnb.epoxy.toro.ToroPlayer
import com.airbnb.epoxy.toro.ToroUtil
import com.airbnb.epoxy.widget.EthanRecyclerView
import com.airbnb.epoxy.widget.PressablePlayerSelector
import com.google.android.exoplayer2.ui.PlayerView
import im.ene.toro.exoplayer.ExoPlayerDispatcher
import im.ene.toro.exoplayer.ExoPlayerViewHelper
import im.ene.toro.exoplayer.Playable
import java.lang.String.format
import java.util.*

class PostNewsFeedViewHolder : KotlinEpoxyPlayerHolder() {

    private val listener = object : Playable.DefaultEventListener() {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)
            state.text = (format(Locale.getDefault(), "STATE: %d・PWR: %s", playbackState, playWhenReady))
        }
    }

    init {
        DebugLog.logD("TAG","init")
    }

    private var helper: ExoPlayerViewHelper? = null
    private var mediaUri: Uri? = null
    private var position: Int =  1

    override fun getPlayerView(): View {
        DebugLog.logD("TAG","getPlayerView")
        return this.playerView
    }

    override fun getCurrentPlaybackInfo(): PlaybackInfo {
        return if (helper != null) helper!!.latestPlaybackInfo else PlaybackInfo()
    }

    override fun initialize(container: EthanRecyclerView, playbackInfo: PlaybackInfo) {
        DebugLog.logD("TAG","initialize: ")
        if (mediaUri == null) throw IllegalStateException("mediaUri is null.")
        if (helper == null) {
            helper = ExoPlayerViewHelper(this, mediaUri!!)
            helper!!.addEventListener(listener)
        }
        helper!!.initialize(container, playbackInfo)
    }

    override fun play() {
        DebugLog.logD("TAG","play")
        if (helper != null) helper!!.play()
    }

    override fun pause() {
        DebugLog.logD("TAG","pause")
        if (helper != null) helper!!.pause()
    }

    override fun isPlaying(): Boolean {
        val isPlay = helper != null && helper!!.isPlaying
        DebugLog.logD("TAG","isPlaying $isPlay")
        return helper != null && helper!!.isPlaying
    }

    override fun release() {
        if (helper != null) {
            helper!!.removeEventListener(listener)
            helper!!.release()
            helper = null
        }
        DebugLog.logD("TAG","release")
    }

    override fun wantsToPlay(): Boolean {
        DebugLog.logD("TAG","wantsToPlay")
        return ToroUtil.visibleAreaOffset(this, parent) >= 0.85
    }

    override fun getPlayerOrder(): Int {
        DebugLog.logD("TAG","getPlayerOrder")
        return position
    }

    val parent by bind<LinearLayout>(R.id.parent)
    val playerView by bind<PlayerView>(R.id.fb_video_player)
    val state by bind<TextView>(R.id.player_state)
    val collageView by bind<CollageView>(R.id.collage_view)


    fun bindUri(position: Int, uri: Uri?) {
        this.position = position
        mediaUri = uri
        playerView.visibility = View.VISIBLE
        DebugLog.logD("TAG","bindUri")
    }

}