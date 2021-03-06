package com.yourmother.android.worstmessengerever.screens.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.firebase.database.annotations.NotNull;

import java.io.Serializable;

public abstract class BaseFragment extends Fragment implements Serializable {

    public interface Searchable {
        void search(String query);
    }

    public boolean onBackPressed() {
        return false;
    }

    public boolean isOnline(@NotNull Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null && info.isConnected())
            return true;

        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void updateUI() { }
}
