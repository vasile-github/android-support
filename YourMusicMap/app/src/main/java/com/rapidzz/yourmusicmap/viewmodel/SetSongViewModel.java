package com.rapidzz.yourmusicmap.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.rapidzz.mymusicmap.datamodel.model.fan.Song;
import com.rapidzz.mymusicmap.datamodel.model.fan.User;
import com.rapidzz.mymusicmap.datamodel.model.responses.ApiErrorResponse;
import com.rapidzz.mymusicmap.datamodel.source.UserDataSource;
import com.rapidzz.mymusicmap.datamodel.source.UserRepository;
import com.rapidzz.yourmusicmap.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

public class SetSongViewModel extends BaseAndroidViewModel {


    private UserRepository mUserRepository;
    public MutableLiveData<Song> mSongMutableLiveData = new MutableLiveData();

    public SetSongViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
    }

    public void doSaveSong(String title, String path, String id, String lat, String lng){

        if(title.trim().isEmpty() || path.isEmpty() || lat.isEmpty() || lng.isEmpty())
            showSnackbarMessage(getString(R.string.error_message_enter_missing_detail));
        else{
            showProgressBar(true);
            mUserRepository.saveSong(title, path, id, lat, lng, new UserDataSource.SaveSongCallback() {
                @Override
                public void onSaveSong(@NotNull Song song) {
                    Log.e("Success",song.toString());
                    showProgressBar(false);
                    mSongMutableLiveData.setValue(song);
                }

                @Override
                public void onPayloadError(@NotNull ApiErrorResponse error) {
                    Log.e("Success",error.toString());
                    showProgressBar(false);
                    showSnackbarMessage(error.getMessage());
                }
            });
        }
    }

    public void getPlacesFromApi(String url){
            showProgressBar(true);
            mUserRepository.getplace(url, new UserDataSource.PlaceCallback() {
                @Override
                public void onPlace(@NotNull Song song) {
                    Log.e("Success",song.toString());
                    showProgressBar(false);
                    mSongMutableLiveData.setValue(song);
                }

                @Override
                public void onPayloadError(@NotNull ApiErrorResponse error) {
                    Log.e("Success",error.toString());
                    showProgressBar(false);
                    showSnackbarMessage(error.getMessage());
                }
            });
    }
}
