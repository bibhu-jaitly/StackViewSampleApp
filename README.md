# StackViewSampleApp

#About:

This is a sample app which implements a custom view group. The custom view is implemented to look like a stack,
where views are inflated on the click of the button. There are two custom viewgroups, both extend ConstraintLayout to
achieve the the goal. The main idea for this came from the view implemented in a specific product of a startup.

#Implementation:

StackLayout is the parent custom view group which contains four another custom view group StackElement which also extends Constraint layout
and contains some elements like TextView. It can be customised according to specific needs and can be implemented in exhaustive manner.

This library exposes "handleBack" method to manage the visibility of the elements in the view and animate them accordingly.
The variable "curentActive" defined inside the StackLayout class, which tracks the current
top view of the stack and is accordingly changed while inserting and removing view from the stack.For managing 
the stack of the views I have used ArrayDequeue.

There are methods like insertView and popView which inserts and removes the view at the top of the view stack.

Enum class is defined for managing the state of the "StackElement" which has mainly two states EXPANDED and COLLAPSED. If the
view is not inside the view stack then by default the state is null. Once the view enters the view stack its state changes to EXPANDED.
If any other view comes on top of it then its value changes to COLLAPSED. 

In EXPANDED state the bottom button to proceed to next view is visible and it becomes invisible when the parent view is in COLLAPSED state.
Views are animated while entering and leaving the stack.

There are a lot of possibilities with this custom view. This custom view is the basic implementation of a use case. It can be 
modified to make the internal elements of the StackElement dynamic and the outer custom view can be modified to hold multiple StackElement or any Generic 
custom view groups or custom view for specific use case.

#Assumptions:

While creating this custom views I have taken a very specific use case from a particular Indian product company. I have limited 
the number of child StackElement of StackLayout as four. I have kept most of the view empty as it can vary depending upon use case.
The views of StackElement visible while the StackElement is in COLLAPSED state are also same for every view, which can also vary 
and can be modified accordingly. 


 
