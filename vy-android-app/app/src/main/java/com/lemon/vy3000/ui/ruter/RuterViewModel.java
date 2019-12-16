package com.lemon.vy3000.ui.ruter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RuterViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RuterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ruter fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}