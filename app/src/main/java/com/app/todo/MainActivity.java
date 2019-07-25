package com.app.todo;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ActionMode;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private CreateToDo toDo;
    public static RecyclerView to_do_list;
    public static List<ToDo> list;
    public static RelativeLayout new_todo;

    private DbHelper dbHelper;

    public static ActionMode.Callback callback;
    public static ActionMode actionMode;

    public static View.OnLongClickListener longClickListener;

    private int index;

    private CreateToDoAdapter adapter;

    private static LayoutAnimationController controller = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My To-Do List");
        setSupportActionBar(toolbar);

        toDo = new CreateToDo(this);

        list = new ArrayList<>();

        dbHelper = new DbHelper(this, "ToDo", null, 1);

        controller = AnimationUtils.loadLayoutAnimation(this, R.anim.to_do_layout);


        to_do_list = (RecyclerView) findViewById(R.id.to_do_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        manager.setReverseLayout(true);
        to_do_list.setLayoutManager(manager);

        new_todo = (RelativeLayout) findViewById(R.id.new_todo);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               toDo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               toDo.setCancelable(true);
               toDo.show();
            }
        });

       displayToDos();

       callback = new ActionMode.Callback() {
           @Override
           public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("Delete");
                getMenuInflater().inflate(R.menu.menu_main, menu);
               return true;
           }

           @Override
           public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
               return false;
           }

           @Override
           public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
               int i = item.getItemId();
               switch (i){
                   case R.id.action_delete:{
                       adapter.deleteToDo(index);
                   }
               }
               return true;
           }

           @Override
           public void onDestroyActionMode(ActionMode mode) {
               actionMode = null;
           }
       };


       longClickListener = new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               if(actionMode != null){
                   return true;
               }else {

                   index = to_do_list.getChildAdapterPosition(v);

                   actionMode = startActionMode(callback);
               }
               return true;
           }
       };


    }

    public static void animateView(){
        to_do_list.setLayoutAnimation(controller);
        to_do_list.getAdapter().notifyDataSetChanged();
        to_do_list.scheduleLayoutAnimation();
    }


    public void displayToDos(){

            list.clear();

            ArrayList<ToDo> mlist = new ArrayList<>();

            Cursor todos = dbHelper.retrieveTodos();

            todos.moveToFirst();

            while (!todos.isAfterLast()) {
                int icon = todos.getInt(0);
                String title = todos.getString(1);
                String text = todos.getString(2);
                String date = todos.getString(3);
                String time = todos.getString(4);

                mlist.add(new ToDo(icon, title, text, date,time));

                todos.moveToNext();
//                toDoList.addAll(list);

            }

            list.addAll(mlist);

        adapter = new CreateToDoAdapter(this, list);

            if (mlist.isEmpty() || mlist.size() == 0) {
                new_todo.setVisibility(View.VISIBLE);
            } else {
                to_do_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                new_todo.setVisibility(View.INVISIBLE);
                animateView();
            }


    }

    @Override
    protected void onResume() {
        super.onResume();
        displayToDos();
    }
}
