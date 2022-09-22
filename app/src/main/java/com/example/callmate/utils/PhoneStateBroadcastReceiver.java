package com.example.callmate.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.callmate.ui.contacts.ContactsViewModel;

public class PhoneStateBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("on receive", "on receive");
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Log.i("state", "ringing");
        } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Log.i("state", "offhook");
        } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            // code which will run when call ends.
            Log.i("state", "call ends.");
            ContactsViewModel.setIsCallEnded(true);
        }
    }
}