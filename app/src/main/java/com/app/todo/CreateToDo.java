package com.app.todo;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.app.todo.MainActivity.animateView;
import static com.app.todo.MainActivity.list;
import static com.app.todo.MainActivity.new_todo;
import static com.app.todo.MainActivity.to_do_list;

public class CreateToDo extends Dialog {
    private TextView dialogText;
    private EditText title;
    private EditText to_do;
    private TextView create;
    private TextView cancel;
    private RecyclerView colors;

    public static int ICON_COLOUR;

    private List<ToDo> toDoList;

    private DbHelper dbHelper;

    CreateToDoAdapter adapter;

    private String date, time;

    private String[] color = {"FFDD2C00", "FFC51162", "FF64DD17", "FF64DD17",
            "FF00C853", "FFFF6D00", "FF00B8D4", "FFAA00FF"};

    public CreateToDo(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_to_do);

        toDoList = new ArrayList<>();
        adapter = new CreateToDoAdapter(getContext());

        dbHelper = new DbHelper(getContext(), "ToDo", null, 1);

        dialogText = (TextView) findViewById(R.id.txt);
        title = (EditText) findViewById(R.id.to_do_title);
        colors = (RecyclerView) findViewById(R.id.colors);
        colors.setLayoutManager(new GridLayoutManager(getOwnerActivity(), 3));
        create = (TextView) findViewById(R.id.create);
        cancel = (TextView) findViewById(R.id.cancel);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH : mm");
        date = dateFormat.format(new Date());
        time = timeFormat.format(new Date());



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTitle = title.getText().toString().trim();
                ArrayList todo = new ArrayList();

                if (TextUtils.isEmpty(mTitle)) {
                    if (dbHelper.addToDo(ICON_COLOUR, "Untitled", "", date, time)) {
                        Toast.makeText(getContext(), "To-do successfully saved ", Toast.LENGTH_SHORT).show();

                        todo.add(new ToDo(ICON_COLOUR, "Untitled", "", date, time));
                        list.addAll(todo);


                        CreateToDoAdapter adapter1 = new CreateToDoAdapter(getContext(), list);

                        if (list.isEmpty() || list.size() == 0) {
                            new_todo.setVisibility(View.VISIBLE);
                        } else {
                            to_do_list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            new_todo.setVisibility(View.INVISIBLE);
                        }
                        to_do_list.setAdapter(adapter1);
                        animateView();
                        dismiss();

                    } else {
                        Toast.makeText(getContext(), "Failed to create", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (dbHelper.addToDo(ICON_COLOUR, mTitle, "", date, time)) {
                        Toast.makeText(getContext(), "To-do successfully saved", Toast.LENGTH_SHORT).show();

                        todo.add(new ToDo(ICON_COLOUR, mTitle, "", date, time));
                        list.addAll(todo);

                        CreateToDoAdapter adapter1 = new CreateToDoAdapter(getContext(), list);

                        if (list.isEmpty() || list.size() == 0) {
                            new_todo.setVisibility(View.VISIBLE);
                        } else {
                            to_do_list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            new_todo.setVisibility(View.INVISIBLE);
                        }
                        to_do_list.setAdapter(adapter1);
                        animateView();
                        dismiss();

                    } else {
                        Toast.makeText(getContext(), "Failed to create", Toast.LENGTH_SHORT).show();
                    }


                }
                title.setText("");
            }


        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                title.setText("");
            }
        });
        createColorChoice();
    }

    private void createColorChoice(){

        toDoList.add(new ToDo(Color.MAGENTA, false));
        toDoList.add(new ToDo(Color.CYAN, false));
        toDoList.add(new ToDo(Color.BLUE, false));
        toDoList.add(new ToDo(Color.GREEN, false));
        toDoList.add(new ToDo(Color.RED, false));
        toDoList.add(new ToDo(Color.DKGRAY, false));


        colors.setAdapter(new ColorsAdapter(getContext(), toDoList));

    }

}
