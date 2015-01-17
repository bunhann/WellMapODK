/*
 * Copyright (C) 2011 University of Washington
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
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyPermission;

import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.form.api.FormEntryPrompt;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.utilities.PropertiesUtils;
import org.odk.collect.android.views.MediaLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public abstract class QuestionWidget extends LinearLayout {

    @SuppressWarnings("unused")
    private final static String t = "QuestionWidget";

    private static int idGenerator = 1211322;

    /**
     * Generate a unique ID to keep Android UI happy when the screen orientation
     * changes.
     *
     * @return
     */
    public static int newUniqueId() {
        return ++idGenerator;
    }

    private LinearLayout.LayoutParams mLayout;
    private LayoutParams qLayout;
    protected FormEntryPrompt mPrompt;

    protected final int mQuestionFontsize;
    protected final int mAnswerFontsize;

    private TextView mQuestionText;
    private MediaLayout mediaLayout;
    private TextView mHelpText;


    public QuestionWidget(Context context, FormEntryPrompt p) {
        super(context);

        mQuestionFontsize = Collect.getQuestionFontsize();
        mAnswerFontsize = mQuestionFontsize + 2;

        mPrompt = p;

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.TOP);
        setPadding(0, 7, 0, 0); // place from the top seng added

//        LinearLayout spacing = new LinearLayout(context);
//		spacing.setPadding(0, 40, 0, 40);
//		addView(spacing);

        mLayout =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayout.setMargins(10, 0, 10, 0);


        qLayout = mLayout;
        addQuestionText(p);
        addHelpText(p);
    }

    public void playAudio() {
        mediaLayout.playAudio();
    }

    public void playVideo() {
        mediaLayout.playVideo();
    }

    public FormEntryPrompt getPrompt() {
        return mPrompt;
    }

    // http://code.google.com/p/android/issues/detail?id=8488
    private void recycleDrawablesRecursive(ViewGroup viewGroup, List<ImageView> images) {

        int childCount = viewGroup.getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = viewGroup.getChildAt(index);
            if (child instanceof ImageView) {
                images.add((ImageView) child);
            } else if (child instanceof ViewGroup) {
                recycleDrawablesRecursive((ViewGroup) child, images);
            }
        }
        viewGroup.destroyDrawingCache();
    }

    // http://code.google.com/p/android/issues/detail?id=8488
    public void recycleDrawables() {
        List<ImageView> images = new ArrayList<ImageView>();
        // collect all the image views
        recycleDrawablesRecursive(this, images);
        for (ImageView imageView : images) {
            imageView.destroyDrawingCache();
            Drawable d = imageView.getDrawable();
            if (d != null && d instanceof BitmapDrawable) {
                imageView.setImageDrawable(null);
                BitmapDrawable bd = (BitmapDrawable) d;
                Bitmap bmp = bd.getBitmap();
                if (bmp != null) {
                    bmp.recycle();
                }
            }
        }
    }

    // Abstract methods
    public abstract IAnswerData getAnswer();


    public abstract void clearAnswer();


    public abstract void setFocus(Context context);


    public abstract void setOnLongClickListener(OnLongClickListener l);

    /**
     * Override this to implement fling gesture suppression (e.g. for embedded WebView treatments).
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return true if the fling gesture should be suppressed
     */
    public boolean suppressFlingGesture(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    /**
     * Add a Views containing the question text, audio (if applicable), and image (if applicable).
     * To satisfy the RelativeLayout constraints, we add the audio first if it exists, then the
     * TextView to fit the rest of the space, then the image if applicable.
     */
    protected void addQuestionText(FormEntryPrompt p) {
        String imageURI = p.getImageText();
        String audioURI = p.getAudioText();
        String videoURI = p.getSpecialFormQuestionText("video");

        // shown when image is clicked
        String bigImageURI = p.getSpecialFormQuestionText("big-image");

        String promptText;
        String pText = p.getLongText();

        //Check The Note Label Question Start With ($)
        if (pText.startsWith("$")) {
            StringBuilder sb = new StringBuilder(pText);
            promptText = sb.deleteCharAt(0).toString();
        } else {
            promptText = pText;
        }

        // Add the text view. Textview always exists, regardless of whether there's text.
        mQuestionText = new TextView(getContext());
        mQuestionText.setText(promptText == null ? "" : promptText);
        mQuestionText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mQuestionFontsize + 3);
        mQuestionText.setTypeface(null, Typeface.BOLD);
        mQuestionText.setPadding(0, 0, 0, 10);
        mQuestionText.setId(QuestionWidget.newUniqueId()); // assign random id
        //seng added
        mQuestionText.setLayoutParams(qLayout);
        // Wrap to the size of the parent view
        mQuestionText.setHorizontallyScrolling(false);

        if (promptText == null || promptText.length() == 0) {
            mQuestionText.setVisibility(GONE);
        }
        String fieldElementReference = p.getFormElement().getBind().getReference().toString();
        String fieldName = getFieldID(fieldElementReference);

        if (fieldName.equalsIgnoreCase("recording_other")) {
            if (PropertiesUtils.getiAnswer1RecordingOther() == 0) {
                mQuestionText.setVisibility(INVISIBLE);
            } else {
                mQuestionText.setVisibility(VISIBLE);
            }
            PropertiesUtils.setTvQuestionRecordingOther(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("otherfunded")) {
            if (PropertiesUtils.get_q2Test() == 0) {
                mQuestionText.setVisibility(INVISIBLE);
            } else mQuestionText.setVisibility(VISIBLE);
            PropertiesUtils.setTv_QuestionText1(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("otherfunded1")) {
            if (PropertiesUtils.getiAnswer28()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQues28Other(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("gps_location")) {
            mQuestionText.setVisibility(INVISIBLE);
        }
        if (fieldName.equalsIgnoreCase("e_coli")) {
            mQuestionText.setVisibility(INVISIBLE);
        }
        if (fieldName.equalsIgnoreCase("total_coliform")) {
            mQuestionText.setVisibility(INVISIBLE);
        }
        if (fieldName.equalsIgnoreCase("fundedbyrehabilitate")){
            mQuestionText.setVisibility(INVISIBLE);
        }
        if (fieldName.equalsIgnoreCase("well_id")){
            mQuestionText.setVisibility(INVISIBLE);
        }
        if (fieldName.equalsIgnoreCase("primaryother")){
            PropertiesUtils.setQuestionPrimaryOther(mQuestionText);
            try{
                if(PropertiesUtils.getPrimaryOther().getVisibility()==VISIBLE)
                    PropertiesUtils.getQuestionPrimaryOther().setVisibility(VISIBLE);
                else PropertiesUtils.getQuestionPrimaryOther().setVisibility(INVISIBLE);
            } catch (Exception e){
                mQuestionText.setVisibility(INVISIBLE);
            }
        }
        if (fieldName.equalsIgnoreCase("diameter_other")){
            if (PropertiesUtils.getiAnswser213()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else  mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion213(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("otherdamaged")){
            if (PropertiesUtils.getiAnswer31()!=0&& PropertiesUtils.getiAnswer312()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else  mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion312Other(mQuestionText);

        }

        //Image View 2.21
        if (fieldName.equalsIgnoreCase("platform_picture")){
            if (PropertiesUtils.getiAnswer2_1()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setQuestionImage(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("wellplatform")){
            if (PropertiesUtils.getiAnswer2_1()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.set_tv_QuestionWellplatform(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("platformdesign")){
            if (PropertiesUtils.getiAnswer2_1()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.set_tv_QuestionPlatformdesign(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("draindesign")){
            if (PropertiesUtils.getiAnswer2_1()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.set_tv_QuestionDraindesign(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("flooded")){
            if (PropertiesUtils.getiAnswer2_1()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.set_tv_QuestionFlooded(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("height")){
            if (PropertiesUtils.getiAnswer2_1()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.set_tv_QuestionHeight(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("typedirt")){
            if (PropertiesUtils.getiAnswer32()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion321(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("typedirt_other")){
            if (PropertiesUtils.getiAnswer32()==1 && PropertiesUtils.getiAnswer321()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion321Other(mQuestionText);
        }

        if(fieldName.equalsIgnoreCase("othertaste")){
           /* if(PropertiesUtils.getiAns33()==1 && PropertiesUtils.getiAns333()==1) {
                mQuestionText.setVisibility(VISIBLE);
        }  else mQuestionText.setVisibility(INVISIBLE);*/
            if (PropertiesUtils.getiAns332()==0){

                    mQuestionText.setVisibility(INVISIBLE);

            } else{
                if (PropertiesUtils.getiAns333()==0){
                    mQuestionText.setVisibility(INVISIBLE);
                } else mQuestionText.setVisibility(VISIBLE);
            }
            PropertiesUtils.setTvQues333Other(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("outflowdrain")) {
            PropertiesUtils.setTv_QuestionTast2(mQuestionText);
            mQuestionText.setVisibility(VISIBLE);
        }

        if (fieldName.equalsIgnoreCase("damaged")) {
            if (PropertiesUtils.getiAnswer31()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion311(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("damagedother")){
            if (PropertiesUtils.getiAnswer31()!=0 && PropertiesUtils.getiAnswer311()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion311Other(mQuestionText);

        }

        if (fieldName.equalsIgnoreCase("yearflooded")) {
            if (PropertiesUtils.getiAnswer2_2()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else  mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTv_QuestionTast4(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("highestflooded")) {
            if (PropertiesUtils.getiAnswer2_2()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else  mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTv_QuestionTast5(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("arsenic")) {
            if (PropertiesUtils.getiAnswer4()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion411(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("otherlocation")){
            PropertiesUtils.setTvQuestionOtherLocation(mQuestionText);
            if (PropertiesUtils.getiAnswerOtherLocation()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
        }

        if (fieldName.equalsIgnoreCase("ph")) {
            if (PropertiesUtils.getiAnswer4()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion412(mQuestionText);
        }

        if(fieldName.equalsIgnoreCase("hardwaredamaged")){
            if (PropertiesUtils.getiAnswer31()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion312(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("free_chlorine")) {
            if (PropertiesUtils.getiAnswer4()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion413(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("nitrate")) {
            if (PropertiesUtils.getiAnswer4()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion414(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("nitrite")) {
            if (PropertiesUtils.getiAnswer4()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion415(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("iron")) {
            if (PropertiesUtils.getiAnswer4()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion416(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("hardness")) {
            if (PropertiesUtils.getiAnswer4()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion417(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("turbidity")) {
            if (PropertiesUtils.getiAnswer4()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion418(mQuestionText);
        }

        if(fieldName.equalsIgnoreCase("inform")){
            if (PropertiesUtils.getiAnswer4()==1){
                mQuestionText.setVisibility(VISIBLE);
            } else mQuestionText.setVisibility(INVISIBLE);
            PropertiesUtils.setTvQuestion4119(mQuestionText);
        }
       if (fieldName.equalsIgnoreCase("date_well1")){

           if (PropertiesUtils.getiAnswer28Date()==0){
               mQuestionText.setVisibility(INVISIBLE);
           } else mQuestionText.setVisibility(VISIBLE);
           PropertiesUtils.setTvQues2111(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("yearflooded")){
            /*PropertiesUtils.set_q18Ref(mAnswer);
            if (PropertiesUtils.get_q8Test() == 0){
                mAnswer.setVisibility(INVISIBLE);
            }else mAnswer.setVisibility(VISIBLE);*/
            PropertiesUtils.set_question20_1(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("highestflooded")){
           /* PropertiesUtils.set_q19Ref(mAnswer);
            if (PropertiesUtils.get_q9Test() == 0){
                mAnswer.setVisibility(INVISIBLE);
            }else mAnswer.setVisibility(VISIBLE);*/
            PropertiesUtils.set_question20_2(mQuestionText);
        }

        if (fieldName.equalsIgnoreCase("supplyseasonal")){
            PropertiesUtils.setTv_QuestionTast18(mQuestionText);
            mQuestionText.setVisibility(VISIBLE);
        }

        if (fieldName.equalsIgnoreCase("taste")){
            if (PropertiesUtils.getiAns33()==0){
                mQuestionText.setVisibility(INVISIBLE);
            } else mQuestionText.setVisibility(VISIBLE);

            PropertiesUtils.setTvQues332(mQuestionText);
        }
        if (fieldName.equalsIgnoreCase("taste1")){
            if (PropertiesUtils.getiAns33()==0){
                mQuestionText.setVisibility(INVISIBLE);
            } else{
                if (PropertiesUtils.getiAns332()==0){
                    mQuestionText.setVisibility(INVISIBLE);
                } else  mQuestionText.setVisibility(VISIBLE);
            }
            PropertiesUtils.setTvQues333(mQuestionText);

        }
        if (fieldName.equalsIgnoreCase("hidden")){
            mQuestionText.setVisibility(INVISIBLE);
        }
        if (fieldName.equalsIgnoreCase("hidden1")){
            mQuestionText.setVisibility(INVISIBLE);
        }

        String appearance = p.getAppearanceHint();
        if (appearance == null) appearance = "";
        appearance = appearance.toLowerCase(Locale.ENGLISH);
        if (appearance.equals("hide")) {
            mQuestionText.setVisibility(INVISIBLE);
        }

        // Create the layout for audio, image, text
        mediaLayout = new MediaLayout(getContext());
        mediaLayout.setAVT(p.getIndex(), "", mQuestionText, audioURI, imageURI, videoURI, bigImageURI);

        addView(mediaLayout, mLayout);
    }


    /**
     * Add a TextView containing the help text.
     */
    private void addHelpText(FormEntryPrompt p) {

        String s = p.getHelpText();

        if (s != null && !s.equals("")) {
            mHelpText = new TextView(getContext());
            mHelpText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mQuestionFontsize - 3);
            mHelpText.setPadding(0, -5, 0, 7);
            // wrap to the widget of view
            mHelpText.setHorizontallyScrolling(false);
            mHelpText.setText(s);
            mHelpText.setTypeface(null, Typeface.ITALIC);

            addView(mHelpText, mLayout);
        }
    }


    /**
     * Every subclassed widget should override this, adding any views they may contain, and calling
     * super.cancelLongPress()
     */
    public void cancelLongPress() {
        super.cancelLongPress();
        if (mQuestionText != null) {
            mQuestionText.cancelLongPress();
        }
        if (mHelpText != null) {
            mHelpText.cancelLongPress();
        }
    }

    //Add by Bunhann
    public static String getFieldID(String refFieldString) {

        String fieldID = "";
        if (null != refFieldString && refFieldString.length() > 0) {
            String[] mySplit = refFieldString.split("/");
            fieldID = mySplit[mySplit.length - 1];
        }
        return fieldID;
    }

}
