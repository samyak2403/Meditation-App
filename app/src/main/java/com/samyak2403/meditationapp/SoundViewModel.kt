/*
 * Created by Samyak Kamble on 9/27/24, 12:03 AM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/27/24, 12:03 AM
 */

package com.samyak2403.meditationapp

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SoundViewModel : ViewModel() {
    private val _isPlaying = MutableLiveData<Boolean>(false)
    val isPlaying: LiveData<Boolean> = _isPlaying

    private lateinit var mediaPlayer: MediaPlayer

    fun initializeMediaPlayer(context: Context, soundResId: Int) {
        mediaPlayer = MediaPlayer.create(context, soundResId)
    }

    fun playPauseSound() {
        if (_isPlaying.value == true) {
            pauseSound()
        } else {
            playSound()
        }
    }

    private fun playSound() {
        mediaPlayer.start()
        _isPlaying.value = true
    }

    private fun pauseSound() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            _isPlaying.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }
}
