package hemantgtx950.com.oberero.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hemantgtx950.com.oberero.R;
import hemantgtx950.com.oberero.Utility.Utils;


/**
 * Created by hemba on 5/7/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<String> categories;
    Context context;

    public CategoryAdapter(ArrayList<String> categories,Context context){
        this.categories = categories;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(context).inflate(R.layout.category_customer_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CategoryHolder){
            ((CategoryHolder) holder).catName.setText(categories.get(position));
            ((CategoryHolder) holder).catImage.setImageResource(Utils.getId(categories.get(position)));
            ((CategoryHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    protected class CategoryHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView catImage;
        TextView catName;
        public CategoryHolder(View itemView) {
            super(itemView);
            catImage=(ImageView)itemView.findViewById(R.id.category_image);
            catName=(TextView)itemView.findViewById(R.id.category_name);
            cardView=(CardView)itemView.findViewById(R.id.category_card_view);
        }
    }
}
