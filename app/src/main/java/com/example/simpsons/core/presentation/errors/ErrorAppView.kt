package com.example.simpsons.core.presentation.errors

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.simpsons.databinding.ViewErrorBinding

class ErrorAppView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = ViewErrorBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        hide()
    }

    fun render(errorAppUI: ErrorAppUI) {
        binding.apply {
            errorImage.setImageResource(errorAppUI.getImageError())
            titleErrorText.text = errorAppUI.getTitleError()
            descriptionErrorText.text = errorAppUI.getDescriptionError()
            retryErrorButton.setOnClickListener {
                errorAppUI.getActionRetry()
            }
        }
        visible()
    }

    fun hide() {
        visibility = View.GONE
    }

    fun visible() {
        visibility = View.VISIBLE
    }
}