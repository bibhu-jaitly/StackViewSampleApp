
package com.example.stackviewframeworkapp

/*class test {
}

package com.example.stackviewframeworkapp

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.custom_stack_element.view.*
import kotlinx.android.synthetic.main.custom_stack_layout.view.*
import java.util.*


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

    private lateinit var viewQueue : Queue<StackElement>
    private lateinit var stackElement1: StackElement
    private lateinit var stackElement2: StackElement
    private lateinit var stackElement3: StackElement
    private lateinit var stackElement4: StackElement

    var currentActive: Int? = null
        set(value) {
            field = value
            setupView()
        }

    init {

        // get the inflater service from the android system
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // inflate the layout into "this" component
        inflater.inflate(R.layout.custom_stack_layout, this)
        initialiseVariables()
        // finally, we set the background of our component
        setBackgroundResource(R.drawable.stack_layout_background)
    }

    private fun initialiseVariables() {
        stackElement1 = sl1
        stackElement2 = sl2
        stackElement3 = sl3
        stackElement4 = sl4
        stackElement1.position = FIRST
        viewQueue.add(stackElement1)
        stackElement1.next_step_btn.setOnClickListener {
            stackElement2.position = SECOND
            stackElement1.state = ElementState.COLLAPSED
            viewQueue.add(stackElement2)
            currentActive = SECOND
        }
        stackElement2.next_step_btn.setOnClickListener {
            stackElement3.position = THIRD
            stackElement2.state = ElementState.COLLAPSED
            viewQueue.add(stackElement3)
            currentActive = THIRD
        }
        stackElement3.next_step_btn.setOnClickListener {
            stackElement4.position = FOURTH
            stackElement3.state = ElementState.COLLAPSED
            viewQueue.add(stackElement4)
            currentActive = FOURTH
        }
        stackElement4.next_step_btn.visibility = GONE
    }

    private fun setupView() {
        when (currentActive) {
            SECOND -> {
                moveToSecond()
            }
            THIRD -> {
                moveToThird()
            }
            FOURTH -> {
                moveToFourth()
            }
            else -> {

            }

        }
    }


    fun handleBack() {
        Log.d("handleBack***", "$currentActive")
        when (currentActive) {
            SECOND -> {
                moveToFirst()
            }
            THIRD -> {
                moveToSecond()
            }
            FOURTH -> {
                moveToThird()
            }
        }
    }

    private fun moveToFirst() {
        Log.d("moveToFirst***", "${stackElement1.state} *** ${stackElement2.state} *** ${stackElement3.state}")
        stackElement1.position = FIRST
        if (stackElement1.state != ElementState.COLLAPSED) {
            stackElement2.visibility = GONE
            stackElement3.visibility = GONE
            stackElement4.visibility = GONE
            translateIn(stackElement1)
        } else {
            stackElement2.position = -1
            stackElement1.state = ElementState.EXPANDED
            currentActive = currentActive?.minus(1)
            translateOut(stackElement2)
        }
    }

    private fun moveToSecond() {
        Log.d("moveToSecond***", "${stackElement1.state} *** ${stackElement2.state} *** ${stackElement3.state}")

        if (stackElement2.state != ElementState.COLLAPSED) {
            stackElement1.state = ElementState.COLLAPSED
            stackElement3.visibility = GONE
            stackElement4.visibility = GONE
            translateIn(stackElement2)
        } else if (stackElement2.state == ElementState.COLLAPSED) {
            stackElement3.position = -1
            stackElement2.state = ElementState.EXPANDED
            currentActive = currentActive?.minus(1)
            translateOut(stackElement3)
        }
    }

    private fun moveToThird() {
        Log.d("moveToThird***", "${stackElement1.state} *** ${stackElement2.state} *** ${stackElement3.state}")

        if (stackElement3.state != ElementState.COLLAPSED) {
            viewQueue.poll().visibility = GO
            stackElement4.visibility = GONE
            translateIn(stackElement3)
        } else if (stackElement3.state == ElementState.COLLAPSED) {
            stackElement4.position = -1
            stackElement3.state = ElementState.EXPANDED
            translateOut(stackElement4)
            currentActive = currentActive?.minus(1)

        }
    }

    private fun moveToFourth() {
        Log.d("moveToFourth***", "${stackElement1.state} *** ${stackElement2.state} *** ${stackElement3.state}")
        if (stackElement4.state != ElementState.COLLAPSED) {
            stackElement4.position = FOURTH
            translateIn(stackElement4)
        }
    }

    private fun translateIn(view: View) {
        view.visibility = VISIBLE
        val animate = TranslateAnimation(
            0F,
            0F,
            view.height.toFloat(),
            0F
        )
        animate.duration = 700
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    private fun translateOut(view: View) {
        view.visibility = GONE
        val animate = TranslateAnimation(
            0F,
            0F,
            0F,
            view.height.toFloat()
        )
        animate.duration = 700
        animate.fillAfter = true
        view.startAnimation(animate)
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
            state = if (position == -1) {
                ElementState.INVISIBLE
            } else {
                ElementState.EXPANDED
            }
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





*/
