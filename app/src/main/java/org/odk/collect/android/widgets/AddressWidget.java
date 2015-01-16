/*
 * Copyright (C) 2009 University of Washington
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.odk.collect.android.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.StringData;
import org.javarosa.form.api.FormEntryPrompt;
import org.odk.collect.android.R;
import org.odk.collect.android.adapters.KeyValueHashMapAdapter;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.model.Commune;
import org.odk.collect.android.model.District;
import org.odk.collect.android.model.ItemData;
import org.odk.collect.android.model.Province;
import org.odk.collect.android.model.Village;
import org.odk.collect.android.utilities.PropertiesUtils;


import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * SpinnerWidget handles select-one fields. Instead of a list of buttons it uses a spinner, wherein
 * the user clicks a button and the choices pop up in a dialogue box. The goal is to be more
 * compact. If images, audio, or video are specified in the select answers they are ignored.
 *
 * @author Jeff Beorse (jeff@beorse.net)
 */
public class AddressWidget extends QuestionWidget implements AdapterView.OnItemSelectedListener {

    LinearLayout layout;
    Spinner spinnerProvince;
    Spinner spinnerDistrict;
    Spinner spinnerVillage;
    Spinner spinnerCommune;
    private final String ADDRESS_SPLITER = " - ";
    private final String PROVINCE_MODE = "province";
    private final String DISTRICT_MODE = "district";
    private final String COMMUNE_MODE = "commune";
    private final String VILLAGE_MODE = "village";
    private boolean isNeedToRefrest = true;
    
    private final String lblChoose;

    private static final int BROWN = 0xFF936931;

    public AddressWidget(Context context, FormEntryPrompt prompt) {
        super(context, prompt);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = (LinearLayout) layoutInflater.inflate(R.layout.address_layout, this, false);
        addView(layout);
        lblChoose = getResources().getString(R.string.lblChoose);
        spinnerProvince = (Spinner) this.findViewById(R.id.address_province);
        spinnerDistrict = (Spinner) this.findViewById(R.id.address_district);
        spinnerCommune = (Spinner) this.findViewById(R.id.address_commune);
        spinnerVillage = (Spinner) this.findViewById(R.id.address_village);
        spinnerProvince.setOnItemSelectedListener(this);
        spinnerDistrict.setOnItemSelectedListener(this);
        spinnerCommune.setOnItemSelectedListener(this);
        spinnerVillage.setOnItemSelectedListener(this);
        setSpinnerAdapter(spinnerProvince, PROVINCE_MODE);
        // Fill in previous answer
        if (prompt.getAnswerValue() != null) {
            String[] address = prompt.getAnswerText().split(ADDRESS_SPLITER);
            setSpinnerSelected(spinnerProvince, address[0].trim());
            setSpinnerAdapter(spinnerDistrict, DISTRICT_MODE);
            setSpinnerSelected(spinnerDistrict, address[1].trim());
            setSpinnerAdapter(spinnerCommune, COMMUNE_MODE);
            setSpinnerSelected(spinnerCommune, address[2].trim());
            setSpinnerAdapter(spinnerVillage, VILLAGE_MODE);
            setSpinnerSelected(spinnerVillage, address[3].trim());
            isNeedToRefrest = false;
        }

    }

