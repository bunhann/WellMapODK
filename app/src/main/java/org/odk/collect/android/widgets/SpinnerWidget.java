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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectOneData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.form.api.FormEntryPrompt;
import org.javarosa.xpath.expr.XPathFuncExpr;
import org.odk.collect.android.R;
import org.odk.collect.android.activities.FormEntryActivity;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.external.ExternalDataUtil;
import org.odk.collect.android.logic.FormController;
import org.odk.collect.android.model.Commune;
import org.odk.collect.android.model.District;
import org.odk.collect.android.model.Province;
import org.odk.collect.android.model.Village;
import org.odk.collect.android.utilities.PropertiesUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Vector;

/**
 * SpinnerWidget handles select-one fields. Instead of a list of buttons it uses a spinner, wherein
 * the user clicks a button and the choices pop up in a dialogue box. The goal is to be more
 * compact. If images, audio, or video are specified in the select answers they are ignored.
 *
 * @author Jeff Beorse (jeff@beorse.net)
 */
public class SpinnerWidget extends QuestionWidget {
    private static final int BROWN = 0xFF936931;
    Vector<SelectChoice> mItems;
    Spinner spinner;
    String[] choices;
    Commune mCommune;
    District mDistrict;
    Province mProvince;
    Village mVillage;
    private FormEntryActivity frm;
    private Boolean isFirstSetApdapter;
    private String hcVillageCome;
    private String hcVillageComeField;
    private List<Province> _mProvinceList;
    private List<District> _mDistrictList;
    private List<Commune> _mCommuneList;
    private List<Village> _mVillageList;


