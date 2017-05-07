package hemantgtx950.com.oberero.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;



import java.util.ArrayList;

import hemantgtx950.com.oberero.Adapters.CategoryAdapter;
import hemantgtx950.com.oberero.R;


public class CategoryActivity extends AppCompatActivity {
    private RecyclerView categoryRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryRecyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(CategoryActivity.this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        ArrayList<String> st=new ArrayList<>();
        st.add("Plumber");
        st.add("Electrician");
        st.add("Carpenter");
        categoryRecyclerView.setAdapter(new CategoryAdapter(st,CategoryActivity.this));

    }
}
