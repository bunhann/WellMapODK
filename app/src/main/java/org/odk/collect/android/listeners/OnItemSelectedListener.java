package org.odk.collect.android.listeners;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.odk.collect.android.R;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.model.Commune;
import org.odk.collect.android.model.District;
import org.odk.collect.android.model.ItemData;
import org.odk.collect.android.model.Province;
import org.odk.collect.android.model.Village;
import org.odk.collect.android.utilities.PropertiesUtils;
import org.opendatakit.httpclientandroidlib.HttpEntity;
import org.opendatakit.httpclientandroidlib.HttpResponse;
import org.opendatakit.httpclientandroidlib.NameValuePair;
import org.opendatakit.httpclientandroidlib.client.HttpClient;
import org.opendatakit.httpclientandroidlib.client.entity.UrlEncodedFormEntity;
import org.opendatakit.httpclientandroidlib.client.methods.HttpPost;
import org.opendatakit.httpclientandroidlib.impl.client.DefaultHttpClient;
import org.opendatakit.httpclientandroidlib.message.BasicNameValuePair;
import org.opendatakit.httpclientandroidlib.params.BasicHttpParams;
import org.opendatakit.httpclientandroidlib.params.HttpConnectionParams;
import org.opendatakit.httpclientandroidlib.params.HttpParams;
import org.opendatakit.httpclientandroidlib.util.EntityUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by oi on 10/16/14.
 */
