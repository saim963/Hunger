package com.example.hunger.listener;

import com.example.hunger.model.CartModel;
import com.example.hunger.model.DrinkModel;

import java.util.List;

public interface ICartLoadListener {

    void onCartLoadSuccess(List<CartModel> cartModelList);
    void onCartLoadFailed(String message);

}
