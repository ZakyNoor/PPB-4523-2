package com.zakynoor.uasppb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zakynoor.uasppb.R;
import com.zakynoor.uasppb.eventbus.MyUpdateCartEvent;
import com.zakynoor.uasppb.listener.ICartLoadListener;
import com.zakynoor.uasppb.listener.IRecyclerViewClickListener;
import com.zakynoor.uasppb.model.CartModel;
import com.zakynoor.uasppb.model.MakananModel;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyMakananAdapter extends RecyclerView.Adapter<MyMakananAdapter.MyMakananViewHolder> {

    private Context context;
    private List<MakananModel> makananModelList;
    private ICartLoadListener iCartLoadListener;

    public MyMakananAdapter(Context context, List<MakananModel> makananModelList, ICartLoadListener iCartLoadListener) {
        this.context = context;
        this.makananModelList = makananModelList;
        this.iCartLoadListener = iCartLoadListener;
    }

    @NonNull
    @Override
    public MyMakananAdapter.MyMakananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyMakananViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.layout_makanan_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyMakananAdapter.MyMakananViewHolder holder, int position) {
        Glide.with(context)
                .load(makananModelList.get(position).getImage())
                .into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("Rp. ").append(makananModelList.get(position).getPrice()));
        holder.txtName.setText((new StringBuilder().append(makananModelList.get(position).getName())));

        holder.setListener((view, adapterPosition) -> {
            addToCart(makananModelList.get(position));

        });

    }

    private void addToCart(MakananModel makananModel) {
        DatabaseReference userCart = FirebaseDatabase
                .getInstance()
                .getReference("Cart")
                .child("UNIQUE_USER_ID");

        userCart.child(makananModel.getKey())
            .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    CartModel cartModel = snapshot.getValue(CartModel.class);
                    cartModel.setQuantity(cartModel.getQuantity()+1);
                    Map<String,Object> updateData = new HashMap<>();
                    updateData.put("quantity", cartModel.getQuantity());
                    updateData.put("totalPrice", cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

                    userCart.child(makananModel.getKey())
                            .updateChildren(updateData)
                            .addOnSuccessListener(aVoid -> {
                                iCartLoadListener.onCartLoadFailed("Add To Cart Success");
                            })
                            .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));

                }
                else {
                    CartModel cartModel = new CartModel();
                    cartModel.setName(makananModel.getName());
                    cartModel.setImage(makananModel.getImage());
                    cartModel.setKey(makananModel.getKey());
                    cartModel.setPrice(makananModel.getPrice());
                    cartModel.setQuantity(1);
                    cartModel.setTotalPrice(Float.parseFloat(makananModel.getPrice()));

                    userCart.child(makananModel.getKey())
                            .setValue(cartModel)
                            .addOnSuccessListener(aVoid -> {
                                iCartLoadListener.onCartLoadFailed("Add To Cart Success");
                            })
                            .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));


                }
                EventBus.getDefault().postSticky(new MyUpdateCartEvent());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iCartLoadListener.onCartLoadFailed(error.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return makananModelList.size();
    }

    public class MyMakananViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;

        IRecyclerViewClickListener listener;

        public void setListener(IRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        private Unbinder unbinder;
        public MyMakananViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRecylcleClick(v,getAdapterPosition());
        }
    }
}
