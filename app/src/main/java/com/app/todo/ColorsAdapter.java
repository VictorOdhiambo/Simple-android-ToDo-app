package com.app.todo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static com.app.todo.CreateToDo.ICON_COLOUR;

import java.util.List;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorHolder> {
    private Context context;
    private List<ToDo> toDoList;

    private int indexClicked;
    private boolean btnIsClicked = false;

    public ColorsAdapter(Context context, List<ToDo> toDoList){
        this.context = context;
        this.toDoList = toDoList;
    }
    @NonNull
    @Override
    public ColorsAdapter.ColorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.select_color, null);

        return new ColorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorsAdapter.ColorHolder holder, final int position) {
        final ToDo toDo = toDoList.get(position);

        final ColorHolder colorHolder = (ColorHolder) holder;

        for(int h = 0; h < toDoList.size(); h++){
            if(toDo.isClicked()){
                indexClicked = position;
                colorHolder.img_color.setBackground(new Drawable() {
                    @Override
                    public void draw(@NonNull Canvas canvas) {
                        int x = (colorHolder.img_color.getWidth() / 2) - 14;
                        int y = (colorHolder.img_color.getHeight() / 2) + 7;
                        int x1 = (colorHolder.img_color.getWidth() / 2);
                        int y1= (colorHolder.img_color.getHeight() / 2);
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        paint.setColor(toDo.getToDoColor());
                        canvas.drawCircle(x1, y1, 35, paint);

                        Paint p = new Paint();
//                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setColor(Color.WHITE);
                        p.setTextSize(20);
                        canvas.drawText("OK", x, y, p);
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
            }else{
                colorHolder.img_color.setBackground(new Drawable() {
                    @Override
                    public void draw(@NonNull Canvas canvas) {
                        int x = (colorHolder.img_color.getWidth() / 2);
                        int y = (colorHolder.img_color.getHeight() / 2);
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        paint.setColor(toDo.getToDoColor());
                        canvas.drawCircle(x, y, 35, paint);
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
            }

            break;

        }

        //onclick event
        colorHolder.img_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(indexClicked == position) {
                    colorHolder.img_color.setBackground(new Drawable() {
                        @Override
                        public void draw(@NonNull Canvas canvas) {
                            int x = (colorHolder.img_color.getWidth() / 2) - 14;
                            int y = (colorHolder.img_color.getHeight() / 2) + 7;
                            int x1 = (colorHolder.img_color.getWidth() / 2);
                            int y1= (colorHolder.img_color.getHeight() / 2);
                            Paint paint = new Paint();
                            paint.setStyle(Paint.Style.FILL_AND_STROKE);
                            paint.setColor(toDo.getToDoColor());
                            canvas.drawCircle(x1, y1, 35, paint);

                            Paint p = new Paint();
//                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                            p.setColor(Color.WHITE);
                            p.setTextSize(20);
                            canvas.drawText("OK", x, y, p);
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
                }else {
                    toDoList.get(position).setClicked(true);
                    colorHolder.img_color.setBackground(new Drawable() {
                        @Override
                        public void draw(@NonNull Canvas canvas) {
                            int x = (colorHolder.img_color.getWidth() / 2) - 14;
                            int y = (colorHolder.img_color.getHeight() / 2) + 7;
                            int x1 = (colorHolder.img_color.getWidth() / 2);
                            int y1= (colorHolder.img_color.getHeight() / 2);
                            Paint paint = new Paint();
                            paint.setStyle(Paint.Style.FILL_AND_STROKE);
                            paint.setColor(toDo.getToDoColor());
                            canvas.drawCircle(x1, y1, 35, paint);

                            Paint p = new Paint();
                            p.setColor(Color.WHITE);
                            p.setTextSize(20);
                            canvas.drawText("OK", x, y, p);
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
                    toDoList.get(indexClicked).setClicked(false);
                    notifyDataSetChanged();

                }

                ICON_COLOUR = toDo.getToDoColor();
            }
        });
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public class ColorHolder extends RecyclerView.ViewHolder {
        public ImageView img_color;

        public ColorHolder(@NonNull View v) {
            super(v);

            img_color = (ImageView) v.findViewById(R.id.img_color);
        }
    }
}
