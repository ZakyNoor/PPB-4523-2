package com.zakynoor.uasppb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;
import com.zakynoor.uasppb.adapter.MyMakananAdapter;
import com.zakynoor.uasppb.eventbus.MyUpdateCartEvent;
import com.zakynoor.uasppb.listener.ICartLoadListener;
import com.zakynoor.uasppb.listener.IDMakananLoadListener;
import com.zakynoor.uasppb.model.CartModel;
import com.zakynoor.uasppb.model.MakananModel;
import com.zakynoor.uasppb.utils.SpaceItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IDMakananLoadListener, ICartLoadListener {
    @BindView(R.id.recycle_makanan)
    RecyclerView recyclerMakanan;
    @BindView(R.id.mainlayout)
    RelativeLayout mainlayout;
    @BindView(R.id.badge)
    NotificationBadge badge;
    @BindView(R.id.btnCart)
    FrameLayout btnCart;

    IDMakananLoadListener makananLoadListener;
    ICartLoadListener cartLoadListener;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if(EventBus.getDefault().hasSubscriberForEvent(MyUpdateCartEvent.class))
            EventBus.getDefault().removeStickyEvent(MyUpdateCartEvent.class);
        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onUpdateCart(MyUpdateCartEvent event)
    {
        countCartItem();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadMakananFromFirebase();
        countCartItem();
    }

    private void loadMakananFromFirebase() {
        List<MakananModel> makananModels =new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("Makanan")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for(DataSnapshot makananSnapshot:snapshot.getChildren()){
                                MakananModel makananModel = makananSnapshot.getValue(MakananModel.class);
                                makananModel.setKey(makananSnapshot.getKey());
                                makananModels.add(makananModel);
                            }
                            makananLoadListener.onMakananLoadSuccsess(makananModels);
                        }else
                            makananLoadListener.onMakananLoadFailed("Makanan Tidak Tersedia");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void init() {
        ButterKnife.bind(this);

        makananLoadListener=this;
        cartLoadListener=this;
        ///GridLayoutManager gridLayoutManager= new GridLayoutManager(this, 2);
        ///recyclerMakanan.setLayoutManager(gridLayoutManager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerMakanan.setLayoutManager(layoutManager);
        recyclerMakanan.addItemDecoration(new SpaceItemDecoration());

        btnCart.setOnClickListener(v -> startActivity(new Intent(this,CartActivity.class)));
    }


    @Override
    public void onMakananLoadSuccsess(List<MakananModel> makananModelList) {
        MyMakananAdapter adapter = new MyMakananAdapter(this,makananModelList,cartLoadListener);
        recyclerMakanan.setAdapter(adapter);

    }

    @Override
    public void onMakananLoadFailed(String messege) {
        Snackbar.make(mainlayout,messege,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCartLoadSuccsess(List<CartModel> cartModelList) {

        int cartSum = 0;
        for(CartModel cartModel: cartModelList)
            cartSum += cartModel.getQuantity();
        badge.setNumber(cartSum);
    }

    @Override
    public void onCartLoadFailed(String messege) {
        Snackbar.make(mainlayout,messege,Snackbar.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        countCartItem();
    }

    private void countCartItem() {
        List<CartModel> cartModels = new ArrayList<>();
        FirebaseDatabase
                .getInstance().getReference("Cart")
                .child("UNIQUE_USER_ID")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot cartSnapshot:snapshot.getChildren()){
                            CartModel cartModel = cartSnapshot.getValue(CartModel.class);
                            cartModel.setKey(cartSnapshot.getKey());
                            cartModels.add(cartModel);
                        }
                        cartLoadListener.onCartLoadSuccsess(cartModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        cartLoadListener.onCartLoadFailed(error.getMessage());

                    }
                });

    }
}