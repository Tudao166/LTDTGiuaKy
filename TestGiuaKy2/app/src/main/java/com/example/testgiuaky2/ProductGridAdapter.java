package com.example.testgiuaky2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.testgiuaky2.model.Category;

import java.util.List;

public class ProductGridAdapter extends BaseAdapter {
    private Context context;
    private List<Category> productList;
    private LayoutInflater inflater;

    public ProductGridAdapter(Context context, List<Category> productList) {
        this.context = context;
        this.productList = productList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productList == null ? 0 : productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_last_product, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.image_cate);
            holder.textView = convertView.findViewById(R.id.tvNameCategory);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Lấy sản phẩm tại vị trí `position`
        Category product = productList.get(position);
        holder.textView.setText(product.getName());

        // Load ảnh với Glide
        Glide.with(context)
                .load(product.getImages())
                .into(holder.imageView);

        return convertView;
    }

    public void updateData(List<Category> newList) {
        productList.clear();
        productList.addAll(newList);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
