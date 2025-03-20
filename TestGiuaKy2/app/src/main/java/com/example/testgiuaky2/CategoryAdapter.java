package com.example.testgiuaky2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testgiuaky2.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.tvCategoryName.setText(category.getName());

        Glide.with(context)
                .load(category.getImages())
                .placeholder(R.drawable.ic_1) // Ảnh mặc định khi tải
                .error(R.drawable.ic_2) // Ảnh khi lỗi
                .into(holder.ivCategoryImage);

        holder.itemView.setOnClickListener(v ->
                Toast.makeText(context, "Bạn đã chọn Category: " + category.getName(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return (categoryList != null) ? categoryList.size() : 0;
    }

    public void updateData(List<Category> newCategories) {
        this.categoryList = newCategories;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCategoryImage;
        private TextView tvCategoryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategoryImage = itemView.findViewById(R.id.image_cate);
            tvCategoryName = itemView.findViewById(R.id.tvNameCategory);
        }
    }
}