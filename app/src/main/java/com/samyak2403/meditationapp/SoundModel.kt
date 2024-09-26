/*
 * Created by Samyak Kamble on 9/27/24, 12:02 AM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/27/24, 12:02 AM
 */

package com.samyak2403.meditationapp

data class SoundModel(
    val soundFile: Int,  // Resource ID of the sound file
    var isPlaying: Boolean = false
)
