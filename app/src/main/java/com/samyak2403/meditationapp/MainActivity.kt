/*
 * Created by Samyak Kamble on 9/27/24, 12:17 AM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/26/24, 11:54 PM
 */

package com.samyak2403.meditationapp



import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton: ImageButton
    private lateinit var seekBar: SeekBar
    private lateinit var currentTimeText: TextView
    private lateinit var durationTimeText: TextView
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        playButton = findViewById(R.id.playButton)
        seekBar = findViewById(R.id.seekBar)
        currentTimeText = findViewById(R.id.currentTime)
        durationTimeText = findViewById(R.id.durationTime)

        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.meditation_sound) // Replace with your audio file
        mediaPlayer.setOnPreparedListener {
            seekBar.max = mediaPlayer.duration
            durationTimeText.text = formatTime(mediaPlayer.duration)
        }

        // Play/Pause button listener
        playButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                playButton.setImageResource(R.drawable.ic_play) // Replace with play icon
            } else {
                mediaPlayer.start()
                playButton.setImageResource(R.drawable.ic_pause) // Replace with pause icon
                updateSeekBar()
            }
        }

        // SeekBar listener
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
                currentTimeText.text = formatTime(mediaPlayer.currentPosition)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Update SeekBar in real-time
        mediaPlayer.setOnCompletionListener {
            playButton.setImageResource(R.drawable.ic_play) // Reset to play icon when audio finishes
            seekBar.progress = 0
            currentTimeText.text = formatTime(0)
        }
    }

    private fun updateSeekBar() {
        seekBar.progress = mediaPlayer.currentPosition
        if (mediaPlayer.isPlaying) {
            handler.postDelayed({ updateSeekBar() }, 1000)
        }
    }

    private fun formatTime(millis: Int): String {
        val minutes = millis / 1000 / 60
        val seconds = millis / 1000 % 60
        return String.format("%d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
        handler.removeCallbacksAndMessages(null)
    }
}
