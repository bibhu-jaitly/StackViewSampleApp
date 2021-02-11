package com.example.stackviewframeworkapp

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.TranslateAnimation
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.custom_stack_element.view.*
import kotlinx.android.synthetic.main.custom_stack_layout.view.*
import kotlin.collections.ArrayDeque


enum class ElementState(val label: Int) {
    COLLAPSED(R.string.collapsed_state),
    EXPANDED(R.string.expanded_state),
    INVISIBLE(R.string.invisible_state)
}

private const val FIRST = 0
private const val SECOND = 1
private const val THIRD = 2
private const val FOURTH = 3

class StackLayout @JvmOverloads
constructor(
    private val ctx: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    private var viewStack: ArrayDeque<StackElement> = ArrayDeque()

    var currentActive: Int? = null
        set(value) {
            field = value
        }

    init {
        currentActive = FIRST
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_stack_layout, this)
        initialiseVariables()
        setBackgroundResource(R.drawable.stack_layout_background)
    }

    private fun initialiseVariables() {
        stack_element_1.position = FIRST
        insertView(stack_element_1)
        stack_element_3.next_step_btn.setOnClickListener {
            stack_element_4.position = FOURTH
            currentActive = FOURTH
            insertView(stack_element_4)
        }
        stack_element_1.next_step_btn.setOnClickListener {
            stack_element_2.position = SECOND
            currentActive = SECOND
            insertView(stack_element_2)
        }
        stack_element_2.next_step_btn.setOnClickListener {
            stack_element_3.position = THIRD
            currentActive = THIRD
            insertView(stack_element_3)
        }
        stack_element_4.next_step_btn.visibility = GONE
        stack_element_1.setOnClickListener {
            if (stack_element_1.state == ElementState.COLLAPSED) {
                for (i in 1 until viewStack.size) {
                    popView()
                }
            }
        }
        stack_element_2.setOnClickListener {
            if (stack_element_2.state == ElementState.COLLAPSED) {
                for (i in 2 until viewStack.size) {
                    popView()
                }
            }
        }
        stack_element_3.setOnClickListener {
            if (stack_element_3.state == ElementState.COLLAPSED) {
                popView()
            }
        }
    }

    private fun insertView(stackElement: StackElement) {
        if (currentActive == FIRST) {
            viewStack.add(stackElement)
        } else {
            viewStack.last().state = ElementState.COLLAPSED
            viewStack.add(stackElement)
        }
        translateIn(stackElement)

    }

    private fun popView() {
        if (!viewStack.isNullOrEmpty() && viewStack.size > 1) {
            val element = viewStack.removeLast()
            element.state = null
            element.next_step_btn.visibility = GONE
            translateOut(element)
            currentActive = currentActive?.minus(1)
        }
        if (!viewStack.isNullOrEmpty() && viewStack.size >= 1) {
            val element = viewStack.last()
            element.state = ElementState.EXPANDED
            element.next_step_btn.visibility = VISIBLE
        }
    }

    fun handleBack() {
        popView()
    }

    private fun translateIn(view: StackElement) {
        val animate = TranslateAnimation(
            0F,
            0F,
            view.height.toFloat(),
            0F
        )
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = VISIBLE
    }

    private fun translateOut(view: StackElement) {
        val animate = TranslateAnimation(
            0F,
            0F,
            0F,
            view.height.toFloat()
        )
        animate.duration = 300
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = GONE
    }
}

class StackElement @JvmOverloads
constructor(
    private val ctx: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    var state: ElementState? = null
        set(value) {
            field = value
            setupView()
        }

    var position: Int? = null
        set(value) {
            field = value
            setupBackground()
            populateDummyData()
            state = ElementState.EXPANDED
        }

    @DrawableRes
    var viewBackground1: Int = R.drawable.container_1_background

    @DrawableRes
    var viewBackground2: Int = R.drawable.container_2_background

    @DrawableRes
    var viewBackground3: Int = R.drawable.container_3_background

    @DrawableRes
    var viewBackground4: Int = R.drawable.container_4_background

    init {
        // get the inflater service from the android system
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // inflate the layout into "this" component
        inflater.inflate(R.layout.custom_stack_element, this)
        setupBackground()
    }

    private fun setupView() {
        when (state) {
            ElementState.COLLAPSED -> {
                handleCollapsedView()
            }
            ElementState.EXPANDED -> {
                handleExpandedView()
            }
        }
    }

    private fun setupBackground() {
        when (position) {
            FIRST -> {
                setBackgroundResource(viewBackground1)
            }
            SECOND -> {
                setBackgroundResource(viewBackground2)
            }
            THIRD -> {
                setBackgroundResource(viewBackground3)
            }
            else -> {
                setBackgroundResource(viewBackground4)
            }
        }
    }

    private fun handleExpandedView() {
        if (position == FOURTH) {
            next_step_btn.visibility = GONE
        } else {
            next_step_btn.visibility = VISIBLE
        }
        collapsed1_tv.visibility = GONE
        collapsed2_tv.visibility = GONE
    }

    private fun handleCollapsedView() {
        next_step_btn.visibility = GONE
        collapsed1_tv.visibility = VISIBLE
        collapsed2_tv.visibility = VISIBLE
    }

    private fun populateDummyData() {
        next_step_btn.text = when (position) {
            FIRST -> "Proceed to EMI selection"
            SECOND -> "Select your bank account"
            THIRD -> "Tap for 1-click KYC"
            else -> "Proceed"
        }
        collapsed1_tv.text = when (position) {
            FIRST -> "credit amount"
            SECOND -> "EMI"
            THIRD -> "where should we send the money?"
            else -> "Final Amount"
        }
        collapsed2_tv.text = when (position) {
            FIRST -> "Rs 1,50,000"
            SECOND -> "Rs 4242 /mo"
            THIRD -> "amount will be credited to the bank"
            else -> "Rs 1,50,000"
        }
    }
}





