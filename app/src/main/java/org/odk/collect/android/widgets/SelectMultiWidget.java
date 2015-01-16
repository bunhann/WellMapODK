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
import org.odk.collect.android.R;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.external.ExternalDataUtil;
import org.odk.collect.android.external.ExternalSelectChoice;
import org.odk.collect.android.utilities.PropertiesUtils;
import org.odk.collect.android.views.MediaLayout;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.TableRow;
import android.widget.GridLayout.Spec;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
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
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

/**
 * SelctMultiWidget handles multiple selection fields using checkboxes.
 * 
 * @author Carl Hartung (carlhartung@gmail.com)
 * @author Yaw Anokwa (yanokwa@gmail.com)
 */

@SuppressLint("NewApi")
public class SelectMultiWidget extends QuestionWidget {
    private boolean mCheckboxInit = true;
    Vector<SelectChoice> mItems;

    private ArrayList<CheckBox> mCheckboxes;
    private LinearLayout ll;
    String fieldName;
    @SuppressWarnings("unchecked")
    public SelectMultiWidget(Context context, FormEntryPrompt prompt) {
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

        String fieldElementReference = prompt.getFormElement().getBind().getReference().toString();
        fieldName = getFieldID(fieldElementReference);


        if (fieldName.equalsIgnoreCase("typedirt")){
            if (PropertiesUtils.getiAnswer32()==1){
                setVisibility(VISIBLE);
            } else setVisibility(INVISIBLE);
            PropertiesUtils.setLayoutQuestion321(this);
        }
        if (fieldName.equalsIgnoreCase("taste1")){
            if (PropertiesUtils.getiAns33()==1 && PropertiesUtils.getiAns332()==1){
                setVisibility(VISIBLE);
            } else setVisibility(INVISIBLE);
            PropertiesUtils.setLayoutQues333(this);
        }
        Vector<Selection> ve = new Vector<Selection>();
        if (prompt.getAnswerValue() != null) {
            ve = (Vector<Selection>) prompt.getAnswerValue().getValue();
        }
       
       
//       int row_index 		= 0 ;
//       int col_index 		= 0 ;
       Boolean isNewRow 	= false;
       Boolean isAddDirect 	= false;
       Boolean isTextView 	= false;
       Boolean isAddDivider = false;
       int itemCount 		= 0;
       
       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,0.5f);
        if (mItems != null) {
            for (int i = 0; i < mItems.size(); i++) {
                // no checkbox group so id by answer + offset
            	View c; 
            	 //Check text of the Choice Start With ($), i must be category
                if(prompt.getSelectChoiceText(mItems.get(i)).startsWith("$")){
                	StringBuilder sb=new StringBuilder(prompt.getSelectChoiceText(mItems.get(i)));
                	String str = sb.deleteCharAt(0).toString();
                	
                	c = new TextView(getContext());
                    ((TextView) c).setText(str);
                    ((TextView) c).setTextSize(TypedValue.COMPLEX_UNIT_DIP, mQuestionFontsize+1);
                    ((TextView) c).setTypeface(null, Typeface.BOLD_ITALIC);
                    c.setPadding(70, 10, 10,10);
                    c.setId(QuestionWidget.newUniqueId()); // assign random id
                    isNewRow = true;
                    itemCount = 0;
                    isTextView = true;
                    mCheckboxes.add(new CheckBox(context));
                }else{ //  checkbox 
                	isNewRow = true;
                	isTextView = false;
                	itemCount++;
                	c = new CheckBox(getContext());
                    c.setTag(Integer.valueOf(i));
                    c.setId(QuestionWidget.newUniqueId());
                    ((CheckBox) c).setText(prompt.getSelectChoiceText(mItems.get(i)));
                    ((CheckBox) c).setTextSize(TypedValue.COMPLEX_UNIT_DIP, mAnswerFontsize);
                    c.setFocusable(!prompt.isReadOnly());
                    c.setEnabled(!prompt.isReadOnly());
                    c.setPadding(10, 10, 10, 10);
                    ((CheckBox) c).setLineSpacing(1, 1.15f);
                    mCheckboxes.add((CheckBox) c);
            
                }
          
                if(!isTextView)
                for (int vi = 0; vi < ve.size(); vi++) {
                    // match based on value, not key
                    if (mItems.get(i).getValue().equals(ve.elementAt(vi).getValue())) {
                        ((CheckBox) c).setChecked(true);
                        break;
                    }

                }
                
                if(!prompt.getSelectChoiceText(mItems.get(i)).startsWith("$")){
	                // when clicked, check for readonly before toggling


	                ((CheckBox) c).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	                    @Override
	                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                             try{
                                    if (fieldName.equalsIgnoreCase("typedirt")&& mItems.get((Integer)buttonView.getTag()).getValue().equalsIgnoreCase("other")){
                                        if (isChecked){
                                            PropertiesUtils.getTvQuestion321Other().setVisibility(VISIBLE);
                                            PropertiesUtils.getEdQuestion321Other().setVisibility(VISIBLE);
                                            PropertiesUtils.setiAnswer321(1);
                                        } else{
                                            PropertiesUtils.getTvQuestion321Other().setVisibility(INVISIBLE);
                                            PropertiesUtils.getEdQuestion321Other().setVisibility(INVISIBLE);
                                            PropertiesUtils.setiAnswer321(0);
                                        }
                                    }
                                    if (fieldName.equalsIgnoreCase("taste1")&& mItems.get((Integer)buttonView.getTag()).getValue().equalsIgnoreCase("other_taste")){

                                        if (isChecked){
                                            PropertiesUtils.setiAns333(1);
                                            PropertiesUtils.getTvQues333Other().setVisibility(VISIBLE);
                                            PropertiesUtils.getEdQues333Other().setVisibility(VISIBLE);
                                        } else{
                                            PropertiesUtils.setiAns333(0);
                                            PropertiesUtils.getTvQues333Other().setVisibility(INVISIBLE);
                                            PropertiesUtils.getEdQues333Other().setVisibility(INVISIBLE);
                                        }

                                 }
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
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
                }

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
                if(isTextView){
                	mediaLayout.setAVT(prompt.getIndex(), "." + Integer.toString(i), (TextView) c, audioURI, imageURI, videoURI, bigImageURI);
                }else{
                	mediaLayout.setAVT(prompt.getIndex(), "." + Integer.toString(i), (CheckBox) c, audioURI, imageURI, videoURI, bigImageURI);
                }
                mediaLayout.setLayoutParams(params);
            
                
//                row_index = i/2 +1;
//                col_index = i%2;
                
                
                if(itemCount==2){
                	isNewRow = false;
                	itemCount = 0;
                }
           
                if(isNewRow) {
                	ll = null; 
                    ll = new LinearLayout(getContext());
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    ll.addView(mediaLayout);
                    isNewRow 		= false;
                    isAddDirect 	= false;
                    isAddDivider 	= false;
                    
                    try {
                    	//if textview (category in choices) or the next element is textView , so must add direct
                    	if(isTextView || prompt.getSelectChoiceText(mItems.get(i+1)).startsWith("$") )
                        	isAddDirect =true;
					} catch (Exception e) {
						isAddDirect = true;
						
					}
                    
                    if(isAddDirect){
                    	addView(ll);
                    	isAddDivider = true;
                    }
                }else{
                	ll.addView(mediaLayout);
                	addView(ll);
                	isAddDivider = true;
                }

                // add the dividing line between elements (except for the last element) but not last
                ImageView divider = new ImageView(getContext());
                divider.setBackgroundResource(android.R.drawable.divider_horizontal_bright);
                int size = mItems.size()/2;
               // if ( (i+1)/2< size && (i+1)%2==0) {
                if(isAddDivider && i<mItems.size()-1)   
                	addView(divider);
                    
               // }
                
            }
        }
      
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
