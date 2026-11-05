package org.sekatol.bandoriplayer

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer

class MediaPlayerPool(
    private val context: Context,
    maxSize: Int = 8
) {
    private val idle = ArrayDeque<MediaPlayer>()
    private val all = mutableListOf<MediaPlayer>()
    private var released = false

    init {
        repeat(maxSize) {
            val mp = MediaPlayer()
            mp.setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            idle.add(mp)
            all.add(mp)
        }
    }

    fun play(resId: Int, offsetMs: Long = 0L) {
        if (released) return
        val player = idle.removeFirstOrNull() ?: return // 如果没有idle的就不播放了

        try {
            player.reset()
            context.resources.openRawResourceFd(resId).use { afd ->
                player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            }

            // 每次播放前重新挂监听，保证归还逻辑一定生效
            player.setOnCompletionListener { recycle(player) }
            player.setOnErrorListener { _, _, _ ->
                recycle(player)
                true
            }

            player.prepare()
            player.seekTo(offsetMs.toInt())
            player.start()
        } catch (e: Exception) {
            recycle(player)
        }
    }

    fun stopAll() {
        if (released) return
        all.forEach {
            it.stop()
            recycle(it)
        }
    }

    private fun recycle(player: MediaPlayer) {
        if (released) return
        // 防止同一实例被重复归还
        if (!idle.contains(player)) {
            idle.add(player)
        }
    }

    fun release() {
        released = true
        all.forEach { runCatching { it.release() } }
        all.clear()
        idle.clear()
    }
}