package com.maximbravo.bakeit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.maximbravo.bakeit.BakingUtils.currentStep;
import static com.maximbravo.bakeit.R.id.pictureView;

/**
 * Created by Kids on 6/12/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Recipe> recipes;

    private ItemClickListener mClickListener;
    public RecipeAdapter(Context context, ArrayList<Recipe> recipes){
        this.context = context;
        this.recipes = recipes;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        System.out.println("onCreateViewholder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe main = recipes.get(position);
        holder.title.setText(main.getRecipeName());
        holder.description.setText(main.getRecipeDescription());
        if(!main.getImage().equals("") && main.getImage() != null) {
            Picasso.with(context).load(main.getImage()).into(holder.image);
        }
        System.out.println("onBindViewHolder: " + position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView description;
        public View itemView;
        public ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            setItemView(itemView);
        }
        public void setItemView(View itemView) {
            this.itemView = itemView;
            this.itemView.setOnClickListener(this);
            this.title = (TextView) itemView.findViewById(R.id.recipe_name);
            this.description = (TextView) itemView.findViewById(R.id.recipe_description);
            this.image = (ImageView) itemView.findViewById(R.id.picture);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
