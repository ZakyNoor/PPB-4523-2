package com.zakynoor.uasppb.listener;

import com.zakynoor.uasppb.model.CartModel;

import java.util.List;

public interface ICartLoadListener {
    void onCartLoadSuccsess(List<CartModel> cartModelList);
    void onCartLoadFailed(String messege);

}
