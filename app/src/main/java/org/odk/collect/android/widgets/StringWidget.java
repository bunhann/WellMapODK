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

import java.util.Locale;

import org.javarosa.core.model.Constants;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.StringData;
import org.javarosa.form.api.FormEntryPrompt;
import org.odk.collect.android.R;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.listeners.StringDateListener;
import org.odk.collect.android.utilities.PropertiesUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.BaseKeyListener;
import android.text.method.DateKeyListener;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.method.QwertyKeyListener;
import android.text.method.TextKeyListener;
import android.text.method.TextKeyListener.Capitalize;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import br.com.sapereaude.maskedEditText.MaskedEditText;

/**
 * The most basic widget that allows for entry of any text.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */
public class StringWidget extends QuestionWidget {
    private static final String ROWS = "rows";
    private SharedPreferences mAdminPreferences;

    boolean mReadOnly = false;
    protected EditText mAnswer;

    public StringWidget(Context context, FormEntryPrompt prompt, boolean readOnlyOverride) {
        this(context, prompt, readOnlyOverride, true);
        setupChangeListener();
    }

    protected StringWidget(Context context, FormEntryPrompt prompt, boolean readOnlyOverride, boolean derived) {
        super(context, prompt);
        mAnswer = new EditText(context);
        mAnswer.setId(QuestionWidget.newUniqueId());
        mReadOnly = prompt.isReadOnly() || readOnlyOverride;

        mAnswer.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mAnswerFontsize);

        String fieldElementReference = prompt.getFormElement().getBind().getReference().toString();
        String fieldName = getFieldID(fieldElementReference);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams();

        if (fieldName.equalsIgnoreCase("well_id")) {
            mAnswer.setText(PropertiesUtils.get_wellId());
        }

