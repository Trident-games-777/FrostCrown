package com.ea.gp.apexlegendsmobilef.ui.screens

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.ea.gp.apexlegendsmobilef.R

class GameActivity : AppCompatActivity() {
    private lateinit var ivFront1: ImageView
    private lateinit var ivFront2: ImageView
    private lateinit var ivFront3: ImageView

    private lateinit var ivBack1: ImageView
    private lateinit var ivBack2: ImageView
    private lateinit var ivBack3: ImageView

    private lateinit var ivArrow1: ImageView
    private lateinit var ivArrow2: ImageView
    private lateinit var ivArrow3: ImageView

    private lateinit var ivToFind: ImageView

    private val elements: List<Int> = listOf(
        R.drawable.element1,
        R.drawable.element2,
        R.drawable.element3
    )

    private val onClickListener: ((View) -> Unit) = { view ->
        ivBack1.isClickable = false
        ivBack2.isClickable = false
        ivBack3.isClickable = false

        ivFront1.isClickable = false
        ivFront2.isClickable = false
        ivFront3.isClickable = false

        imageGuessed = view.tag as Int

        when (shuffled.indexOf(imageGuessed)) {
            0 -> {
                ivArrow1.visibility = View.VISIBLE
                animateCard(start = ivBack1, end = ivFront1).doOnEnd {
                    animateCard(start = ivBack2, end = ivFront2)
                    animateCard(start = ivBack3, end = ivFront3).doOnEnd {
                        endGame()
                    }
                }
            }
            1 -> {
                ivArrow2.visibility = View.VISIBLE
                animateCard(start = ivBack2, end = ivFront2).doOnEnd {
                    animateCard(start = ivBack1, end = ivFront1)
                    animateCard(start = ivBack3, end = ivFront3).doOnEnd {
                        endGame()
                    }
                }
            }
            2 -> {
                ivArrow3.visibility = View.VISIBLE
                animateCard(start = ivBack3, end = ivFront3).doOnEnd {
                    animateCard(start = ivBack1, end = ivFront1)
                    animateCard(start = ivBack2, end = ivFront2).doOnEnd {
                        endGame()
                    }
                }
            }
        }
    }

    private fun endGame() {
        val intent = Intent(this, EndGameActivity::class.java)
        intent.putExtra("extra_result", imageToFind == imageGuessed)
        startActivity(intent)
        finish()
    }

    private var imageToFind: Int = 0
    private var imageGuessed: Int = 0
    private var shuffled: List<Int> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initViews()
        setImageToFind()
        shuffled = setImagesToGuess()
        setClickListeners()
    }

    private fun initViews() {
        ivFront1 = findViewById(R.id.ivFront1)
        ivFront2 = findViewById(R.id.ivFront2)
        ivFront3 = findViewById(R.id.ivFront3)

        ivBack1 = findViewById(R.id.ivBack1)
        ivBack2 = findViewById(R.id.ivBack2)
        ivBack3 = findViewById(R.id.ivBack3)

        ivArrow1 = findViewById(R.id.ivArrow1)
        ivArrow2 = findViewById(R.id.ivArrow2)
        ivArrow3 = findViewById(R.id.ivArrow3)

        ivToFind = findViewById(R.id.ivToFind)
    }

    private fun setImageToFind(): Int = with(elements.random()) {
        imageToFind = this
        ivToFind.setImageResource(this)
        this
    }

    private fun setImagesToGuess(): List<Int> {
        val shuffled = elements.shuffled()

        ivFront1.setImageResource(shuffled[0])
        ivBack1.tag = shuffled[0]
        ivFront2.setImageResource(shuffled[1])
        ivBack2.tag = shuffled[1]
        ivFront3.setImageResource(shuffled[2])
        ivBack3.tag = shuffled[2]

        return shuffled
    }

    private fun setClickListeners() {
        ivBack1.setOnClickListener(onClickListener)
        ivBack2.setOnClickListener(onClickListener)
        ivBack3.setOnClickListener(onClickListener)
    }

    private fun animateCard(start: ImageView, end: ImageView): AnimatorSet {
        val frontAnim = AnimatorInflater
            .loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        val backAnim = AnimatorInflater
            .loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet
        frontAnim.setTarget(start)
        backAnim.setTarget(end)
        frontAnim.start()
        backAnim.start()
        return frontAnim
    }
}