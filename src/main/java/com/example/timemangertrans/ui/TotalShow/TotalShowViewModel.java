package com.example.timemangertrans.ui.TotalShow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TotalShowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TotalShowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
