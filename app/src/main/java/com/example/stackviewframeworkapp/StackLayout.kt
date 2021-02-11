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
    private lateinit var stackElement1: StackElement
    private lateinit var stackElement2: StackElement
    private lateinit var stackElement3: StackElement
    private lateinit var stackElement4: StackElement

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
        stackElement1 = sl1
        stackElement2 = sl2
        stackElement3 = sl3
        stackElement4 = sl4
        stackElement1.position = FIRST
        insertView(stackElement1)
        stackElement1.next_step_btn.setOnClickListener {
            Log.d("stackElement1", "onclick****")
           // stackElement1.next_step_btn.isEnabled = false
            stackElement2.position = SECOND
            currentActive = SECOND
            insertView(stackElement2)
        }
        stackElement2.next_step_btn.setOnClickListener {
            Log.d("stackElement2", "onclick****")
           // stackElement2.next_step_btn.isEnabled = false
            stackElement3.position = THIRD
            currentActive = THIRD
            insertView(stackElement3)
        }
        stackElement3.next_step_btn.setOnClickListener {
            Log.d("stackElement3", "onclick****")
            //stackElement3.next_step_btn.isEnabled = false
            stackElement4.position = FOURTH
            currentActive = FOURTH
            insertView(stackElement4)
        }
        stackElement4.next_step_btn.visibility = GONE
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
        var lastViewPosition: Int? = null
        if (!viewStack.isNullOrEmpty()) {
            val element = viewStack.removeLast()
            element.state = null
            lastViewPosition = element.position
            translateOut(element)
            currentActive = currentActive?.minus(1)
        }
        if (!viewStack.isNullOrEmpty()) {
            //updateLastItem(lastViewPosition)
        }
    }

    private fun updateLastItem(lastPopPosition: Int?) {
        lastPopPosition?.let { position ->
            Log.d("position***", "$position")
            when (position - 1) {
                FIRST -> {
                    Log.d("position***", "first")
                    stackElement1.next_step_btn.isEnabled = true
                    stackElement2.next_step_btn.isEnabled = false
                    stackElement3.next_step_btn.isEnabled = false
                    stackElement1.state = ElementState.EXPANDED
                }
                SECOND -> {
                    Log.d("position***", "second")
                    stackElement1.next_step_btn.isEnabled = false
                    stackElement2.next_step_btn.isEnabled = true
                    stackElement3.next_step_btn.isEnabled = false
                    stackElement2.state = ElementState.EXPANDED
                }
                THIRD -> {
                    Log.d("position***", "third")
                    stackElement1.next_step_btn.isEnabled = false
                    stackElement2.next_step_btn.isEnabled = false
                    stackElement3.next_step_btn.isEnabled = true
                    stackElement3.state = ElementState.EXPANDED
                }
            }
        }
    }


    fun handleBack() {
        popView()
    }

    private fun translateIn(view: StackElement) {
        view.visibility = VISIBLE
        val animate = TranslateAnimation(
            0F,
            0F,
            view.height.toFloat(),
            0F
        )
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    private fun translateOut(view: StackElement) {
        view.visibility = GONE
        val animate = TranslateAnimation(
            0F,
            0F,
            0F,
            view.height.toFloat()
        )
        animate.duration = 300
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    fun clearData() {
        /*  stackElement1.state = ElementState.EXPANDED
          stackElement2.state = null
          stackElement3.state = null
          stackElement4.state = null*/
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
        }
        collapsed1_tv.visibility = GONE
        collapsed2_tv.visibility = GONE
        expand_iv.visibility = GONE
    }

    private fun handleCollapsedView() {
        collapsed1_tv.visibility = VISIBLE
        expand_iv.visibility = VISIBLE
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