    private void setSpinnerSelected(Spinner spinner, String selectedName) {
        int items = spinner.getAdapter().getCount();
        for (int i = 0; i < items; i++) {
            ItemData viewItem = (ItemData) spinner.getAdapter().getItem(i);
            /*if (viewItem.getId() == Integer.parseInt(selectedCode)) {
                spinner.setSelection(i);
                break;
            }*/
            if (viewItem.getName().equalsIgnoreCase(selectedName)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.address_province) {
            setSpinnerAdapter(spinnerDistrict, DISTRICT_MODE);
        } else if (spinner.getId() == R.id.address_district) {
            setSpinnerAdapter(spinnerCommune, COMMUNE_MODE);
        } else if (spinner.getId() == R.id.address_commune) {
            setSpinnerAdapter(spinnerVillage, VILLAGE_MODE);
        } else if (spinner.getId() == R.id.address_village) {
            isNeedToRefrest = true;
            if (getAnswer() != null) {
                Collect.getInstance().getActivityLogger()
                        .logInstanceAction(this, "answerTextChanged", getAnswer().toString(), getPrompt().getIndex());
            }
            try{
                String villageID = String.valueOf(id);

                if (villageID.length()<=7){
                    villageID = "0"+  villageID;
                }
                PropertiesUtils.get_villeCode().setText(villageID);
            } catch (Exception e){
                PropertiesUtils.get_villeCode().setText("0");
            }

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    private void setSpinnerAdapter(Spinner sp, String mode) {
        if (!isNeedToRefrest) {
            return;
        }
        //List<String> labels = new ArrayList<String>();
        HashMap<String, ItemData> labels = new HashMap<String, ItemData>();
        labels.put(lblChoose, new ItemData(0, lblChoose));
        if (mode.equals(PROVINCE_MODE)) {
            List<Province> provinceList = new Province(getContext()).getListProvinces();
            for (Province p : provinceList) {
                labels.put(p.getProvinceKh(), new ItemData(p.getProCode(), p.getProvinceKh()));
            }
        } else if (mode.equals(DISTRICT_MODE)) {
            ItemData itemData = (ItemData) spinnerProvince.getSelectedItem();
            List<District> districtList = null;
            try {
                districtList = new District(getContext()).getByProvinceId(itemData.getId());
            } catch (ParseException pe) {
                Log.d(DISTRICT_MODE, pe.getMessage().toString());
            }

            for (District d : districtList)
                labels.put(d.getdNameKh(), new ItemData(d.getdCode(), d.getdNameKh()));
        } else if (mode.equals(COMMUNE_MODE)) {
            ItemData itemData = (ItemData) spinnerDistrict.getSelectedItem();
            List<Commune> communeList = null;
            try {
                communeList = new Commune(getContext()).getByDistrictId(itemData.getId());
            } catch (ParseException pe) {
                Log.d(COMMUNE_MODE, pe.getMessage().toString());
            }

            for (Commune c : communeList)
                labels.put(c.getcNameKh(), new ItemData(c.getcCode(), c.getcNameKh()));
        } else if (mode.equals(VILLAGE_MODE)) {
            ItemData itemData = (ItemData) spinnerCommune.getSelectedItem();
            List<Village> villageList = null;
            try {
                villageList = new Village(getContext()).getByCommuneId(itemData.getId());
            } catch (ParseException pe) {
                Log.d(VILLAGE_MODE, pe.getMessage().toString());
            }
            for (Village v : villageList)
                labels.put(v.getvNameKh(), new ItemData(v.getvCode(), v.getvNameKh()));
        }
        Map<String, ItemData> map = new TreeMap<String, ItemData>(labels);
        // Creating adapter for spinner
        KeyValueHashMapAdapter dataAdapter = new KeyValueHashMapAdapter(getContext(), android.R.layout.simple_spinner_item, map);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp.setAdapter(dataAdapter);
        int spinnerPosition = dataAdapter.getPositionFromKey(lblChoose);
        sp.setSelection(spinnerPosition);
    }


    @Override
    public IAnswerData getAnswer() {
        clearFocus();
        ItemData villageData = (ItemData) spinnerVillage.getSelectedItem();
        boolean isNotSelected = villageData.getName().equalsIgnoreCase(lblChoose);
        if (isNotSelected) {
            return null;
        } else {
            StringBuilder answer = new StringBuilder();
            ItemData provinceData = (ItemData) spinnerProvince.getSelectedItem();
            ItemData communeData = (ItemData) spinnerCommune.getSelectedItem();
            ItemData districtData = (ItemData) spinnerDistrict.getSelectedItem();
            PropertiesUtils.setAddressProvinceCode(String.valueOf(provinceData.getId()));
            PropertiesUtils.setAddressDistrictCode(String.valueOf(districtData.getId()));
            PropertiesUtils.setAddressCommuneCode(String.valueOf(communeData.getId()));
            PropertiesUtils.setAddressVillageCode(String.valueOf(villageData.getId()));
            answer.append(provinceData.getName() + ADDRESS_SPLITER);
            answer.append(districtData.getName() + ADDRESS_SPLITER);
            answer.append(communeData.getName() + ADDRESS_SPLITER);
            answer.append(villageData.getName());
            return new StringData(answer.toString());
        }
    }


    @Override
    public void clearAnswer() {
        // It seems that spinners cannot return a null answer. This resets the answer
        // to its original value, but it is not null.

    }


    @Override
    public void setFocus(Context context) {
        // Hide the soft keyboard if it's showing.
        InputMethodManager inputManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getWindowToken(), 0);

    }

    // Defines how to display the select answers
    private class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        String[] items = new String[]{};
        int textUnit;
        float textSize;

        public SpinnerAdapter(final Context context, final int textViewResourceId,
                              final String[] objects, int textUnit, float textSize) {
            super(context, textViewResourceId, objects);
            this.items = objects;
            this.context = context;
            this.textUnit = textUnit;
            this.textSize = textSize;
        }


        @Override
        // Defines the text view parameters for the drop down list entries
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            return convertView;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setTextSize(textUnit, textSize);
            tv.setTextColor(Color.BLACK);
            tv.setTypeface(null, Typeface.BOLD);
            if (position == items.length - 1) {
                tv.setTextColor(BROWN);
                tv.setTypeface(null, Typeface.NORMAL);
            }
            return convertView;
        }

    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
    }

    @Override
    public void cancelLongPress() {
        super.cancelLongPress();
    }
}