package com.example.android.myappimgs.prefs;


/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

import com.example.android.myappimgs.MainActivity;
import com.example.android.myappimgs.R;

import java.util.Set;
import java.util.StringTokenizer;

// COMPLETED (1) Implement OnPreferenceChangeListener
public class SettingsFragment extends PreferenceFragmentCompat implements
        OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer


      // getPreferenceManager().setSharedPreferencesName(String.valueOf(R.string.PREFS_NAME));


        addPreferencesFromResource(R.xml.pref_visualizer);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences (getActivity().getApplicationContext());

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences (getActivity().getApplicationContext());//getActivity().getApplicationContext().getSharedPreferences(String.valueOf(R.string.PREFS_NAME), 0);
       // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String indexes = settings.getString(String.valueOf(R.string.PREFS_Arr_inds),"");
        Set<String> vals=settings.getStringSet(String.valueOf(R.string.PREFS_Arr_vals), null);
        StringTokenizer st = new StringTokenizer(indexes, "@#");
        int size=0;
        if(st.hasMoreTokens()){
        size=Integer.parseInt(st.nextToken());}
        String[] savedList = new String[size];
        String[] savedListvals = new String[size];
        Log.d("tagggg","ssaa"+ vals+"cc" + indexes);
        for (int i = 0; i < size; i++) {
            savedList[i] = st.nextToken();
            savedListvals[i]= st.nextToken();
        }



        ListPreference listPreference = (ListPreference) findPreference("CityList");
        listPreference.setEntryValues(savedList);
        listPreference.setEntries(savedListvals);
        // Go through all of the preferences, and set up their preference summary.
        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            // You don't need to set up preference summaries for checkbox preferences because
            // they are already set up in xml using summaryOff and summary On
            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
            }

        }

        // COMPLETED (3) Add the OnPreferenceChangeListener specifically to the EditTextPreference
        // Add the preference listener which checks that the size is correct to the size preference
        Preference preference = findPreference(getString(R.string.pref_size_key));
        preference.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Figure out which preference was changed
        Preference preference = findPreference(key);
        if (null != preference) {
            // Updates the summary for the preference
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
            if(preference instanceof EditTextPreference){

//                if (key.equals(getString(R.string.pref_size_key))) {
//                    float minSize = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_size_key), "1.0"));
//                    mVisualizerView.setMinSizeScale(minSize);
//                }
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences (getActivity().getApplicationContext());
                EditTextPreference mypreferencepass= (EditTextPreference)findPreference(getString(R.string.Password_key));

                EditTextPreference mypreferenceuser= (EditTextPreference)findPreference(getString(R.string.username_key));
                String user= mypreferenceuser.getText();
                String pass= mypreferencepass.getText();////selected=sharedPreferences.getString(preference.getKey(), "");

                SharedPreferences.Editor editor = settings.edit();
                editor.putString(String.valueOf(R.string.PREFS_user),user);
                editor.putString(String.valueOf(R.string.PREFS_pass),pass);
               // editor.putString(String.valueOf(R.string.PREFS_Arr_inds),str.toString());

                editor.commit();

                Log.d("tagggg34","ssaa"+ user+"cc" +pass);

                // int index = mylistpreference.findIndexOfValue(selected)
            }
            if(preference instanceof ListPreference){
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences (getActivity().getApplicationContext());
                ListPreference mylistpreference= (ListPreference)preference;
                String selected=sharedPreferences.getString(preference.getKey(), "");
                int index = mylistpreference.findIndexOfValue(selected);  // <- selected taken from your code above
                //  String entry = mylistpreference.getEntries()[index];// getPreferenceScreen().findPreference(key);
                String entry = (String) mylistpreference.getEntryValues()[index];
                String toput1=(String) mylistpreference.getEntries()[index];
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(String.valueOf(R.string.PREFS_final_ind),entry);
                // editor.putString(String.valueOf(R.string.PREFS_Arr_inds),str.toString());
                editor.putString(String.valueOf(R.string.PREFS_final_tripname),toput1);
                editor.commit();

                Log.d("tagggg3","ssaa"+ index+"cc" );

                // int index = mylistpreference.findIndexOfValue(selected)
            }
        }
    }

    /**
     * Updates the summary for the preference
     *
     * @param preference The preference to be updated
     * @param value      The value that the preference was updated to
     */
    private void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            // For list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                // Set the summary to that label
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else if (preference instanceof EditTextPreference) {
            // For EditTextPreferences, set the summary to the value's simple string representation.
            preference.setSummary(value);
        }
    }

    // COMPLETED (2) Override onPreferenceChange. This method should try to convert the new preference value
    // to a float; if it cannot, show a helpful error message and return false. If it can be converted
    // to a float check that that float is between 0 (exclusive) and 3 (inclusive). If it isn't, show
    // an error message and return false. If it is a valid number, return true.

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // In this context, we're using the onPreferenceChange listener for checking whether the
        // size setting was set to a valid value.

        Toast error = Toast.makeText(getContext(), "Please select a number between 0.1 and 3", Toast.LENGTH_SHORT);

        // Double check that the preference is the size preference
        String sizeKey = getString(R.string.pref_size_key);
        if (preference.getKey().equals(sizeKey)) {
            String stringSize = (String) newValue;
            try {
                float size = Float.parseFloat(stringSize);
                // If the number is outside of the acceptable range, show an error.
                if (size > 3 || size <= 0) {
                    error.show();
                    return false;
                }
            } catch (NumberFormatException nfe) {
                // If whatever the user entered can't be parsed to a number, show an error
                error.show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }



}