package com.app.todo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import static com.app.todo.MainActivity.actionMode;
import static com.app.todo.MainActivity.animateView;
import static com.app.todo.MainActivity.callback;
import static com.app.todo.MainActivity.list;
import static com.app.todo.MainActivity.longClickListener;
import static com.app.todo.MainActivity.new_todo;
import static com.app.todo.MainActivity.to_do_list;

public class CreateToDoAdapter extends RecyclerView.Adapter<CreateToDoAdapter.ToDoItem> {
    private Context context;
    private List<ToDo> toDoList;
    private CreateToDo createToDo;
    private DbHelper dbHelper;

    public CreateToDoAdapter(Context context){
        this.context = context;
    }

    public CreateToDoAdapter(Context context, List<ToDo> toDoList) {
        this.context = context;
        this.toDoList = toDoList;

        createToDo = new CreateToDo(context);
        dbHelper = new DbHelper(context, "ToDo", null, 1);

    }

    @NonNull
    @Override
    public CreateToDoAdapter.ToDoItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.to_do_item, null);

        return new ToDoItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateToDoAdapter.ToDoItem holder, final int position) {
        final ToDo toDo = toDoList.get(position);

        final ToDoItem item = (ToDoItem) holder;
        item.title.setText(toDo.getToDoTitle());
        item.icon.setBackgroundColor(toDo.getToDoColor());
        item.period.setText(toDo.getToDoDate());
        item.time.setText(toDo.getToDoTime());
        item.icon.setBackground(new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                if(toDo.getToDoTitle().length() > 0){
                char text = toDo.getToDoTitle().charAt(0);
                    int x = (item.icon.getWidth() / 2) - 10;
                    int y = (item.icon.getHeight() / 2) + 10;
                    Paint p = new Paint();
                    p.setStyle(Paint.Style.FILL_AND_STROKE);
                    p.setColor(toDo.getToDoColor());
                    canvas.drawCircle(item.icon.getWidth() / 2, item.icon.getHeight() / 2, 35, p);

                    Paint pText = new Paint();
                    pText.setColor(Color.WHITE);
                    pText.setTextSize(30);
                    pText.setFakeBoldText(true);
                    canvas.drawText(String.valueOf(text).toUpperCase(), x, y, pText);
                }
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.OPAQUE;
            }
        });
//        item.icon.setBackgroundResource(R.drawable.color_bg);

        item.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               context.startActivity(new Intent(context, ReadToDo.class)
               .putExtra("title", toDo.getToDoTitle())
               .putExtra("content", toDo.getToDoText())
               .putExtra("icon", toDo.getToDoColor())
               .putExtra("date", toDo.getToDoDate())
               .putExtra("time", toDo.getToDoTitle()));

            }
        });

        item.layout.setOnLongClickListener(longClickListener);

    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }


    void deleteToDo(int pos){
        ToDo toDo = toDoList.get(pos);

        if(dbHelper.deleteToDo(toDo.getToDoTitle())){
            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
            toDoList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemMoved(pos, toDoList.size());
            if (list.isEmpty() || list.size() == 0) {
                new_todo.setVisibility(View.VISIBLE);
            } else {
                new_todo.setVisibility(View.INVISIBLE);
            }
            animateView();
            notifyDataSetChanged();
        }else {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }


        actionMode.finish();

    }
    public class ToDoItem extends RecyclerView.ViewHolder {
        public RelativeLayout layout;
        public TextView title;
        public ImageView icon;
        public TextView period;
        public TextView time;

        public ToDoItem(@NonNull View view) {
            super(view);

            layout = (RelativeLayout) view.findViewById(R.id.rlayout);
            title = (TextView) view.findViewById(R.id.title);
            icon = (ImageView) view.findViewById(R.id.icon);
            period = (TextView) view.findViewById(R.id.period);
            time = (TextView) view.findViewById(R.id.time);
        }
    }

}