    public SpinnerWidget(final Context context, FormEntryPrompt prompt) {
        super(context, prompt);
        isFirstSetApdapter = true;
        hcVillageCome = prompt.getFormElement().getBind().getReference().toString();
        hcVillageComeField = getFieldID(hcVillageCome);
        //Get Form Controller
        FormController formController = Collect.getInstance()
                .getFormController();
        String frmLang = formController.getLanguage();

        // SurveyCTO-added support for dynamic select content (from .csv files)
        XPathFuncExpr xPathFuncExpr = ExternalDataUtil.getSearchXPathExpression(prompt.getAppearanceHint());
        if (xPathFuncExpr != null) {
            mItems = ExternalDataUtil.populateExternalChoices(prompt, xPathFuncExpr);
        } else {
            mItems = prompt.getSelectChoices();
        }
        Village village = new Village(context);
        String villageName;
        choices = new String[1];
        spinner = new Spinner(context);
        boolean overriceOnItemSelected = false;
        if (hcVillageComeField.equalsIgnoreCase("province")) {
            spinner.setId(R.id.sp_Province);
            mProvince = new Province(context);
            _mProvinceList = mProvince.getListFiveProvinces();
            mItems = new Vector<SelectChoice>();
            choices = new String[_mProvinceList.size() + 1];
            for (int i = 0; i < _mProvinceList.size(); i++) {
                Province selectProvince = mProvince.get(_mProvinceList.get(i).getProCode());
                choices[i] = selectProvince.getProvinceKh();
                mItems.add(new SelectChoice(String.valueOf(i), selectProvince.getProvinceKh()));
            }
            choices[_mProvinceList.size()] = "ជ្រើសរើស";
            PropertiesUtils.setSp_province(spinner);
            overriceOnItemSelected = true;
        } else if (hcVillageComeField.equalsIgnoreCase("district")) {
            spinner.setId(R.id.sp_District);
            PropertiesUtils.setSp_district(spinner);
            overriceOnItemSelected = true;
            mItems = new Vector<SelectChoice>();
            if (PropertiesUtils.getSelectedProvince() != null) {
                int proCode = PropertiesUtils.getSelectedProvince().getProCode();
                mDistrict = new District(context);
                try {
                    _mDistrictList = mDistrict.getByProvinceId(proCode);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                choices = new String[_mDistrictList.size() + 1];
                for (int i = 0; i < _mDistrictList.size(); i++) {
                    District selectDistrict = null;
                    try {
                        selectDistrict = mDistrict.get(_mDistrictList.get(i).getdCode());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    choices[i] = selectDistrict.getdNameKh();
                    mItems.add(new SelectChoice(String.valueOf(i), selectDistrict.getdNameKh()));
                }
                choices[_mDistrictList.size()] = "ជ្រើសរើស";
            } else {
                choices[0] = "ជ្រើសរើស";
            }
        } else if (hcVillageComeField.equalsIgnoreCase("Commune")) {
            spinner.setId(R.id.sp_Commune);
            PropertiesUtils.setSp_commune(spinner);
            overriceOnItemSelected = true;
            mItems = new Vector<SelectChoice>();
            if (PropertiesUtils.getSelectedDistrict() != null) {
                int districtCode = PropertiesUtils.getSelectedDistrict().getdCode();
                mCommune = new Commune(context);
                try {
                    _mCommuneList = mCommune.getByDistrictId(districtCode);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                choices = new String[_mCommuneList.size() + 1];
                for (int i = 0; i < _mCommuneList.size(); i++) {
                    Commune selectCommune = null;
                    try {
                        selectCommune = mCommune.get(_mCommuneList.get(i).getcCode());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    mItems.add(new SelectChoice(String.valueOf(i), selectCommune.getcNameKh()));
                    choices[i] = selectCommune.getcNameKh();
                }
                choices[_mCommuneList.size()] = "ជ្រើសរើស";
            } else {
                choices[0] = "ជ្រើសរើស";
            }
        } else if (hcVillageComeField.equalsIgnoreCase("Village")) {
            spinner.setId(R.id.sp_Village);
            PropertiesUtils.setSp_village(spinner);
            overriceOnItemSelected = true;
            mItems = new Vector<SelectChoice>();
            if (PropertiesUtils.getSelectedCommune() != null) {
                Village mVillage = new Village(context);
                int communeId = PropertiesUtils.getSelectedCommune().getcCode();
                try {
                    _mVillageList = mVillage.getByCommuneId(communeId);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                choices = new String[_mVillageList.size() + 1];
                for (int i = 0; i < _mVillageList.size(); i++) {
                    Village selectVillages = null;
                    try {
                        selectVillages = mVillage.get(_mVillageList.get(i).getvCode());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    mItems.add(new SelectChoice(String.valueOf(i), selectVillages.getvNameKh()));
                    choices[i] = selectVillages.getvNameKh();
                }
                choices[_mVillageList.size()] = "ជ្រើសរើស";
            } else {
                choices[0] = "ជ្រើសរើស";
            }
        }  else {
            choices = new String[mItems.size() + 1];
            for (int i = 0; i < mItems.size(); i++) {
                choices[i] = prompt.getSelectChoiceText(mItems.get(i));
            }
            choices[mItems.size()] = "ជ្រើសរើស";
        }
        // The spinner requires a custom adapter. It is defined below
        SpinnerAdapter adapter = new SpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, choices,
                TypedValue.COMPLEX_UNIT_DIP, mQuestionFontsize);

        spinner.setAdapter(adapter);
        spinner.setPrompt(prompt.getQuestionText());
        spinner.setEnabled(!prompt.isReadOnly());
        spinner.setFocusable(!prompt.isReadOnly());

        // Fill in previous answer
        String s = null;
        if (prompt.getAnswerValue() != null) {
            s = ((Selection) prompt.getAnswerValue().getValue()).getValue();
        }

        if (overriceOnItemSelected) {
            spinner.setOnItemSelectedListener(new org.odk.collect.android.listeners.OnItemSelectedListener(context));
        } else {
            spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

                @SuppressWarnings("static-access")
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int size = mItems.size();
                    if (position == size || choices.length == 1) {
                        Collect.getInstance().getActivityLogger().logInstanceAction(this, "onCheckedChanged.clearValue",
                                "", mPrompt.getIndex());
                        Log.i("sengelse", "jolif");
                        if (isFirstSetApdapter)
                            isFirstSetApdapter = false;
                    } else {
                        Log.i("sengelse", "jol " + isFirstSetApdapter.toString());
                        try {

                            Collect.getInstance().getActivityLogger().logInstanceAction(this, "onCheckedChanged",
                                    mItems.get(position).getValue(), mPrompt.getIndex());
                        } catch (Exception e) {
                            Collect.getInstance().getActivityLogger().logInstanceAction(this, "onCheckedChanged",
                                    "", mPrompt.getIndex());
                        }

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });
        }
        if (s != null) {
            for (int i = 0; i < mItems.size(); ++i) {
                String sMatch = mItems.get(i).getValue();
                if (sMatch.equals(s)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        } else {
            spinner.setSelection(mItems.size());
        }
        if (hcVillageComeField.equalsIgnoreCase("yearflooded")){
            if (PropertiesUtils.getiAnswer2_2()==1){
                spinner.setVisibility(VISIBLE);
            } else  spinner.setVisibility(INVISIBLE);
            PropertiesUtils.set_spQuestion20_1(spinner);
        }
        addView(spinner);
    }

    @Override
    public IAnswerData getAnswer() {
        clearFocus();
        int i = spinner.getSelectedItemPosition();
        if (i == -1 || i == mItems.size()) {
            return null;

        } else {
            try {
                SelectChoice sc = mItems.elementAt(i);
                return new SelectOneData(new Selection(sc));
            } catch (Exception e) {
            }
            String sc = null;
            if (hcVillageComeField.equalsIgnoreCase("district")) {
                if (PropertiesUtils.getSelectedDistrict() != null) {
                    sc = PropertiesUtils.getSelectedDistrict().getdNameKh();
                } else {
                    return null;
                }
            } else if (hcVillageComeField.equalsIgnoreCase("province")) {
                if (PropertiesUtils.getSelectedProvince() != null) {
                    sc = PropertiesUtils.getSelectedProvince().getProvinceKh();
                } else {
                    return null;
                }
            } else if (hcVillageComeField.equalsIgnoreCase("commune")) {
                if (PropertiesUtils.getSelectedCommune() != null) {
                    sc = PropertiesUtils.getSelectedCommune().getcNameKh();
                } else {
                    return null;
                }
            } else if (hcVillageComeField.equalsIgnoreCase("village")) {
                if (PropertiesUtils.getSelectedVillage() != null) {
                    sc = PropertiesUtils.getSelectedVillage().getvNameKh();

                } else {
                    return null;
                }
            }
            return new SelectOneData(new Selection(sc));
        }
    }


    @Override
    public void clearAnswer() {
        // It seems that spinners cannot return a null answer. This resets the answer
        // to its original value, but it is not null.
        spinner.setSelection(mItems.size());
    }


    @Override
    public void setFocus(Context context) {
        // Hide the soft keyboard if it's showing.
        InputMethodManager inputManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getWindowToken(), 0);

    }

    @Override
    public void setOnLongClickListener(View.OnLongClickListener l) {
        spinner.setOnLongClickListener(l);
    }

    @Override
    public void cancelLongPress() {
        super.cancelLongPress();
        spinner.cancelLongPress();
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
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

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.custom_spinner_item, parent, false);
            }

            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setTextSize(textUnit, textSize);
            tv.setBackgroundColor(Color.WHITE);
            tv.setPadding(10, 10, 10, 10); // Are these values OK?
            if (position == items.length - 1) {
                tv.setText(parent.getContext().getString(R.string.clear_answer));
                tv.setTextColor(BROWN);
                tv.setTypeface(null, Typeface.NORMAL);
                if (spinner.getSelectedItemPosition() == position) {
                    tv.setBackgroundColor(Color.LTGRAY);
                }
            } else {
                tv.setText(items[position]);
                tv.setTextColor(Color.BLACK);
                tv.setTypeface(null, (spinner.getSelectedItemPosition() == position)
                        ? Typeface.BOLD : Typeface.NORMAL);
            }
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

}
