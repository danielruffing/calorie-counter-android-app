/**
 * Custom adapter for the list view displaying a list of tasks
 */
/**
 * Author(s): Daniel Ruffing, Doug Shook
 * */
package com.example.cse438.caloriecounter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TaskListAdapter(private var activity: Activity, private var items: ArrayList<Food>): BaseAdapter() {

    /**
     * Need to override the ViewHolder method
     */
    private class ViewHolder(row: View?){
        var taskName: TextView? = null
        var taskNumber: TextView? = null

        init {
            this.taskName = row?.findViewById(R.id.taskName)
            this.taskNumber = row?.findViewById(R.id.taskNumber)
        }
    }

    /**
     * Displays the information in the row format we want
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.food_item_row, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val task = items[position]
        //viewHolder.taskNumber?.text = Integer.toString(position + 1)
        viewHolder.taskNumber?.text = task.calories.toString()
        viewHolder.taskName?.text = task.food

        return view as View
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

}