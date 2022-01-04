package com.example.timemangertrans.ui.ShowWei;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShowWeiViewModel extends ViewModel{
    private MutableLiveData<String> mText;

    public ShowWeiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}