public class OnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private static final int BROWN = 0xFF936931;
    Context mContext;
    public OnItemSelectedListener(final Context context) {
        mContext = context;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Spinner spinner = (Spinner) parent;
        Object spinnerId = spinner.getId();
        if (spinner.getId() == R.id.sp_Province) {
            String itemData = (String) PropertiesUtils.getSp_province().getSelectedItem();
            if (!itemData.equals("ជ្រើសរើស")) {
                Province mProvince = new Province(mContext).getByNameKh(itemData);
                PropertiesUtils.setSelectedProvince(mProvince);
                setSpinnerAdapter(PropertiesUtils.getSp_district(), "district", mProvince.getProCode());
            }
        } else if (spinner.getId() == R.id.sp_District) {
            String itemData = (String) PropertiesUtils.getSp_district().getSelectedItem();
            if (!itemData.equals("ជ្រើសរើស")) {
                District mDistrict = null;
                try {
                    mDistrict = new District(mContext).getByNameKh(itemData);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                PropertiesUtils.setSelectedDistrict(mDistrict);
                setSpinnerAdapter(PropertiesUtils.getSp_commune(), "commune", mDistrict.getdCode());
            }
        } else if (spinner.getId() == R.id.sp_Commune) {
            String itemData = (String) PropertiesUtils.getSp_commune().getSelectedItem();
            if (!itemData.equals("ជ្រើសរើស")) {
                Commune mCommune = null;
                try {
                    mCommune = new Commune(mContext).getByNameKh(itemData);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                PropertiesUtils.setSelectedCommune(mCommune);
                setSpinnerAdapter(PropertiesUtils.getSp_village(), "village", mCommune.getcCode());
            }
        } else if (spinner.getId() == R.id.sp_Village) {
            String itemData = (String) PropertiesUtils.getSp_village().getSelectedItem();
            if (!itemData.equals("ជ្រើសរើស")) {
                Village mVillage = null;
                try {
                    mVillage = new Village(mContext).getByNameKh(itemData);
                    if(PropertiesUtils.get_villeCode() != null) {
                        String vCode = String.valueOf(mVillage.getvCode());
                        if (vCode.length() < 8) {
                            PropertiesUtils.get_villeCode().setText("0" + vCode);
                        } else {
                            PropertiesUtils.get_villeCode().setText(vCode);

                        }
                        //PropertiesUtils.set_wellId(vCode);
                        DoPost mDoPost = new DoPost(mContext, vCode);
                        mDoPost.execute("");


                        //PropertiesUtils.set_wellId(dWellID);
                        //Log.i("TEST", PropertiesUtils.get_wellId());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                PropertiesUtils.setSelectedVillage(mVillage);

            }else{
                if(PropertiesUtils.get_villeCode() != null) {
                    PropertiesUtils.get_villeCode().setText("");
                }
            }
        }
    }

    private void setSpinnerAdapter(Spinner sp, String mode, int id) {
        //List<String> labels = new ArrayList<String>();
        Hashtable<String, ItemData> labels = new Hashtable<String, ItemData>();
        String[] choices = new String[1];
        choices[0] = "ជ្រើសរើស";
        if (mode.equals("district")) {
            List<District> districtList = null;
            try {
                districtList = new District(mContext).getByProvinceId(id);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int i = 0;
            choices = new String[districtList.size() + 1];
            for (District d : districtList) {
                choices[i] = d.getdNameKh();
                i++;
            }
            choices[districtList.size()] = "ជ្រើសរើស";
        } else if (mode.equals("commune")) {
            List<Commune> communeList = null;
            try {
                communeList = new Commune(mContext).getByDistrictId(id);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int i = 0;
            choices = new String[communeList.size() + 1];
            for (Commune c : communeList) {
                choices[i] = c.getcNameKh();
                i++;
            }
            choices[communeList.size()] = "ជ្រើសរើស";
        } else if (mode.equals("village")) {
            String itemData = (String) PropertiesUtils.getSp_commune().getSelectedItem();
            if (!itemData.equals("ជ្រើសរើស")) {
                List<Village> villageList = null;
                try {
                    villageList = new Village(mContext).getByCommuneId(id);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int i = 0;
                choices = new String[villageList.size() + 1];
                for (Village v : villageList) {
                    choices[i] = v.getvNameKh();
                    i++;
                }
                choices[villageList.size()] = "ជ្រើសរើស";
            }
        }
        // Creating adapter for spinner
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        //KeyValueAdapter dataAdapter = new KeyValueAdapter(mContext, android.R.layout.simple_spinner_item, labels);
        // The spinner requires a custom adapter. It is defined below
        SpinnerAdapter adapter = new SpinnerAdapter(mContext, android.R.layout.simple_spinner_item, choices,
                TypedValue.COMPLEX_UNIT_DIP, Collect.getQuestionFontsize());
        adapter.spinner = sp;
        // Drop down layout style - list view with radio button
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp.setAdapter(adapter);
        sp.setSelection(choices.length - 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        String[] items = new String[]{};
        int textUnit;
        float textSize;
        Spinner spinner;


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

    public class DoPost extends AsyncTask<String, Void, Boolean> {
        Context mContext = null;
        String dVillage;
        Exception exception = null;

        DoPost(Context context, String dVille) {
            mContext = context;
            dVillage = dVille;

        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("villageID", dVillage));
                //Log.i("post_data-------------",dVillage);
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost("http://aggregate.open.org.kh/wellid.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs) {
                });
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity);
                if (dVillage.length() < 8) {
                    dVillage = "0" + dVillage;
                }
                int numWell = Integer.parseInt(result) + 1;
                if (numWell < 10) {
                    PropertiesUtils.set_wellId(dVillage + "_00" + String.valueOf(numWell));
                } else if (numWell > 9 && numWell < 99) {
                    PropertiesUtils.set_wellId(dVillage + "_0" + String.valueOf(numWell));
                } else if (numWell > 99 && numWell < 1000) {
                    PropertiesUtils.set_wellId(dVillage + "_" + String.valueOf(numWell));
                } else {
                    PropertiesUtils.set_wellId(dVillage + "_" + "Error");
                }


                //Log.i("result _____",result + "____");

                //Retrieve the data from the JSON object


            } catch (Exception e) {
                Log.e("ClientServerDemo", "Error:", e);
                exception = e;

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean valid) {


        }
    }
}
