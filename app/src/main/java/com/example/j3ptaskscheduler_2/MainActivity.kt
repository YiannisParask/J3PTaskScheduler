package com.example.j3ptaskscheduler_2

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupWindow
import com.example.j3ptaskscheduler_2.DB.DatabaseHandler
import com.example.j3ptaskscheduler_2.adapter.TaskRecyclerAdapter
import com.example.j3ptaskscheduler_2.models.Tasks
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private var taskRecyclerAdapter: TaskRecyclerAdapter? = null
    private var fab: FloatingActionButton? = null
    private var recyclerView: RecyclerView? = null
    private var dbHandler: DatabaseHandler? = null
    private var listTasks: List<Tasks> = ArrayList()
    private var linearLayoutManager: LinearLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.title = "J3P Task Scheduler"
        initViews()
        initOperations()
        //initDB()

        motivatebtn.setOnClickListener {
            val list = listOf("pls remember that wen u feel scare or frightn never forget ttimes wen u feeled happy.\n\n wen day is dark alway rember happy day.",
                "Don’t stop when you’re tired. Stop when you’re done.",
                "Do something today that your future self will thank you for.",
                "Sometimes later becomes never. Do it now.",
                "Dream it. Wish it. Do it.",
                "The harder you work for something, the greater you’ll feel when you achieve it.",
                "It’s going to be hard, but hard does not mean impossible.",
                "The key to success is to focus on goals, not obstacles.",
                "The way get started is to quit talking and begin doing.",
                "You learn more from failure than from success. Don’t let it stop you. Failure builds character.",
                "If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you.",
                "No valid plans for the future can be made by those who have no capacity for living now.",
                "You don't look out there for God, something in the sky, you look in you.",
                "Knowing is not enough; we must apply. Wishing is not enough; we must do.",
                "We generate fears while we sit. We overcome them by action.",
                "You don't look out there for God, something in the sky, you look in you.",
                "The only limit to our realization of tomorrow will be our doubts of today.",
                "Do what you can with all you have, wherever you are.",
                "You are never too old to set another goal or to dream a new dream.",
                "No amount of anxiety makes any difference to anything that is going to happen.",
                "Reading is to the mind, as exercise is to the body.",
                "The future belongs to the competent. Get good, get better, be the best!",
                "I think goals should never be easy, they should force you to work, even if they are uncomfortable at the time.",
                "You don’t have to be great to start, but you have to start to be great.",
                "People often say that motivation doesn’t last. Well, neither does bathing – that’s why we recommend it daily",
                "Work until your bank account looks like a phone number.",
                "Think like a proton. Always positive.",
                "Always remember that you are unique – just like everybody else.",
                "Dreams are like rainbows. Only idiots chase them.",
                "Trying is the first step toward failure.",
                "Discipline is doing what needs to be done, even if you don’t want to.",
                "Don’t quit. You’re already in pain. You’re already hurt. Get a reward from it.",
                "Do what you have to do until you can do what you want to do.",
                "Be stubborn about your goals and flexible about your methods.",
                "Work until your idols become your rivals."
                )

            val quote = list.shuffled()[0]

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Your Motivational Quote")
            builder.setMessage(quote)
            builder.setNegativeButton("Thank you") { _: DialogInterface, _: Int -> }
            builder.show()
        }


    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        listTasks = (dbHandler as DatabaseHandler).task()
        taskRecyclerAdapter = TaskRecyclerAdapter(tasksList = listTasks, context = applicationContext)
        (recyclerView as RecyclerView).adapter = taskRecyclerAdapter
    }

    private fun initViews() {
        fab = findViewById(R.id.fab)
        recyclerView = findViewById(R.id.recycler_view)
        taskRecyclerAdapter = TaskRecyclerAdapter(tasksList = listTasks, context = applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        (recyclerView as RecyclerView).layoutManager = this.linearLayoutManager
    }

    private fun initOperations() {
        fab?.setOnClickListener {
            val i = Intent(applicationContext, AddOrEditActivity::class.java)
            i.putExtra("Mode", "A")
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_delete) {
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click 'YES' Delete All Tasks")
                .setPositiveButton("YES") { dialog, _ ->
                    dbHandler!!.deleteAllTasks()
                    initDB()
                    dialog.dismiss()
                }
                .setNegativeButton("NO") { dialog, _ ->
                    dialog.dismiss()
                }
            dialog.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        initDB()
    }


}