package com.flatstack.mytddapplication.ui.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import androidx.fragment.app.Fragment
import com.flatstack.mytddapplication.R
import kotlinx.android.synthetic.main.fragment_anim_first.view.*

class AnimationFirstFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_anim_first, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(view) {
        val downAnimation = ObjectAnimator.ofFloat(btn_push, "translationY", 400f).apply {
            duration = 3000
            interpolator = BounceInterpolator()
        }

        val upAnimation = ObjectAnimator.ofFloat(btn_push, "translationY", 10f).apply {
            duration = 3000
            interpolator = AccelerateDecelerateInterpolator()
        }

        val rotationAnimation = ObjectAnimator.ofFloat(btn_push, "rotation", 180f).apply {
            duration = 3000
        }

        val rotationBackAnimation = ObjectAnimator.ofFloat(btn_push, "rotation", 0f).apply {
            duration = 3000
            interpolator = AnticipateOvershootInterpolator()
        }

        btn_push.setOnClickListener {
            AnimatorSet().apply {
                play(downAnimation).with(rotationAnimation)
                play(upAnimation).after(downAnimation)
                play(rotationBackAnimation).after(upAnimation)
                start()
            }
        }
    }
}
