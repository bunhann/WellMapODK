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

import org.javarosa.core.model.SelectChoice;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.data.SelectMultiData;
import org.javarosa.core.model.data.helper.Selection;
import org.javarosa.form.api.FormEntryCaption;
import org.javarosa.form.api.FormEntryPrompt;
import org.javarosa.xpath.expr.XPathFuncExpr;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.external.ExternalDataUtil;
import org.odk.collect.android.external.ExternalSelectChoice;
import org.odk.collect.android.views.MediaLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

/**
 * SelctMultiWidget handles multiple selection fields using checkboxes.
 * 
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */

@SuppressLint("NewApi")
public class SelectMultiWidgetGridLayout extends QuestionWidget {
    private boolean mCheckboxInit = true;
    Vector<SelectChoice> mItems;

    private ArrayList<CheckBox> mCheckboxes;
    private GridLayout glayout ;
    @SuppressWarnings("unchecked")
    public SelectMultiWidgetGridLayout(Context context, FormEntryPrompt prompt) {
        super(context, prompt);
        mPrompt = prompt;
        mCheckboxes = new ArrayList<CheckBox>();

        // SurveyCTO-added support for dynamic select content (from .csv files)
        XPathFuncExpr xPathFuncExpr = ExternalDataUtil.getSearchXPathExpression(prompt.getAppearanceHint());
        if (xPathFuncExpr != null) {
            mItems = ExternalDataUtil.populateExternalChoices(prompt, xPathFuncExpr);
        } else {
            mItems = prompt.getSelectChoices();
        }

        setOrientation(LinearLayout.VERTICAL);

        Vector<Selection> ve = new Vector<Selection>();
        if (prompt.getAnswerValue() != null) {
            ve = (Vector<Selection>) prompt.getAnswerValue().getValue();
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int height = size.y;
        
        int halfScreenWidth = (int)(screenWidth *0.5);
        int quarterScreenWidth = (int)(halfScreenWidth * 0.5);
        
     
        
        glayout = new GridLayout(getContext());
        glayout.setOrientation(0);
        glayout.setColumnCount(2);
        //glayout.setRowCount(2);
        
       int row_index = 0 ;
       int col_index = 0 ;
        
        if (mItems != null) {
            for (int i = 0; i < mItems.size(); i++) {
                // no checkbox group so id by answer + offset
                CheckBox c = new CheckBox(getContext());
                c.setTag(Integer.valueOf(i));
                c.setId(QuestionWidget.newUniqueId());
                c.setText(prompt.getSelectChoiceText(mItems.get(i)));
                c.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mAnswerFontsize);
                c.setFocusable(!prompt.isReadOnly());
                c.setEnabled(!prompt.isReadOnly());
                
                for (int vi = 0; vi < ve.size(); vi++) {
                    // match based on value, not key
                    if (mItems.get(i).getValue().equals(ve.elementAt(vi).getValue())) {
                        c.setChecked(true);
                        break;
                    }

                }
                mCheckboxes.add(c);
                // when clicked, check for readonly before toggling
                c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!mCheckboxInit && mPrompt.isReadOnly()) {
                            if (buttonView.isChecked()) {
                                buttonView.setChecked(false);
                               	Collect.getInstance().getActivityLogger().logInstanceAction(this, "onItemClick.deselect", 
                            			mItems.get((Integer)buttonView.getTag()).getValue(), mPrompt.getIndex());
                            } else {
                                buttonView.setChecked(true);
                               	Collect.getInstance().getActivityLogger().logInstanceAction(this, "onItemClick.select", 
                            			mItems.get((Integer)buttonView.getTag()).getValue(), mPrompt.getIndex());
                            }
                        }
                    }
                });

                String audioURI = null;
                audioURI =
                    prompt.getSpecialFormSelectChoiceText(mItems.get(i),
                        FormEntryCaption.TEXT_FORM_AUDIO);

                String imageURI;
                if (mItems.get(i) instanceof ExternalSelectChoice) {
                    imageURI = ((ExternalSelectChoice) mItems.get(i)).getImage();
                } else {
                    imageURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i), FormEntryCaption.TEXT_FORM_IMAGE);
                }

                String videoURI = null;
                videoURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i), "video");

                String bigImageURI = null;
                bigImageURI = prompt.getSpecialFormSelectChoiceText(mItems.get(i), "big-image");

                MediaLayout mediaLayout = new MediaLayout(getContext());
                mediaLayout.setAVT(prompt.getIndex(), "." + Integer.toString(i), c, audioURI, imageURI, videoURI, bigImageURI);
               // mediaLayout.setAVT(index, selectionDesignator, text, audioURI, imageURI, videoURI, bigImageURI);
               // addView(mediaLayout);
                
                row_index = i/2 +1;
                col_index = i%2;
                


                int orientation = display.getOrientation();
                if(orientation == 1){
                	halfScreenWidth = height/2;
                }else{
                	halfScreenWidth = screenWidth/2;
                }
        
                
                Spec row = GridLayout.spec(row_index);
                Spec col = GridLayout.spec(col_index);
                GridLayout.LayoutParams first = new GridLayout.LayoutParams(row, col);
                first.width = halfScreenWidth;
               // first.height = quarterScreenWidth * 2;
                glayout.addView(mediaLayout,first);
             

                // Last, add the dividing line between elements (except for the last element)
                ImageView divider = new ImageView(getContext());
                divider.setBackgroundResource(android.R.drawable.divider_horizontal_bright);
                if (i != mItems.size() - 1) {
                    if(col_index==0) 
                    	glayout.addView(divider);
                }

            }
        }
        addView(glayout);
        mCheckboxInit = false;

    }


    @Override
    public void clearAnswer() {
    	for ( CheckBox c : mCheckboxes ) {
    		if ( c.isChecked() ) {
    			c.setChecked(false);
    		}
    	}
    }


    @Override
    public IAnswerData getAnswer() {
        Vector<Selection> vc = new Vector<Selection>();
        for ( int i = 0; i < mCheckboxes.size() ; ++i ) {
        	CheckBox c = mCheckboxes.get(i);
        	if ( c.isChecked() ) {
        		vc.add(new Selection(mItems.get(i)));
        	}
        }

        if (vc.size() == 0) {
            return null;
        } else {
            return new SelectMultiData(vc);
        }

    }


    @Override
    public void setFocus(Context context) {
        // Hide the soft keyboard if it's showing.
        InputMethodManager inputManager =
            (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }


    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        for (CheckBox c : mCheckboxes) {
            c.setOnLongClickListener(l);
        }
    }


    @Override
    public void cancelLongPress() {
        super.cancelLongPress();
        for (CheckBox c : mCheckboxes) {
            c.cancelLongPress();
        }
    }

}
