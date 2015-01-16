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

import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectOneData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;
import org.javarosa.xpath.expr.XPathFuncExpr;
import org.odk.collect.android.R;
import org.odk.collect.android.activities.FormEntryActivity;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.external.ExternalDataUtil;
import org.odk.collect.android.external.ExternalSelectChoice;
import org.odk.collect.android.utilities.PropertiesUtils;
import org.odk.collect.android.views.MediaLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

/**
 * SelectOneWidgets handles select-one fields using radio buttons.
 *
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */
@SuppressLint("NewApi")
public class SelectOneWidget extends QuestionWidget implements
        OnCheckedChangeListener {

    Vector<SelectChoice> mItems; // may take a while to compute
    ArrayList<RadioButton> buttons;
    String fieldName;

    public SelectOneWidget(Context context, FormEntryPrompt prompt) {
        super(context, prompt);

        // SurveyCTO-added support for dynamic select content (from .csv files)
        XPathFuncExpr xPathFuncExpr = ExternalDataUtil.getSearchXPathExpression(prompt.getAppearanceHint());
        if (xPathFuncExpr != null) {
            mItems = ExternalDataUtil.populateExternalChoices(prompt, xPathFuncExpr);
        } else {
            mItems = prompt.getSelectChoices();
        }
        buttons = new ArrayList<RadioButton>();

        //setOrientation(LinearLayout.HORIZONTAL);
        //setBackgroundColor(Color.LTGRAY);


        // Layout holds the vertical list of buttons
        LinearLayout buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        buttonLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        // The buttons take up the right half of the screen
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, 0.25f);

        String fieldElementReference = prompt.getFormElement().getBind().getReference().toString();
        fieldName = getFieldID(fieldElementReference);

        String s = null;
        if (prompt.getAnswerValue() != null) {
            s = ((Selection) prompt.getAnswerValue().getValue()).getValue();
        }

        if (mItems != null) {
            for (int i = 0; i < mItems.size(); i++) {
                RadioButton r = new RadioButton(getContext());
                r.setText(prompt.getSelectChoiceText(mItems.get(i)));
                r.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mAnswerFontsize);
                r.setTag(Integer.valueOf(i));
                r.setId(QuestionWidget.newUniqueId());
                r.setEnabled(!prompt.isReadOnly());
                r.setFocusable(!prompt.isReadOnly());
                r.setPadding(0, 10, 0, 10);
                buttons.add(r);

                if (mItems.get(i).getValue().equals(s)) {
                    r.setChecked(true);
                }

                r.setOnCheckedChangeListener(this);

                String audioURI = null;
                audioURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i),
                        FormEntryCaption.TEXT_FORM_AUDIO);

                String imageURI;
                if (mItems.get(i) instanceof ExternalSelectChoice) {
                    imageURI = ((ExternalSelectChoice) mItems.get(i)).getImage();
                } else {
                    imageURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i), FormEntryCaption.TEXT_FORM_IMAGE);
                }

                String videoURI = null;
                videoURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i),
                        "video");

                String bigImageURI = null;
                bigImageURI = prompt.getSpecialFormSelectChoiceText(
                        mItems.get(i), "big-image");

                MediaLayout mediaLayout = new MediaLayout(getContext());
                mediaLayout.setAVT(prompt.getIndex(), "." + Integer.toString(i), r, audioURI, imageURI,
                        videoURI, bigImageURI);
                mediaLayout.setLayoutParams(params);
           /*     ImageView divider = new ImageView(getContext());
                divider.setBackgroundResource(android.R.drawable.divider_horizontal_bright);
                mediaLayout.addDivider(divider);*/