        //address hide value
        if (fieldName.equalsIgnoreCase("address_province_code")) {
            mAnswer.setText(PropertiesUtils.getAddressProvinceCode());
            mAnswer.setVisibility(GONE);
        }
        if (fieldName.equalsIgnoreCase("address_district_code")) {
            mAnswer.setText(PropertiesUtils.getAddressDistrictCode());
            mAnswer.setVisibility(GONE);
        }
        if (fieldName.equalsIgnoreCase("address_commune_code")) {
            mAnswer.setText(PropertiesUtils.getAddressCommuneCode());
            mAnswer.setVisibility(GONE);
        }
        if (fieldName.equalsIgnoreCase("address_village_code")) {
            mAnswer.setText(PropertiesUtils.getAddressVillageCode());
            mAnswer.setVisibility(GONE);
        }
        if (fieldName.equalsIgnoreCase("village_code")) {
            mAnswer.setKeyListener(null);
            mAnswer.setEnabled(false);
            mAnswer.setTextColor(getResources().getColor(R.color.text_red));
            PropertiesUtils.set_villeCode(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("well_id")) {
            mAnswer.setText(PropertiesUtils.get_wellId());
        }
        if (fieldName.equalsIgnoreCase("recording_other")) {

            if (PropertiesUtils.getiAnswer1RecordingOther() == 0) {
                mAnswer.setVisibility(INVISIBLE);
            } else {
                mAnswer.setVisibility(VISIBLE);
            }
            PropertiesUtils.setEdQuestionRecordingOther(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("otherfunded")) {
            PropertiesUtils.set_q12Ref(mAnswer);
            if (PropertiesUtils.get_q2Test() == 0) {
                mAnswer.setVisibility(INVISIBLE);
            } else mAnswer.setVisibility(VISIBLE);
        }
        if (fieldName.equalsIgnoreCase("otherfunded1")) {
            PropertiesUtils.set_q32Ref(mAnswer);
            if (PropertiesUtils.get_q22Test() == 0) {
                mAnswer.setVisibility(INVISIBLE);
            } else mAnswer.setVisibility(VISIBLE);
        }
        if (fieldName.equalsIgnoreCase("platform")) {
            PropertiesUtils.set_q33Ref(mAnswer);
            if (PropertiesUtils.get_q23Test() == 0) {
                mAnswer.setVisibility(INVISIBLE);
            } else mAnswer.setVisibility(VISIBLE);
        }
        if (fieldName.equalsIgnoreCase("fundedbyrehabilitate")){
            PropertiesUtils.set_q50Ref(mAnswer);
            if (PropertiesUtils.get_q50Test() == 0) {
                mAnswer.setVisibility(INVISIBLE);
            } else mAnswer.setVisibility(VISIBLE);

        }
        if (fieldName.equalsIgnoreCase("primaryother")) {
            PropertiesUtils.set_q13Ref(mAnswer);
            if (PropertiesUtils.get_q3Test() == 0) {
                mAnswer.setVisibility(INVISIBLE);
            } else mAnswer.setVisibility(VISIBLE);
        }

        if (fieldName.equalsIgnoreCase("diameterother")) {
            PropertiesUtils.set_q14Ref(mAnswer);
            if (PropertiesUtils.get_q4Test() == 0) {
                mAnswer.setVisibility(INVISIBLE);
            } else mAnswer.setVisibility(VISIBLE);
        }

        if (fieldName.equalsIgnoreCase("damagedother")) {
            if (PropertiesUtils.getiAnswer31()!=0 && PropertiesUtils.getiAnswer311()==1){
                mAnswer.setVisibility(VISIBLE);
            } else mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion311Other(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("othertaste")) {
           /* if(PropertiesUtils.getiAns33()==0 && PropertiesUtils.getiAns333()==0) {
                mAnswer.setVisibility(VISIBLE);
            }  else mAnswer.setVisibility(INVISIBLE);*/
            if (PropertiesUtils.getiAns332()==0){
                mAnswer.setVisibility(INVISIBLE);
            } else mAnswer.setVisibility(VISIBLE);
            PropertiesUtils.setEdQues333Other(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("typedirt_other")) {
            if (PropertiesUtils.getiAnswer32()!=0 && PropertiesUtils.getiAnswer321()==1){
                mAnswer.setVisibility(VISIBLE);
            } else mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion321Other(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("arsenic")){
            if (PropertiesUtils.getiAnswer4()==1){
                mAnswer.setVisibility(VISIBLE);
            } else mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion411(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("ph")){
            if (PropertiesUtils.getiAnswer4()==1){
                mAnswer.setVisibility(VISIBLE);
            } else mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion412(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("free_chlorine")){
            if (PropertiesUtils.getiAnswer4()==1){
                mAnswer.setVisibility(VISIBLE);
            } else mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion413(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("nitrate")){
            if (PropertiesUtils.getiAnswer4()==1){
                mAnswer.setVisibility(VISIBLE);
            } else mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion414(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("nitrite")){
            if (PropertiesUtils.getiAnswer4()==1){
                mAnswer.setVisibility(VISIBLE);
            } else mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion415(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("iron")){
            if (PropertiesUtils.getiAnswer4()==1){
                mAnswer.setVisibility(VISIBLE);
            } else mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion416(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("e_coli")){
            PropertiesUtils.set_q26Ref(mAnswer);
//            if (PropertiesUtils.get_q16Test() == 0){
//                mAnswer.setVisibility(VISIBLE);
//            }else mAnswer.setVisibility(INVISIBLE);
            mAnswer.setVisibility(INVISIBLE);
        }

        if (fieldName.equalsIgnoreCase("total_coliform")){
            PropertiesUtils.set_q27Ref(mAnswer);
//            if (PropertiesUtils.get_q17Test() == 0){
//                mAnswer.setVisibility(VISIBLE);
//            }else mAnswer.setVisibility(INVISIBLE);
            mAnswer.setVisibility(INVISIBLE);
        }

        if (fieldName.equalsIgnoreCase("hardness")){
            if (PropertiesUtils.getiAnswer4()==1){
                mAnswer.setVisibility(VISIBLE);
            } else mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion417(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("turbidity")){
            if (PropertiesUtils.getiAnswer4()==1){
                mAnswer.setVisibility(VISIBLE);
            } else mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion418(mAnswer);
        }
        if (fieldName.equalsIgnoreCase("gps_location")) {
            if (PropertiesUtils.getLocationGPS() != null) {
                mAnswer.setText(PropertiesUtils.getLocationGPS());
                mAnswer.setVisibility(INVISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("well_id")){
            PropertiesUtils.set_q28Ref(mAnswer);
//            if (PropertiesUtils.get_q17Test() == 0){
//                mAnswer.setVisibility(VISIBLE);
//            }else mAnswer.setVisibility(INVISIBLE);
            mAnswer.setVisibility(INVISIBLE);
        }

        if (fieldName.equalsIgnoreCase("primaryother")){
            mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setPrimaryOther(mAnswer);
            if (PropertiesUtils.getQuestionPrimaryOther().getVisibility()==VISIBLE)
                mAnswer.setVisibility(VISIBLE);
            else mAnswer.setVisibility(INVISIBLE);
        }
        if (fieldName.equalsIgnoreCase("diameter_other")){
            mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setDiameterOther(mAnswer);
            if (PropertiesUtils.getQuestionDiameterOther().getVisibility()==VISIBLE)
                mAnswer.setVisibility(VISIBLE);
            else mAnswer.setVisibility(INVISIBLE);
        }

        if (fieldName.equalsIgnoreCase("otherdamaged")){
            if (PropertiesUtils.getiAnswer31()!=0&& PropertiesUtils.getiAnswer312()==1){
                mAnswer.setVisibility(VISIBLE);
            } else  mAnswer.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion312Other(mAnswer);
        }

        if (fieldName.equalsIgnoreCase("date_well1")){
            PropertiesUtils.set_ed_Answer2_1_1(mAnswer);
            if (PropertiesUtils.getiAnswer2_8()==7){
                mAnswer.setVisibility(INVISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("otherlocation")){
            PropertiesUtils.setEdAnswerOtherLocation(mAnswer);
            if (PropertiesUtils.getiAnswerOtherLocation()==1){
                mAnswer.setVisibility(VISIBLE);
            } else mAnswer.setVisibility(INVISIBLE);
        }

        if (fieldName.equalsIgnoreCase("hidden")){
            mAnswer.setVisibility(INVISIBLE);
        }
        if (fieldName.equalsIgnoreCase("hidden1")){
            mAnswer.setVisibility(INVISIBLE);
        }


        /**
         * If a 'rows' attribute is on the input tag, set the minimum number of lines
         * to display in the field to that value.
         *
         * I.e.,
         * <input ref="foo" rows="5">
         *   ...
         * </input>
         *
         * will set the height of the EditText box to 5 rows high.
         */
        String height = prompt.getQuestion().getAdditionalAttribute(null, ROWS);
        if (height != null && height.length() != 0) {
            try {
                int rows = Integer.valueOf(height);
                mAnswer.setMinLines(rows);
                mAnswer.setGravity(Gravity.TOP); // to write test starting at the top of the edit area
            } catch (Exception e) {
                Log.e(this.getClass().getName(), "Unable to process the rows setting for the answer field: " + e.toString());
            }
        }

        params.setMargins(7, 5, 7, 5);
        mAnswer.setLayoutParams(params);

        // capitalize the first letter of the sentence
        mAnswer.setKeyListener(new TextKeyListener(Capitalize.SENTENCES, false));

        // needed to make long read only text scroll
        mAnswer.setHorizontallyScrolling(false);
        mAnswer.setSingleLine(false);

        String s = prompt.getAnswerText();
        if (s != null) {
            mAnswer.setText(s);
        }

        if (mReadOnly) {
            mAnswer.setBackgroundDrawable(null);
            mAnswer.setFocusable(false);
            mAnswer.setClickable(false);

            // add spacing to note seng added
            LinearLayout ll = new LinearLayout(context);
            ll.setPadding(0, 10, 0, 10);
            addView(ll);

            // seng added with appearance username
            String appearance = prompt.getAppearanceHint();
            if (appearance == null) appearance = "";
            // for now, all appearance tags are in english...
            appearance = appearance.toLowerCase(Locale.ENGLISH);
            switch (prompt.getControlType()) {
                case Constants.CONTROL_INPUT:
                    switch (prompt.getDataType()) {
                        case Constants.DATATYPE_TEXT:
                            String query = prompt.getQuestion().getAdditionalAttribute(null, "query");
                            if (query != null) {

                            } else if (appearance.equals("box")) {
                                setBackgroundResource(R.drawable.bordortopbottom);
                            } else if (appearance.equalsIgnoreCase("readonly")) {
                                mAnswer.setKeyListener(null);
                                mAnswer.setEnabled(false);
                                mAnswer.setTextColor(getResources().getColor(R.color.text_red));
                            } else if (appearance.equalsIgnoreCase("hide")) {
                                mAnswer.setVisibility(INVISIBLE);
                                mAnswer.setKeyListener(null);
                                mAnswer.setEnabled(false);
                                mAnswer.setTextColor(getResources().getColor(R.color.text_red));
                            }

                            break;
                    }
            }
            // end appearance username

        } else {
            // seng added with appearance username
            String appearance = prompt.getAppearanceHint();
            if (appearance == null) appearance = "";
            // for now, all appearance tags are in english...
            appearance = appearance.toLowerCase(Locale.ENGLISH);
            switch (prompt.getControlType()) {
                case Constants.CONTROL_INPUT:
                    switch (prompt.getDataType()) {
                        case Constants.DATATYPE_TEXT:
                            String query = prompt.getQuestion().getAdditionalAttribute(null, "query");
                            if (query != null) {

                            } else if (appearance.equals("username")) {
                                // get admin preference settings
                                mAdminPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                                String name = mAdminPreferences.getString("username", "");
                                //String name = mAdminPreferences.getString(PreferencesActivity.KEY_USERNAME, "");
                                //EditTextPreference name = (EditTextPreference)PreferenceActivity.findPreference("KEY_USERNAME");
                                Log.i("seng", name);
                                mAnswer.setText(name);

                            } else if (appearance.equals("string-date")){
                                mAnswer.setKeyListener(new DateKeyListener() {
                                    @Override
                                    protected char[] getAcceptedChars() {
                                        char[] accepted = {
                                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/'
                                        };
                                        return accepted;
                                    }
                                });
                            } else if (appearance.equalsIgnoreCase("readonly")) {
                                mAnswer.setKeyListener(null);
                                mAnswer.setEnabled(false);
                                mAnswer.setTextColor(getResources().getColor(R.color.text_red));
                            }
                            break;
                    }
            }



            // end appearance username

            addView(mAnswer);
        }


    }

    protected void setupChangeListener() {
        mAnswer.addTextChangedListener(new TextWatcher() {
            private String oldText = "";

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(oldText)) {
                    Collect.getInstance().getActivityLogger()
                            .logInstanceAction(this, "answerTextChanged", s.toString(), getPrompt().getIndex());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                oldText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });
    }

    @Override
    public void clearAnswer() {
        mAnswer.setText(null);
    }


    @Override
    public IAnswerData getAnswer() {
        clearFocus();
        String s = mAnswer.getText().toString();
        if (s == null || s.equals("")) {
            return null;
        } else {
            return new StringData(s);
        }
    }


    @Override
    public void setFocus(Context context) {
        // Put focus on text input field and display soft keyboard if appropriate.
        mAnswer.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!mReadOnly) {
            inputManager.showSoftInput(mAnswer, 0);
            /*
             * If you do a multi-question screen after a "add another group" dialog, this won't
             * automatically pop up. It's an Android issue.
             *
             * That is, if I have an edit text in an activity, and pop a dialog, and in that
             * dialog's button's OnClick() I call edittext.requestFocus() and
             * showSoftInput(edittext, 0), showSoftinput() returns false. However, if the edittext
             * is focused before the dialog pops up, everything works fine. great.
             */
        } else {
            inputManager.hideSoftInputFromWindow(mAnswer.getWindowToken(), 0);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.isAltPressed() == true) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        mAnswer.setOnLongClickListener(l);
    }


    @Override
    public void cancelLongPress() {
        super.cancelLongPress();
        mAnswer.cancelLongPress();
    }

}
