package ru.nukolay.stupnikov.animelist.util

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPosition(position: Int, targetViewId: Int = -1): Matcher<View?>? {
        return atPositionOnView(position, targetViewId)
    }

    private fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            var resources: Resources? = null
            var childView: View? = null

            override fun describeTo(description: Description?) {
                var idDescription = recyclerViewId.toString()
                if (resources != null) {
                    idDescription = try {
                        resources!!.getResourceName(recyclerViewId)
                    } catch (var4: Resources.NotFoundException) {
                        String.format("%s (resource name not found)",
                                Integer.valueOf(recyclerViewId))
                    }
                }
                description!!.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View?): Boolean {
                resources = view!!.resources
                if (childView == null) {
                    val recyclerView = view.rootView.findViewById(recyclerViewId) as RecyclerView
                    childView = if (recyclerView.id == recyclerViewId) {
                        recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                    } else {
                        return false
                    }
                }
                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView: View = childView!!.findViewById(targetViewId)
                    view === targetView
                }
            }
        }
    }
}