/*				if (((i/2) != (Math.floor( mItems.size()/2) )) && i%2==0){
					// Last, add the dividing line (except for the last element)
					ImageView divider = new ImageView(getContext());
					divider.setBackgroundResource(android.R.drawable.divider_horizontal_bright);
					//SHOW_DIVIDER_MIDDLE
					mediaLayout.addDivider(divider);
				}*/
                buttonLayout.addView(mediaLayout);
                //buttonLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE | LinearLayout.SHOW_DIVIDER_BEGINNING | LinearLayout.SHOW_DIVIDER_END);
                //buttonLayout.setDividerDrawable(context.getResources().getDrawable(R.drawable.divider));
            }
        }
        if (fieldName.equalsIgnoreCase("outflowdrain")) {
            PropertiesUtils.setLayoutTaste2(buttonLayout);
            buttonLayout.setVisibility(GONE);

        }
        if (fieldName.equalsIgnoreCase("damaged")) {
            if (PropertiesUtils.getiAnswer31() == 1) {
                buttonLayout.setVisibility(VISIBLE);
            } else buttonLayout.setVisibility(INVISIBLE);
            PropertiesUtils.setLayoutQuestion311(buttonLayout);
        }
        if (fieldName.equalsIgnoreCase("yearflooded")) {
            if (PropertiesUtils.getiAnswer2_2() == 1) {
                buttonLayout.setVisibility(VISIBLE);
            } else buttonLayout.setVisibility(INVISIBLE);
            PropertiesUtils.setLayoutTaste4(buttonLayout);

        }
        if (fieldName.equalsIgnoreCase("highestflooded")) {
            if (PropertiesUtils.getiAnswer2_2() == 1) {
                buttonLayout.setVisibility(VISIBLE);
            } else buttonLayout.setVisibility(INVISIBLE);
            PropertiesUtils.set_layQuestion20_2(buttonLayout);

        }

        if (fieldName.equalsIgnoreCase("wellplatform")) {
            if (PropertiesUtils.getiAnswer2_1() == 1) {
                buttonLayout.setVisibility(VISIBLE);
            } else buttonLayout.setVisibility(INVISIBLE);
            PropertiesUtils.set_wellplatform(buttonLayout);

        }
        if (fieldName.equalsIgnoreCase("platformdesign")) {
            if (PropertiesUtils.getiAnswer2_1() == 1) {
                buttonLayout.setVisibility(VISIBLE);
            } else buttonLayout.setVisibility(INVISIBLE);
            PropertiesUtils.set_platformdesign(buttonLayout);

        }
        if (fieldName.equalsIgnoreCase("draindesign")) {
            if (PropertiesUtils.getiAnswer2_1() == 1) {
                buttonLayout.setVisibility(VISIBLE);
            } else buttonLayout.setVisibility(INVISIBLE);
            PropertiesUtils.set_draindesign(buttonLayout);
        }
        if (fieldName.equalsIgnoreCase("flooded")) {
            if (PropertiesUtils.getiAnswer2_1() == 1) {
                buttonLayout.setVisibility(VISIBLE);
            } else buttonLayout.setVisibility(INVISIBLE);
            PropertiesUtils.set_flooded(buttonLayout);
        }
        if (fieldName.equalsIgnoreCase("height")) {
            if (PropertiesUtils.getiAnswer2_1() == 1) {
                buttonLayout.setVisibility(VISIBLE);
            } else buttonLayout.setVisibility(INVISIBLE);
            PropertiesUtils.set_height(buttonLayout);
        }
        if (fieldName.equalsIgnoreCase("date_well1")) {
            PropertiesUtils.set_date_well1Layout(buttonLayout);
        }
        if (fieldName.equalsIgnoreCase("inform")) {
            if (PropertiesUtils.getiAnswer4() == 1) {
                buttonLayout.setVisibility(VISIBLE);
            } else buttonLayout.setVisibility(INVISIBLE);
            PropertiesUtils.setEdQuestion4119(buttonLayout);
        }
        if (fieldName.equalsIgnoreCase("taste")) {
            if (PropertiesUtils.getiAns33() == 1) {
                buttonLayout.setVisibility(VISIBLE);
            } else buttonLayout.setVisibility(INVISIBLE);
            PropertiesUtils.setLayoutQues332(buttonLayout);
        }
        if (fieldName.equalsIgnoreCase("taste1")) {
            if (PropertiesUtils.getiAns33() == 1) {
                buttonLayout.setVisibility(VISIBLE);
            } else buttonLayout.setVisibility(INVISIBLE);
            PropertiesUtils.setLayoutQues332(buttonLayout);
        }
        addView(buttonLayout, params);

    }

    @Override
    public void clearAnswer() {
        for (RadioButton button : this.buttons) {
            if (button.isChecked()) {
                button.setChecked(false);
                return;
            }
        }
    }

    @Override
    public IAnswerData getAnswer() {
        int i = getCheckedId();
        if (i == -1) {
            return null;
        } else {
            SelectChoice sc = mItems.elementAt(i);
            return new SelectOneData(new Selection(sc));
        }
    }

    @Override
    public void setFocus(Context context) {
        // Hide the soft keyboard if it's showing.
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    public int getCheckedId() {
        for (int i = 0; i < buttons.size(); ++i) {
            RadioButton button = buttons.get(i);
            if (button.isChecked()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            // If it got unchecked, we don't care.
            return;
        }

        for (RadioButton button : buttons) {
            if (button.isChecked() && !(buttonView == button)) {
                button.setChecked(false);
            }
        }
        if (fieldName.equalsIgnoreCase("recording_organization")) {
            if (buttons.get(4).isChecked()) {
                PropertiesUtils.setiAnswer1RecordingOther(1);
                PropertiesUtils.getTvQuestionRecordingOther().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestionRecordingOther().setVisibility(VISIBLE);
            } else {
                PropertiesUtils.setiAnswer1RecordingOther(0);
                PropertiesUtils.getTvQuestionRecordingOther().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestionRecordingOther().setVisibility(INVISIBLE);
            }
        }

        //Other Location of Well
        if (fieldName.equalsIgnoreCase("location_well")) {
            if (buttons.get(5).isChecked()) {
                PropertiesUtils.getTvQuestionOtherLocation().setVisibility(VISIBLE);
                PropertiesUtils.getEdAnswerOtherLocation().setVisibility(VISIBLE);
                PropertiesUtils.setiAnswerOtherLocation(1);
            } else {
                PropertiesUtils.getTvQuestionOtherLocation().setVisibility(INVISIBLE);
                PropertiesUtils.getEdAnswerOtherLocation().setVisibility(INVISIBLE);
                PropertiesUtils.setiAnswerOtherLocation(0);
            }
        }

        if (fieldName.equalsIgnoreCase("fundedbyoriginal")) {
            if (buttons.get(5).isChecked()) {
                PropertiesUtils.set_q2Test(1);
                PropertiesUtils.get_q12Ref().setVisibility(View.VISIBLE);
                //Log.d("BUNHANN", PropertiesUtils.getTv_QuestionText().getText().toString());
                PropertiesUtils.getTv_QuestionText1().setVisibility(VISIBLE);

            } else {
                PropertiesUtils.set_q2Test(0);
                PropertiesUtils.get_q12Ref().setVisibility(View.INVISIBLE);
                PropertiesUtils.getTv_QuestionText1().setVisibility(INVISIBLE);
            }
        }
        if (fieldName.equalsIgnoreCase("fundedbyrehabilitated")) {
            if (buttons.get(5).isChecked()) {
                PropertiesUtils.set_q22Test(1);
                PropertiesUtils.get_q32Ref().setVisibility(View.VISIBLE);
                PropertiesUtils.getTv_QuestionText20().setVisibility(VISIBLE);
            } else if (buttons.get(7).isChecked()) {
                PropertiesUtils.get_tv_Question2_1_1().setVisibility(INVISIBLE);
                PropertiesUtils.get_ed_Answer2_1_1().setVisibility(INVISIBLE);
                PropertiesUtils.setiAnswer2_8(7);

            } else {
                PropertiesUtils.set_q22Test(0);
                PropertiesUtils.get_q32Ref().setVisibility(View.INVISIBLE);
                PropertiesUtils.getTv_QuestionText20().setVisibility(INVISIBLE);
                PropertiesUtils.get_tv_Question2_1_1().setVisibility(VISIBLE);
                PropertiesUtils.get_ed_Answer2_1_1().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("platform")) {

            if (buttons.get(0).isChecked()) {
                PropertiesUtils.getImgLayout().setVisibility(VISIBLE);
                PropertiesUtils.getQuestionImage().setVisibility(VISIBLE);

                PropertiesUtils.get_tv_QuestionWellplatform().setVisibility(VISIBLE);
                PropertiesUtils.get_wellplatform().setVisibility(VISIBLE);

                PropertiesUtils.get_tv_QuestionPlatformdesign().setVisibility(VISIBLE);
                PropertiesUtils.get_platformdesign().setVisibility(VISIBLE);

                PropertiesUtils.get_tv_QuestionDraindesign().setVisibility(VISIBLE);
                PropertiesUtils.get_draindesign().setVisibility(VISIBLE);

                PropertiesUtils.get_tv_QuestionFlooded().setVisibility(VISIBLE);
                PropertiesUtils.get_flooded().setVisibility(VISIBLE);

                PropertiesUtils.get_tv_QuestionHeight().setVisibility(VISIBLE);
                PropertiesUtils.get_height().setVisibility(VISIBLE);

                PropertiesUtils.setiAnswer2_1(1);

            } else {
                PropertiesUtils.getImgLayout().setVisibility(INVISIBLE);
                PropertiesUtils.getQuestionImage().setVisibility(INVISIBLE);

                PropertiesUtils.get_tv_QuestionWellplatform().setVisibility(INVISIBLE);
                PropertiesUtils.get_wellplatform().setVisibility(INVISIBLE);

                PropertiesUtils.get_tv_QuestionPlatformdesign().setVisibility(INVISIBLE);
                PropertiesUtils.get_platformdesign().setVisibility(INVISIBLE);

                PropertiesUtils.get_tv_QuestionDraindesign().setVisibility(INVISIBLE);
                PropertiesUtils.get_draindesign().setVisibility(INVISIBLE);

                PropertiesUtils.get_tv_QuestionFlooded().setVisibility(INVISIBLE);
                PropertiesUtils.get_flooded().setVisibility(INVISIBLE);

                PropertiesUtils.get_tv_QuestionHeight().setVisibility(INVISIBLE);
                PropertiesUtils.get_height().setVisibility(INVISIBLE);

                PropertiesUtils.setiAnswer2_1(0);
            }
        }

        if (fieldName.equalsIgnoreCase("primarypump")) {
            if (buttons.get(12).isChecked()) {
                PropertiesUtils.set_q3Test(1);
                PropertiesUtils.get_q13Ref().setVisibility(View.VISIBLE);
            } else {
                PropertiesUtils.set_q3Test(0);
                PropertiesUtils.get_q13Ref().setVisibility(View.INVISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("diameter_of_well")) {
            if (buttons.get(10).isChecked()) {
                PropertiesUtils.set_q4Test(1);
                PropertiesUtils.get_q14Ref().setVisibility(VISIBLE);
            } else {
                PropertiesUtils.set_q4Test(0);
                PropertiesUtils.get_q14Ref().setVisibility(View.INVISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("damaged")) {
            if (buttons.get(4).isChecked()) {
                PropertiesUtils.getTvQuestion311Other().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion311Other().setVisibility(VISIBLE);
                PropertiesUtils.setiAnswer311(1);
            } else {
                PropertiesUtils.getTvQuestion311Other().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion311Other().setVisibility(INVISIBLE);
                PropertiesUtils.setiAnswer311(0);
            }
        }

        if (fieldName.equalsIgnoreCase("otherdamaged")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.set_q6Test(1);
                PropertiesUtils.get_q16Ref().setVisibility(VISIBLE);
            } else {
                PropertiesUtils.set_q6Test(0);
                PropertiesUtils.get_q16Ref().setVisibility(View.INVISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("outflow")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.getLayoutTaste2().setVisibility(VISIBLE);
                PropertiesUtils.getTv_QuestionTast2().setVisibility(VISIBLE);
            } else {
                PropertiesUtils.getLayoutTaste2().setVisibility(INVISIBLE);
                PropertiesUtils.getTv_QuestionTast2().setVisibility(INVISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("waterpoint")) {
            if (buttons.get(0).isChecked()) {
                //Invisible Question TextView
                PropertiesUtils.getTvQuestion311().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQuestion312().setVisibility(INVISIBLE);
                //Invisible View of Question
                PropertiesUtils.getLayoutQuestion311().setVisibility(INVISIBLE);
                PropertiesUtils.getLayoutQuestion312().setVisibility(INVISIBLE);
                //Invisible Other 311
                PropertiesUtils.getTvQuestion311Other().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion311Other().setVisibility(INVISIBLE);
                PropertiesUtils.setiAnswer311(0);
                //INvisble Other 312
                PropertiesUtils.getTvQuestion312Other().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion312Other().setVisibility(INVISIBLE);
                PropertiesUtils.setiAnswer312(0);
                //Set Integer for Answer
                PropertiesUtils.setiAnswer31(0);
            } else {
                //VISIBLE Question TextView
                PropertiesUtils.getTvQuestion311().setVisibility(VISIBLE);
                PropertiesUtils.getTvQuestion312().setVisibility(VISIBLE);
                //VISIBLE View of Question
                PropertiesUtils.getLayoutQuestion311().setVisibility(VISIBLE);
                PropertiesUtils.getLayoutQuestion312().setVisibility(VISIBLE);
                //Invisible Other 311
                PropertiesUtils.getTvQuestion311Other().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion311Other().setVisibility(VISIBLE);
                PropertiesUtils.setiAnswer311(1);
                PropertiesUtils.getTvQuestion312Other().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion312Other().setVisibility(VISIBLE);
                PropertiesUtils.setiAnswer312(1);
                //Set Integer for Answer
                PropertiesUtils.setiAnswer31(1);
            }
        }

        if (fieldName.equalsIgnoreCase("wellflooded")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.get_question20_1().setVisibility(INVISIBLE);
                PropertiesUtils.get_question20_2().setVisibility(INVISIBLE);
                PropertiesUtils.get_spQuestion20_1().setVisibility(INVISIBLE);
                PropertiesUtils.get_layQuestion20_2().setVisibility(INVISIBLE);
                PropertiesUtils.setiAnswer2_2(0);
            } else {
                PropertiesUtils.get_question20_1().setVisibility(VISIBLE);
                PropertiesUtils.get_question20_2().setVisibility(VISIBLE);
                PropertiesUtils.get_spQuestion20_1().setVisibility(VISIBLE);
                PropertiesUtils.get_layQuestion20_2().setVisibility(VISIBLE);
                PropertiesUtils.setiAnswer2_2(1);
            }
        }

        if (fieldName.equalsIgnoreCase("waterqual_monitoring")) {
            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setiAnswer4(1);
                PropertiesUtils.getTvQuestion411().setVisibility(VISIBLE);
                PropertiesUtils.getTvQuestion412().setVisibility(VISIBLE);
                PropertiesUtils.getTvQuestion413().setVisibility(VISIBLE);
                PropertiesUtils.getTvQuestion414().setVisibility(VISIBLE);
                PropertiesUtils.getTvQuestion415().setVisibility(VISIBLE);
                PropertiesUtils.getTvQuestion416().setVisibility(VISIBLE);
                PropertiesUtils.getTvQuestion417().setVisibility(VISIBLE);
                PropertiesUtils.getTvQuestion418().setVisibility(VISIBLE);
                PropertiesUtils.getTvQuestion4119().setVisibility(VISIBLE);

                PropertiesUtils.getEdQuestion411().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion412().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion413().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion414().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion415().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion416().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion417().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion418().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion4119().setVisibility(VISIBLE);

            } else {
                PropertiesUtils.setiAnswer4(0);
                PropertiesUtils.getTvQuestion411().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQuestion412().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQuestion413().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQuestion414().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQuestion415().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQuestion416().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQuestion417().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQuestion418().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQuestion4119().setVisibility(INVISIBLE);

                PropertiesUtils.getEdQuestion411().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion412().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion413().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion414().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion415().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion416().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion417().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion418().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion4119().setVisibility(INVISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("environmentcon")) {

            if (buttons.get(0).isChecked()) {
                PropertiesUtils.setiAnswer32(0);
                PropertiesUtils.getTvQuestion321().setVisibility(INVISIBLE);
                PropertiesUtils.getLayoutQuestion321().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQuestion321Other().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQuestion321Other().setVisibility(INVISIBLE);

            } else {
                PropertiesUtils.setiAnswer32(1);
                PropertiesUtils.getTvQuestion321().setVisibility(VISIBLE);
                PropertiesUtils.getLayoutQuestion321().setVisibility(VISIBLE);
                PropertiesUtils.getTvQuestion321Other().setVisibility(VISIBLE);
                PropertiesUtils.getEdQuestion321Other().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("taste")) {
            if (buttons.get(1).isChecked()) {
                PropertiesUtils.setiAns332(1);
                PropertiesUtils.getTvQues333().setVisibility(VISIBLE);
                PropertiesUtils.getLayoutQues333().setVisibility(VISIBLE);
                PropertiesUtils.getTvQues333Other().setVisibility(VISIBLE);
                PropertiesUtils.getEdQues333Other().setVisibility(VISIBLE);
            } else {
                PropertiesUtils.setiAns332(0);
                PropertiesUtils.getTvQues333().setVisibility(INVISIBLE);
                PropertiesUtils.getLayoutQues333().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQues333Other().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQues333Other().setVisibility(INVISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("quantitywater")) {
            if (buttons.get(2).isChecked()) {
                PropertiesUtils.setiAns33(0);
                PropertiesUtils.setiAns332(0);
                PropertiesUtils.setiAns333(0);
                PropertiesUtils.getTvQues332().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQues333().setVisibility(INVISIBLE);
                PropertiesUtils.getLayoutQues332().setVisibility(INVISIBLE);
                PropertiesUtils.getLayoutQues333().setVisibility(INVISIBLE);
                PropertiesUtils.getTvQues333Other().setVisibility(INVISIBLE);
                PropertiesUtils.getEdQues333Other().setVisibility(INVISIBLE);

            } else {
                PropertiesUtils.setiAns33(1);
                PropertiesUtils.setiAns332(1);
                PropertiesUtils.setiAns333(1);
                PropertiesUtils.getTvQues332().setVisibility(VISIBLE);
                PropertiesUtils.getTvQues333().setVisibility(VISIBLE);
                PropertiesUtils.getLayoutQues332().setVisibility(VISIBLE);
                PropertiesUtils.getLayoutQues333().setVisibility(VISIBLE);
                PropertiesUtils.getTvQues333Other().setVisibility(VISIBLE);
                PropertiesUtils.getEdQues333Other().setVisibility(VISIBLE);
            }
        }

        if (fieldName.equalsIgnoreCase("typedirt")) {
            if (buttons.get(3).isChecked()) {
                PropertiesUtils.set_q20Test(1);
                PropertiesUtils.get_q30Ref().setVisibility(VISIBLE);
            } else {
                PropertiesUtils.set_q20Test(0);
                PropertiesUtils.get_q30Ref().setVisibility(View.INVISIBLE);
            }
        }

        Collect.getInstance().getActivityLogger().logInstanceAction(this, "onCheckedChanged",
                mItems.get((Integer) buttonView.getTag()).getValue(), mPrompt.getIndex());
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        for (RadioButton r : buttons) {
            r.setOnLongClickListener(l);
        }
    }

    @Override
    public void cancelLongPress() {
        super.cancelLongPress();
        for (RadioButton button : this.buttons) {
            button.cancelLongPress();
        }
    }

}
