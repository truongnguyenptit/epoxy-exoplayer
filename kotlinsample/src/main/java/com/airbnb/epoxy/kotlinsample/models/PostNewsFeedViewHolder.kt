package com.gg.gapo.features.posts

import android.net.Uri
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.kotlinsample.DebugLog
import com.airbnb.epoxy.kotlinsample.R
import com.airbnb.epoxy.kotlinsample.helpers.KotlinEpoxyHolder
import com.airbnb.epoxy.media.PlaybackInfo
import com.airbnb.epoxy.toro.ToroPlayer
import com.airbnb.epoxy.toro.ToroUtil
import com.airbnb.epoxy.widget.EthanRecyclerView
import com.google.android.exoplayer2.ui.PlayerView
import im.ene.toro.exoplayer.ExoPlayerViewHelper
import im.ene.toro.exoplayer.Playable
import java.lang.String.format
import java.util.*

class PostNewsFeedViewHolder : KotlinEpoxyHolder(), ToroPlayer {

    private val listener = object : Playable.DefaultEventListener() {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)
            state.text = (format(Locale.getDefault(), "STATE: %d・PWR: %s", playbackState, playWhenReady))
        }
    }

    init {
        DebugLog.logD("MEDIA","init")
    }

    private var helper: ExoPlayerViewHelper? = null
    private var mediaUri: Uri? = null
    private var position: Int =  - 1

    override fun getPlayerView(): View {
        DebugLog.logD("MEDIA","getPlayerView")
        return this.playerView
    }

    override fun getCurrentPlaybackInfo(): PlaybackInfo {
        return if (helper != null) helper!!.latestPlaybackInfo else PlaybackInfo()
    }

    override fun initialize(container: EthanRecyclerView, playbackInfo: PlaybackInfo) {
        DebugLog.logD("MEDIA","initialize: ")
        if (mediaUri == null) throw IllegalStateException("mediaUri is null.")
        if (helper == null) {
            helper = ExoPlayerViewHelper(this, mediaUri!!)
            helper!!.addEventListener(listener)
        }
        helper!!.initialize(container, playbackInfo)
    }

    override fun play() {
        DebugLog.logD("MEDIA","play")
        if (helper != null) helper!!.play()
    }

    override fun pause() {
        DebugLog.logD("MEDIA","pause")
        if (helper != null) helper!!.pause()
    }

    override fun isPlaying(): Boolean {
        DebugLog.logD("MEDIA","isPlaying")
        return helper != null && helper!!.isPlaying
    }

    override fun release() {
        if (helper != null) {
            helper!!.removeEventListener(listener)
            helper!!.release()
            helper = null
        }
        DebugLog.logD("MEDIA","release")
    }

    override fun wantsToPlay(): Boolean {
        DebugLog.logD("MEDIA","wantsToPlay")
        return ToroUtil.visibleAreaOffset(this, parent) >= 0.85
    }

    override fun getPlayerOrder(): Int {
        DebugLog.logD("MEDIA","getPlayerOrder")
        return position
    }

    val parent by bind<LinearLayout>(R.id.parent)
    val playerView by bind<PlayerView>(R.id.fb_video_player)
    val state by bind<TextView>(R.id.player_state)


    fun bindUri(position: Int, uri: Uri?) {
        this.position = position
        mediaUri = Uri.parse("file:///android_asset/tos.mp4")
        playerView.visibility = View.VISIBLE

        DebugLog.logD("MEDIA","bindUri")
    }

}