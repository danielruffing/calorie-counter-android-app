/**
* Author(s): Daniel Ruffing, Doug Shook
* */
package com.example.cse438.caloriecounter

import android.app.AlertDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.enter_name.*
import kotlinx.android.synthetic.main.enter_name.view.*

data class Food(var food:String, var calories: Int)

class MainActivity : AppCompatActivity() {

    // List of tasks the user enters
    private var listOfTasks = ArrayList<Food>()

    // Text and List views
    private var listView: ListView? = null

    private var taskNum = 0
    private var totalItems = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup functions
        getInitial()
        dialogView()
    }

    /**
     *  Assigns the list and text view objects with their respective views
     */
    private fun getInitial() {
        listView = foodItems

        // Setting up the adapter using our custom built adapter
        val adapter = TaskListAdapter(this, listOfTasks)
        listView?.adapter = adapter

        adapter.notifyDataSetChanged()
    }

    /**
     * Displays the dialog box asking the user for the user's calorie TDE
     *
     */
    private fun dialogView() {
        // Opens the dialog view asking the user for
        val dialogView = LayoutInflater.from(this).inflate(R.layout.enter_name, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Enter your Calorie TDE")
        val mAlertDialog = mBuilder.show()

        // Sets an onclick listener on the dialog box button
        mAlertDialog.submitName.setOnClickListener {
            val userName = dialogView.name.text.toString()
            // If the string is empty, we do not want to accept that as an input
            if(userName != ""){
                //greeting.text = userName
                initialTDE.text = userName
                mAlertDialog.dismiss()
            }
        }
    }

    /**
     * Handler for adding a new task
     */
    fun addTask(view: View?){
        totalItems++

        val foodToAdd = Food(enterFood.text.toString(), enterCalories.text.toString().toInt())


        //sets text color of Calories Remaining to red if below zero
        if(initialTDE.text.toString().toInt()<1){
            initialTDE.setTextColor(Color.parseColor("#ff0000"))
        }
        if(foodToAdd.food == ""){
            val myToast = Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT)
            myToast.show()
        }
        else{
            /**
             * Creative Portion: Here is the code for the additional functionality that checks for repeat food items,
             * so that identical food items do not create a new row, but they still add the added calories
             */
            for(i in 0 until listOfTasks.size){
                //if the food item is already recorded
                if(foodToAdd.food==listOfTasks[i].food) {
                    //do not add a new row, but add the calories
                    listOfTasks[i].calories += foodToAdd.calories
                    taskNum += foodToAdd.calories
                    caloriesEaten.text = taskNum.toString()
                    initialTDE.text = (initialTDE.text.toString().toInt() - foodToAdd.calories).toString()
                    (listView?.adapter as? TaskListAdapter)?.notifyDataSetChanged()
                    return
                }
            }

            //create a row for a unique item
            listOfTasks.add(foodToAdd)
            taskNum += foodToAdd.calories
            caloriesEaten.text = taskNum.toString()
            initialTDE.text = (initialTDE.text.toString().toInt() - foodToAdd.calories).toString()
            (listView?.adapter as? TaskListAdapter)?.notifyDataSetChanged()
        }
    }
}
