package com.zakynoor.uasppb.listener;

import com.zakynoor.uasppb.model.MakananModel;

import java.util.List;

public interface IDMakananLoadListener {
    void onMakananLoadSuccsess(List<MakananModel> makananModelList);
    void onMakananLoadFailed(String messege);
}
