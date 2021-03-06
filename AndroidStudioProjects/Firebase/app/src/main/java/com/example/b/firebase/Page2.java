package com.example.b.firebase;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


public class Page2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference myref;
    String value;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dresh);
                Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");

            //p.getName().indexOf(value);
            Toast.makeText(Page2.this, value, Toast.LENGTH_SHORT).show();
        }
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Page2.this,2);
        //  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Dresh.this);


        recyclerView.setLayoutManager(layoutManager);
        //myref= FirebaseDatabase.getInstance().getReference().child("/blog");
        myref= FirebaseDatabase.getInstance().getReference().child("Database4");
        //   myref.child("Category").child("Clothing").getKey();
        query=        myref.orderByChild("id").equalTo(value);

        //   myref.child("School").orderByValue().equalTo("CBSE","type");

        // query =  myref.child("Database/Category/Schools").orderByValue();

//        myref.getParent().getKey();
//        Log.e("Data",query.toString());
        Log.e("Data",myref.toString());

        Person per = new Person();



        final FirebaseRecyclerAdapter<Person,BlogViewHolder> recyclerAdapter=new FirebaseRecyclerAdapter<Person,BlogViewHolder>(
                Person.class,
                R.layout.individual_row,
                BlogViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Person model, int position) {



                viewHolder.setName(model.getName());
                viewHolder.setImage(model.getImage());
            }


        };
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.clearOnScrollListeners();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(Page2.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Person s = recyclerAdapter.getItem(position);

                Intent i = new Intent(getApplicationContext(), Page3.class);

                i.putExtra("key",s.getId());
                startActivity(i);

                //   recyclerAdapter.getItem(position);


                //  Toast.makeText(Dresh.this,s.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));
    }



    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView textView_title;
        TextView textView_decription;
        ImageView imageView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            textView_title = (TextView)itemView.findViewById(R.id.title);
            textView_decription = (TextView) itemView.findViewById(R.id.description);
            imageView=(ImageView)itemView.findViewById(R.id.image);
        }

        public void setImage(String image)
        {
            Picasso.with(mView.getContext())
                    .load(image)
                    .into(imageView);
        }
        public void setName(String name){
            textView_decription.setText(name);
        }




    }


}