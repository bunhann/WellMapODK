package org.odk.collect.android.utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.odk.collect.android.model.Commune;
import org.odk.collect.android.model.District;
import org.odk.collect.android.model.Province;
import org.odk.collect.android.model.Village;
import org.odk.collect.android.views.ExpandedHeightGridView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by oi on 9/5/14.
 *
 * @author sovannoty_chea
 */
public class PropertiesUtils {

    public static final String HOUSE_HOLD_PATH = "household.path";
    private static final String PATIENT_PATH = "patient.path";
    private static final String ROOT_PATH = "root.path";

    private static Properties properties = new Properties();
    private static File _rootAppDir;
    private static File _patientAppDir;
    private static File _householdAppDir;
    private static Map<String, Integer> _fee = new HashMap<String, Integer>();
    private static String _patientId = null;
    private static String _wellId;

    private static Context mContext;


    //State of Hidden Question 1 = Show, 0 = hide
    private static TextView tv_QuestionText;
    private static TextView tv_QuestionText1;
    private static TextView tv_QuestionText2;
    private static TextView tv_QuestionText3;
    private static TextView tv_QuestionText4;
    private static TextView tv_QuestionText5;
    private static TextView tv_QuestionText6;
    private static TextView tv_QuestionText7;
    private static TextView tv_QuestionText8;
    private static TextView tv_QuestionText9;
    private static TextView tv_QuestionText10;
    private static TextView tv_QuestionText11;
    private static TextView tv_QuestionText12;
    private static TextView tv_QuestionText13;
    private static TextView tv_QuestionText14;
    private static TextView tv_QuestionText15;
    private static TextView tv_QuestionText16;
    private static TextView tv_QuestionText17;
    private static TextView tv_QuestionText18;
    private static TextView tv_QuestionText19;
    private static TextView tv_QuestionText20;
    private static TextView tv_QuestionText21;
    private static TextView tv_QuestionText22;
    private static TextView tv_QuestionText23;
    private static TextView tv_QuestionText24;
    private static TextView tv_QuestionText25;
    private static TextView tv_QuestionText26;
    private static TextView tv_QuestionText50;

    //ImageWidget
    private static int iAnswer2_1 = 1; // 1 Visible, 0 Invisible

    private static LinearLayout _imgLayout; //0 Invisible, 1 Visible
    private static TextView _tv_QuestionImage;

    private static LinearLayout _wellplatform;
    private static TextView _tv_QuestionWellplatform;

    private static LinearLayout _platformdesign;
    private static TextView _tv_QuestionPlatformdesign;

    private static LinearLayout _draindesign;
    private static TextView _tv_QuestionDraindesign;

    private static LinearLayout _flooded;
    private static TextView _tv_QuestionFlooded;

    private static LinearLayout _height;
    private static TextView _tv_QuestionHeight;

    //datewell1
    private static LinearLayout _date_well1Layout;
    private static TextView _tv_Question_date_well1;

    //4.1
    private static LinearLayout _informLayout;
    private static TextView _tv_Question_inform;


    //3.2 Question on Clean and NOt Clean
    private static LinearLayout _typedirtLayout;
    private static TextView _tv_Question_Typedirt;
    private static int iAnswerDirt = 0;

    private static EditText _ed_DirtyOther;
    private static TextView _tv_DirtyOther;
    private static boolean _viewOther = false;
    private static int iAnswerDirtOther = 0;


    //3.3.2 Question on water taste
    private static LinearLayout _taste1Layout;
    private static TextView _tv_Question_Taste1;
    private static EditText _ed_Taste1Other;
    private static TextView _tv_Taste1Other;

    //2.8 Question
    private static TextView _tv_Question2_1_1;
    private static EditText _ed_Answer2_1_1;
    private static int iAnswer2_8 = 0;

    //String Location
    private static String locationGPS;

    //Layout of Radio Taste 1
    private static LinearLayout layoutTaste1;
    private static LinearLayout layoutTaste2;
    private static LinearLayout layoutTaste3;
    private static LinearLayout layoutTaste4;
    private static LinearLayout layoutTaste5;
    private static LinearLayout layoutTaste6;
    private static LinearLayout layoutTaste7;
    private static LinearLayout layoutTaste8;
    private static LinearLayout layoutTaste9;
    private static LinearLayout layoutTaste10;
    private static LinearLayout layoutTaste11;
    private static LinearLayout layoutTaste12;
    private static LinearLayout layoutTaste13;
    private static LinearLayout layoutTaste14;
    private static LinearLayout layoutTaste15;
    private static LinearLayout layoutTaste16;
    private static LinearLayout layoutTaste17;
    private static LinearLayout layoutTaste18;
    private static LinearLayout layoutTaste19;

    private static ExpandedHeightGridView _gridViewHardwareDemaged;

    private static TextView tv_QuestionTast1;
    private static TextView tv_QuestionTast2;
    private static TextView tv_QuestionTast3;
    private static TextView tv_QuestionTast4;
    private static TextView tv_QuestionTast5;
    private static TextView tv_QuestionTast6;
    private static TextView tv_QuestionTast7;
    private static TextView tv_QuestionTast8;
    private static TextView tv_QuestionTast9;
    private static TextView tv_QuestionTast10;
    private static TextView tv_QuestionTast11;
    private static TextView tv_QuestionTast12;
    private static TextView tv_QuestionTast13;
    private static TextView tv_QuestionTast14;
    private static TextView tv_QuestionTast15;
    private static TextView tv_QuestionTast16;
    private static TextView tv_QuestionTast17;
    private static TextView tv_QuestionTast18;
    private static TextView tv_QuestionTast19;
    private static TextView tv_QuestionTast20;
    private static TextView tv_QuestionTast21;
    private static TextView tv_QuestionTast22;
    private static TextView tv_QuestionTast23;
    private static TextView tv_QuestionTast24;
    private static TextView tv_QuestionTast50;


    private static EditText _villeCode;
    private static Province _selectedProvince;
    private static District _selectedDistrict;
    private static Commune _selectedCommune;
    private static Village _selectedVillage;
    private static Spinner sp_province;
    private static Spinner sp_district;
    private static Spinner sp_commune;
    private static Spinner sp_village;
    private static String _newPatientName = null;

    //Set Other for 7 EditText

    private static EditText _q11Ref;
    private static EditText _q12Ref;
    private static EditText _q13Ref;
    private static EditText _q14Ref;
    private static EditText _q15Ref;
    private static EditText _q16Ref;
    private static EditText _q17Ref;
    private static EditText _q18Ref;
    private static EditText _q19Ref;
    private static EditText _q20Ref;
    private static EditText _q21Ref;
    private static EditText _q22Ref;
    private static EditText _q23Ref;
    private static EditText _q24Ref;
    private static EditText _q25Ref;
    private static EditText _q26Ref;
    private static EditText _q27Ref;
    private static EditText _q28Ref;
    private static EditText _q29Ref;
    private static EditText _q30Ref;
    private static EditText _q31Ref;
    private static EditText _q32Ref;
    private static EditText _q33Ref;
    private static EditText _q34Ref;
    private static EditText _q35Ref;
    private static EditText _q36Ref;
    private static EditText _q37Ref;
    private static EditText _q38Ref;
    private static EditText _q50Ref;


    private static EditText _primaryOther;
    private static TextView _questionPrimaryOther;

    private static EditText _otherDamaged;
    private static TextView _questionOtherDamaged;

    private static EditText _diameterOther;
    private static TextView _questionDiameterOther;

    //Question 20, 20.1, 20.2
    private static int iAnswer2_2 = 1; // 1 = Visible, 0 = Invisible

    private static TextView _question20_1;
    private static Spinner _spQuestion20_1;


    private static TextView _question20_2;
    private static LinearLayout _layQuestion20_2;


    //address
    private static String addressProvinceCode;
    private static String addressDistrictCode;
    private static String addressCommuneCode;
    private static String addressVillageCode;


    //Reference Key
    private static int _q1Test = 0;
    private static int _q2Test = 0;
    private static int _q3Test = 0;
    private static int _q4Test = 0;
    private static int _q5Test = 0;
    private static int _q6Test = 0;
    private static int _q7Test = 0;
    private static int _q8Test = 0;
    private static int _q9Test = 0;
    private static int _q10Test = 0;
    private static int _q11Test = 0;
    private static int _q12Test = 0;
    private static int _q13Test = 0;
    private static int _q14Test = 0;
    private static int _q15Test = 0;
    private static int _q16Test = 0;
    private static int _q17Test = 0;
    private static int _q18Test = 0;
    private static int _q19Test = 0;
    private static int _q20Test = 0;
    private static int _q21Test = 0;
    private static int _q22Test = 0;
    private static int _q23Test = 0;
    private static int _q24Test = 0;
    private static int _q25Test = 0;
    private static int _q26Test = 0;
    private static int _q27Test = 0;
    private static int _q28Test = 0;
    private static int _q50Test = 0;


    public static Spinner getSp_province() {
        return sp_province;
    }

    public static void setSp_province(Spinner spProvince) {
        sp_province = spProvince;
    }

    public static Spinner getSp_district() {
        return sp_district;
    }

    public static void setSp_district(Spinner spDistrict) {
        sp_district = spDistrict;
    }

    public static Spinner getSp_commune() {
        return sp_commune;
    }

    public static void setSp_commune(Spinner spCommune) {
        sp_commune = spCommune;
    }

    public static Spinner getSp_village() {
        return sp_village;
    }

    public static void setSp_village(Spinner spVillage) {
        sp_village = spVillage;
    }

    public synchronized static String getNewPatientName() {
        return _newPatientName;
    }

    public synchronized static void setNewPatientName(String _newPatientName) {
        PropertiesUtils._newPatientName = _newPatientName;
    }

    public synchronized static void init(final Context context) {
        mContext = context;
        if (properties.isEmpty()) {
            Resources resources = context.getResources();
            AssetManager assetManager = resources.getAssets();
            InputStream inputStream = null;
            // Read from the /assets directory
            try {
                inputStream = assetManager.open("app.properties");
                properties.load(inputStream);
                System.out.println("The properties are now loaded");
                System.out.println("properties: " + properties);
            } catch (IOException e) {
                System.err.println("Failed to open app property file");
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Failed to open app property file");
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
            if (!properties.isEmpty()) {
                setValue(context);
            }
        }
    }

    private static void setValue(final Context context) {
        final String patientPath = File.separator + (String) properties.get(PropertiesUtils.PATIENT_PATH);
        final String householdPath = File.separator + (String) properties.get(PropertiesUtils.HOUSE_HOLD_PATH);
        setRootAppDir(context.getExternalFilesDir((String) properties.get(PropertiesUtils.ROOT_PATH)));
        File patientDir = new File(_rootAppDir, patientPath);
        File householdDir = new File(_rootAppDir, householdPath);
        FileUtils.createFolder(patientDir.getAbsolutePath());
        FileUtils.createFolder(householdDir.getAbsolutePath());
        setPatientAppDir(patientDir);
        setHouseholdAppDir(householdDir);

    }

    public static File getRootAppDir() {
        return _rootAppDir;
    }

    private static void setRootAppDir(final File rootAppDir) {
        _rootAppDir = rootAppDir;
    }

    public static File getPatientAppDir() {
        return _patientAppDir;
    }

    private static void setPatientAppDir(final File patientAppDir) {
        _patientAppDir = patientAppDir;
    }

    public static File getHouseholdAppDir() {
        return _householdAppDir;
    }

    private static void setHouseholdAppDir(final File householdAppDir) {
        _householdAppDir = householdAppDir;
    }

    public synchronized static Map getFee() {
        return _fee;
    }

    public synchronized static void setFee(final Map fee) {
        _fee = fee;
    }

    public synchronized static String getPatientId() {
        return _patientId;
    }

    public synchronized static void setPatientId(final String patientId) {
        _patientId = patientId;
    }

    public static Province getSelectedProvince() {
        return _selectedProvince;
    }

    public static void setSelectedProvince(Province _selectedProvince) {
        PropertiesUtils._selectedProvince = _selectedProvince;
    }

    public static District getSelectedDistrict() {
        return _selectedDistrict;
    }

    public static void setSelectedDistrict(District _selectedDistrict) {
        PropertiesUtils._selectedDistrict = _selectedDistrict;
    }

    public static Commune getSelectedCommune() {
        return _selectedCommune;
    }

    public static void setSelectedCommune(Commune _selectedCommune) {
        PropertiesUtils._selectedCommune = _selectedCommune;
    }

    public static Village getSelectedVillage() {
        return _selectedVillage;
    }

    public static void setSelectedVillage(Village _selectedVillage) {
        PropertiesUtils._selectedVillage = _selectedVillage;
    }

    public static EditText get_villeCode() {
        return _villeCode;
    }

    public static void set_villeCode(EditText _villeCode) {
        PropertiesUtils._villeCode = _villeCode;
    }

    public static EditText get_q11Ref() {
        return _q11Ref;
    }

    public static void set_q11Ref(EditText _q11Ref) {
        PropertiesUtils._q11Ref = _q11Ref;
    }

    public synchronized static int get_q1Test() {
        return _q1Test;
    }

    public synchronized static void set_q1Test(int _q1Test) {
        PropertiesUtils._q1Test = _q1Test;
    }

    public static int get_q2Test() {
        return _q2Test;
    }

    public static void set_q2Test(int _q2Test) {
        PropertiesUtils._q2Test = _q2Test;
    }

    public static int get_q3Test() {
        return _q3Test;
    }

    public static void set_q3Test(int _q3Test) {
        PropertiesUtils._q3Test = _q3Test;
    }

    public static int get_q4Test() {
        return _q4Test;
    }

    public static void set_q4Test(int _q4Test) {
        PropertiesUtils._q4Test = _q4Test;
    }

    public static int get_q5Test() {
        return _q5Test;
    }

    public static void set_q5Test(int _q5Test) {
        PropertiesUtils._q5Test = _q5Test;
    }

    public static int get_q6Test() {
        return _q6Test;
    }

    public static void set_q6Test(int _q6Test) {
        PropertiesUtils._q6Test = _q6Test;
    }

    public static int get_q7Test() {
        return _q7Test;
    }

    public static void set_q7Test(int _q7Test) {
        PropertiesUtils._q7Test = _q7Test;
    }

    public static void set_q8Test(int _q8Test) {
        PropertiesUtils._q8Test = _q8Test;
    }

    public static int get_q8Test() {
        return _q8Test;
    }

    public static void set_q9Test(int _q9Test) {
        PropertiesUtils._q9Test = _q9Test;
    }

    public static int get_q9Test() {
        return _q9Test;
    }

    public static void set_q10Test(int _q10Test) {
        PropertiesUtils._q10Test = _q10Test;
    }

    public static int get_q10Test() {
        return _q10Test;
    }

    public static void set_q11Test(int _q11Test) {
        PropertiesUtils._q11Test = _q11Test;
    }

    public static int get_q11Test() {
        return _q11Test;
    }

    public static void set_q12Test(int _q12Test) {
        PropertiesUtils._q12Test = _q12Test;
    }

    public static int get_q12Test() {
        return _q12Test;
    }

    public static void set_q13Test(int _q13Test) {
        PropertiesUtils._q13Test = _q13Test;
    }

    public static int get_q13Test() {
        return _q13Test;
    }

    public static void set_q14Test(int _q14Test) {
        PropertiesUtils._q14Test = _q14Test;
    }

    public static int get_q14Test() {
        return _q14Test;
    }

    public static void set_q15Test(int _q15Test) {
        PropertiesUtils._q15Test = _q15Test;
    }

    public static int get_q15Test() {
        return _q15Test;
    }

    public static void set_q16Test(int _q16Test) {
        PropertiesUtils._q16Test = _q16Test;
    }

    public static int get_q16Test() {
        return _q16Test;
    }

    public static void set_q17Test(int _q17Test) {
        PropertiesUtils._q17Test = _q17Test;
    }

    public static int get_q17Test() {
        return _q17Test;
    }

    public static void set_q18Test(int _q18Test) {
        PropertiesUtils._q18Test = _q18Test;
    }

    public static int get_q18Test() {
        return _q18Test;
    }

    public static void set_q19Test(int _q19Test) {
        PropertiesUtils._q19Test = _q19Test;
    }

    public static int get_q19Test() {
        return _q19Test;
    }

    public static EditText get_q12Ref() {
        return _q12Ref;
    }

    public static void set_q12Ref(EditText _q12Ref) {
        PropertiesUtils._q12Ref = _q12Ref;
    }

    public static EditText get_q13Ref() {
        return _q13Ref;
    }

    public static void set_q13Ref(EditText _q13Ref) {
        PropertiesUtils._q13Ref = _q13Ref;
    }

    public static EditText get_q14Ref() {
        return _q14Ref;
    }

    public static void set_q14Ref(EditText _q14Ref) {
        PropertiesUtils._q14Ref = _q14Ref;
    }

    public static EditText get_q15Ref() {
        return _q15Ref;
    }

    public static void set_q15Ref(EditText _q15Ref) {
        PropertiesUtils._q15Ref = _q15Ref;
    }

    public static EditText get_q16Ref() {
        return _q16Ref;
    }

    public static void set_q16Ref(EditText _q16Ref) {
        PropertiesUtils._q16Ref = _q16Ref;
    }

    public static EditText get_q17Ref() {
        return _q17Ref;
    }

    public static void set_q17Ref(EditText _q17Ref) {
        PropertiesUtils._q17Ref = _q17Ref;
    }

    public static EditText get_q18Ref() {
        return _q18Ref;
    }

    public static void set_q18Ref(EditText _q18Ref) {
        PropertiesUtils._q18Ref = _q18Ref;
    }

    public static EditText get_q19Ref() {
        return _q19Ref;
    }

    public static void set_q20Ref(EditText _q20Ref) {
        PropertiesUtils._q20Ref = _q20Ref;
    }

    public static EditText get_q20Ref() {
        return _q20Ref;
    }

    public static void set_q21Ref(EditText _q21Ref) {
        PropertiesUtils._q21Ref = _q21Ref;
    }

    public static EditText get_q21Ref() {
        return _q21Ref;
    }

    public static void set_q22Ref(EditText _q22Ref) {
        PropertiesUtils._q22Ref = _q22Ref;
    }

    public static EditText get_q22Ref() {
        return _q22Ref;
    }

    public static void set_q23Ref(EditText _q23Ref) {
        PropertiesUtils._q23Ref = _q23Ref;
    }

    public static EditText get_q23Ref() {
        return _q23Ref;
    }

    public static void set_q24Ref(EditText _q24Ref) {
        PropertiesUtils._q24Ref = _q24Ref;
    }

    public static EditText get_q24Ref() {
        return _q24Ref;
    }

    public static void set_q25Ref(EditText _q25Ref) {
        PropertiesUtils._q25Ref = _q25Ref;
    }

    public static EditText get_q25Ref() {
        return _q25Ref;
    }

    public static void set_q26Ref(EditText _q26Ref) {
        PropertiesUtils._q26Ref = _q26Ref;
    }

    public static EditText get_q26Ref() {
        return _q26Ref;
    }

    public static void set_q27Ref(EditText _q27Ref) {
        PropertiesUtils._q27Ref = _q27Ref;
    }

    public static EditText get_q27Ref() {
        return _q27Ref;
    }

    public static void set_q28Ref(EditText _q28Ref) {
        PropertiesUtils._q28Ref = _q28Ref;
    }

    public static EditText get_q28Ref() {
        return _q28Ref;
    }

    public static void set_q29Ref(EditText _q29Ref) {
        PropertiesUtils._q29Ref = _q29Ref;
    }

    public static EditText get_q29Ref() {
        return _q29Ref;
    }

    public static void set_q19Ref(EditText _q19Ref) {
        PropertiesUtils._q19Ref = _q19Ref;
    }


    public static String get_wellId() {
        return _wellId;
    }

    public static void set_wellId(String _wellId) {
        PropertiesUtils._wellId = _wellId;
    }

    public static TextView getTv_QuestionText() {
        return tv_QuestionText;
    }

    public static void setTv_QuestionText(TextView tv_QuestionText) {
        PropertiesUtils.tv_QuestionText = tv_QuestionText;
    }

    public static TextView getTv_QuestionText1() {
        return tv_QuestionText1;
    }

    public static void setTv_QuestionText1(TextView tv_QuestionText1) {
        PropertiesUtils.tv_QuestionText1 = tv_QuestionText1;
    }

    public static TextView getTv_QuestionText2() {
        return tv_QuestionText2;
    }

    public static void setTv_QuestionTex2(TextView tv_QuestionText2) {
        PropertiesUtils.tv_QuestionText2 = tv_QuestionText2;
    }

    public static TextView getTv_QuestionText3() {
        return tv_QuestionText3;
    }

    public static void setTv_QuestionTex3(TextView tv_QuestionText3) {
        PropertiesUtils.tv_QuestionText3 = tv_QuestionText3;
    }

    public static TextView getTv_QuestionText4() {
        return tv_QuestionText4;
    }

    public static void setTv_QuestionTex4(TextView tv_QuestionText4) {
        PropertiesUtils.tv_QuestionText4 = tv_QuestionText4;
    }

    public static TextView getTv_QuestionText5() {
        return tv_QuestionText5;
    }

    public static void setTv_QuestionTex5(TextView tv_QuestionText5) {
        PropertiesUtils.tv_QuestionText5 = tv_QuestionText5;
    }

    public static TextView getTv_QuestionText6() {
        return tv_QuestionText6;
    }

    public static void setTv_QuestionTex6(TextView tv_QuestionText6) {
        PropertiesUtils.tv_QuestionText6 = tv_QuestionText6;
    }

    public static TextView getTv_QuestionText7() {
        return tv_QuestionText7;
    }

    public static void setTv_QuestionTex7(TextView tv_QuestionText7) {
        PropertiesUtils.tv_QuestionText7 = tv_QuestionText7;
    }

    public static TextView getTv_QuestionText8() {
        return tv_QuestionText8;
    }

    public static void setTv_QuestionTex8(TextView tv_QuestionText8) {
        PropertiesUtils.tv_QuestionText8 = tv_QuestionText8;
    }

    public static TextView getTv_QuestionText9() {
        return tv_QuestionText9;
    }

    public static void setTv_QuestionTex9(TextView tv_QuestionText9) {
        PropertiesUtils.tv_QuestionText9 = tv_QuestionText9;
    }

    public static TextView getTv_QuestionText10() {
        return tv_QuestionText10;
    }

    public static void setTv_QuestionTex10(TextView tv_QuestionText10) {
        PropertiesUtils.tv_QuestionText10 = tv_QuestionText10;
    }

    public static TextView getTv_QuestionText11() {
        return tv_QuestionText11;
    }

    public static void setTv_QuestionTex11(TextView tv_QuestionText11) {
        PropertiesUtils.tv_QuestionText11 = tv_QuestionText11;
    }

    public static TextView getTv_QuestionText12() {
        return tv_QuestionText12;
    }

    public static void setTv_QuestionTex12(TextView tv_QuestionText12) {
        PropertiesUtils.tv_QuestionText12 = tv_QuestionText12;
    }

    public static TextView getTv_QuestionText13() {
        return tv_QuestionText13;
    }

    public static void setTv_QuestionTex13(TextView tv_QuestionText13) {
        PropertiesUtils.tv_QuestionText13 = tv_QuestionText13;
    }

    public static TextView getTv_QuestionText14() {
        return tv_QuestionText14;
    }

    public static void setTv_QuestionTex14(TextView tv_QuestionText14) {
        PropertiesUtils.tv_QuestionText14 = tv_QuestionText14;
    }

    public static TextView getTv_QuestionText15() {
        return tv_QuestionText15;
    }

    public static void setTv_QuestionTex15(TextView tv_QuestionText15) {
        PropertiesUtils.tv_QuestionText15 = tv_QuestionText15;
    }

    public static TextView getTv_QuestionText16() {
        return tv_QuestionText16;
    }

    public static void setTv_QuestionText16(TextView tv_QuestionText16) {
        PropertiesUtils.tv_QuestionText16 = tv_QuestionText16;
    }

    public static LinearLayout getLayoutTaste16() {
        return layoutTaste16;
    }

    public static void setLayoutTaste16(LinearLayout layoutTaste16) {
        PropertiesUtils.layoutTaste16 = layoutTaste16;
    }

    public static TextView getTv_QuestionTast16() {
        return tv_QuestionTast16;
    }

    public static void setTv_QuestionTast16(TextView tv_QuestionTast16) {
        PropertiesUtils.tv_QuestionTast16 = tv_QuestionTast16;
    }

    public static LinearLayout getLayoutTaste1() {
        return layoutTaste1;
    }

    public static void setLayoutTaste1(LinearLayout layoutTaste1) {
        PropertiesUtils.layoutTaste1 = layoutTaste1;
    }

    public static LinearLayout getLayoutTaste2() {
        return layoutTaste2;
    }

    public static void setLayoutTaste2(LinearLayout layoutTaste2) {
        PropertiesUtils.layoutTaste2 = layoutTaste2;
    }

    public static LinearLayout getLayoutTaste3() {
        return layoutTaste3;
    }

    public static void setLayoutTaste3(LinearLayout layoutTaste3) {
        PropertiesUtils.layoutTaste3 = layoutTaste3;
    }

    public static LinearLayout getLayoutTaste4() {
        return layoutTaste4;
    }

    public static void setLayoutTaste4(LinearLayout layoutTaste4) {
        PropertiesUtils.layoutTaste4 = layoutTaste4;
    }

    public static LinearLayout getLayoutTaste5() {
        return layoutTaste5;
    }

    public static void setLayoutTaste5(LinearLayout layoutTaste5) {
        PropertiesUtils.layoutTaste5 = layoutTaste5;
    }

    public static LinearLayout getLayoutTaste6() {
        return layoutTaste6;
    }

    public static void setLayoutTaste6(LinearLayout layoutTaste6) {
        PropertiesUtils.layoutTaste6 = layoutTaste6;
    }

    public static LinearLayout getLayoutTaste7() {
        return layoutTaste7;
    }

    public static void setLayoutTaste7(LinearLayout layoutTaste7) {
        PropertiesUtils.layoutTaste7 = layoutTaste7;
    }

    public static LinearLayout getLayoutTaste8() {
        return layoutTaste8;
    }

    public static void setLayoutTaste8(LinearLayout layoutTaste8) {
        PropertiesUtils.layoutTaste8 = layoutTaste8;
    }

    public static LinearLayout getLayoutTaste9() {
        return layoutTaste9;
    }

    public static void setLayoutTaste9(LinearLayout layoutTaste9) {
        PropertiesUtils.layoutTaste9 = layoutTaste9;
    }

    public static LinearLayout getLayoutTaste10() {
        return layoutTaste10;
    }

    public static void setLayoutTaste10(LinearLayout layoutTaste10) {
        PropertiesUtils.layoutTaste10 = layoutTaste10;
    }

    public static LinearLayout getLayoutTaste11() {
        return layoutTaste11;
    }

    public static void setLayoutTaste11(LinearLayout layoutTaste11) {
        PropertiesUtils.layoutTaste11 = layoutTaste11;
    }

    public static LinearLayout getLayoutTaste12() {
        return layoutTaste12;
    }

    public static void setLayoutTaste12(LinearLayout layoutTaste12) {
        PropertiesUtils.layoutTaste12 = layoutTaste12;
    }

    public static LinearLayout getLayoutTaste13() {
        return layoutTaste13;
    }

    public static void setLayoutTaste13(LinearLayout layoutTaste13) {
        PropertiesUtils.layoutTaste13 = layoutTaste13;
    }

    public static LinearLayout getLayoutTaste14() {
        return layoutTaste14;
    }

    public static void setLayoutTaste14(LinearLayout layoutTaste14) {
        PropertiesUtils.layoutTaste14 = layoutTaste14;
    }

    public static LinearLayout getLayoutTaste15() {
        return layoutTaste15;
    }

    public static void setLayoutTaste15(LinearLayout layoutTaste15) {
        PropertiesUtils.layoutTaste15 = layoutTaste15;
    }

    public static TextView getTv_QuestionTast1() {
        return tv_QuestionTast1;
    }

    public static void setTv_QuestionTast1(TextView tv_QuestionTast1) {
        PropertiesUtils.tv_QuestionTast1 = tv_QuestionTast1;
    }

    public static TextView getTv_QuestionTast2() {
        return tv_QuestionTast2;
    }

    public static void setTv_QuestionTast2(TextView tv_QuestionTast2) {
        PropertiesUtils.tv_QuestionTast2 = tv_QuestionTast2;
    }

    public static TextView getTv_QuestionTast3() {
        return tv_QuestionTast3;
    }

    public static void setTv_QuestionTast3(TextView tv_QuestionTast3) {
        PropertiesUtils.tv_QuestionTast3 = tv_QuestionTast3;
    }

    public static TextView getTv_QuestionTast4() {
        return tv_QuestionTast4;
    }

    public static void setTv_QuestionTast4(TextView tv_QuestionTast4) {
        PropertiesUtils.tv_QuestionTast4 = tv_QuestionTast4;
    }

    public static TextView getTv_QuestionTast5() {
        return tv_QuestionTast5;
    }

    public static void setTv_QuestionTast5(TextView tv_QuestionTast5) {
        PropertiesUtils.tv_QuestionTast5 = tv_QuestionTast5;
    }

    public static TextView getTv_QuestionTast6() {
        return tv_QuestionTast6;
    }

    public static void setTv_QuestionTast6(TextView tv_QuestionTast6) {
        PropertiesUtils.tv_QuestionTast6 = tv_QuestionTast6;
    }

    public static TextView getTv_QuestionTast7() {
        return tv_QuestionTast7;
    }

    public static void setTv_QuestionTast7(TextView tv_QuestionTast7) {
        PropertiesUtils.tv_QuestionTast7 = tv_QuestionTast7;
    }

    public static TextView getTv_QuestionTast8() {
        return tv_QuestionTast8;
    }

    public static void setTv_QuestionTast8(TextView tv_QuestionTast8) {
        PropertiesUtils.tv_QuestionTast8 = tv_QuestionTast8;
    }

    public static TextView getTv_QuestionTast9() {
        return tv_QuestionTast9;
    }

    public static void setTv_QuestionTast9(TextView tv_QuestionTast9) {
        PropertiesUtils.tv_QuestionTast9 = tv_QuestionTast9;
    }

    public static TextView getTv_QuestionTast10() {
        return tv_QuestionTast10;
    }

    public static void setTv_QuestionTast10(TextView tv_QuestionTast10) {
        PropertiesUtils.tv_QuestionTast10 = tv_QuestionTast10;
    }

    public static TextView getTv_QuestionTast11() {
        return tv_QuestionTast11;
    }

    public static void setTv_QuestionTast11(TextView tv_QuestionTast11) {
        PropertiesUtils.tv_QuestionTast11 = tv_QuestionTast11;
    }

    public static TextView getTv_QuestionTast12() {
        return tv_QuestionTast12;
    }

    public static void setTv_QuestionTast12(TextView tv_QuestionTast12) {
        PropertiesUtils.tv_QuestionTast12 = tv_QuestionTast12;
    }

    public static TextView getTv_QuestionTast13() {
        return tv_QuestionTast13;
    }

    public static void setTv_QuestionTast13(TextView tv_QuestionTast13) {
        PropertiesUtils.tv_QuestionTast13 = tv_QuestionTast13;
    }

    public static TextView getTv_QuestionTast14() {
        return tv_QuestionTast14;
    }

    public static void setTv_QuestionTast14(TextView tv_QuestionTast14) {
        PropertiesUtils.tv_QuestionTast14 = tv_QuestionTast14;
    }

    public static TextView getTv_QuestionTast15() {
        return tv_QuestionTast15;
    }

    public static void setTv_QuestionTast15(TextView tv_QuestionTast15) {
        PropertiesUtils.tv_QuestionTast15 = tv_QuestionTast15;
    }

    public static EditText getPrimaryOther() {
        return _primaryOther;
    }

    public static void setPrimaryOther(EditText primaryOther) {
        PropertiesUtils._primaryOther = primaryOther;
    }

    public static TextView getQuestionPrimaryOther() {
        return _questionPrimaryOther;
    }

    public static void setQuestionPrimaryOther(TextView questionPrimaryOther) {
        PropertiesUtils._questionPrimaryOther = questionPrimaryOther;
    }

    public static EditText getOtherDamaged() {
        return _otherDamaged;
    }

    public static void setOtherDamaged(EditText otherDamaged) {
        PropertiesUtils._otherDamaged = otherDamaged;
    }

    public static TextView getQuestionOtherDamaged() {
        return _questionOtherDamaged;
    }

    public static void setQuestionOtherDamaged(TextView questionOtherDamaged) {
        PropertiesUtils._questionOtherDamaged = questionOtherDamaged;
    }

    public static EditText getDiameterOther() {
        return _diameterOther;
    }

    public static void setDiameterOther(EditText diameterOther) {
        PropertiesUtils._diameterOther = diameterOther;
    }

    public static TextView getQuestionDiameterOther() {
        return _questionDiameterOther;
    }

    public static void setQuestionDiameterOther(TextView questionDiameterOther) {
        PropertiesUtils._questionDiameterOther = questionDiameterOther;
    }

    public synchronized static String getLocationGPS() {
        return locationGPS;
    }

    public synchronized static void setLocationGPS(String locationGPS) {
        PropertiesUtils.locationGPS = locationGPS;
    }

    public synchronized static ExpandedHeightGridView getGridViewHardwareDemaged() {
        return _gridViewHardwareDemaged;
    }

    public static String getHouseHoldPath() {
        return HOUSE_HOLD_PATH;
    }

    public static String getAddressProvinceCode() {
        return addressProvinceCode;
    }

    public static void setAddressProvinceCode(String addressProvinceCode) {
        PropertiesUtils.addressProvinceCode = addressProvinceCode;
    }

    public static String getAddressDistrictCode() {
        return addressDistrictCode;
    }

    public static void setAddressDistrictCode(String addressDistrictCode) {
        PropertiesUtils.addressDistrictCode = addressDistrictCode;
    }

    public static String getAddressCommuneCode() {
        return addressCommuneCode;
    }

    public static void setAddressCommuneCode(String addressCommuneCode) {
        PropertiesUtils.addressCommuneCode = addressCommuneCode;
    }

    public static String getAddressVillageCode() {
        return addressVillageCode;
    }

    public static void setAddressVillageCode(String addressVillageCode) {
        PropertiesUtils.addressVillageCode = addressVillageCode;
    }

    public synchronized static void setGridViewHardwareDemaged(ExpandedHeightGridView gridViewHardwareDemaged) {
        PropertiesUtils._gridViewHardwareDemaged = gridViewHardwareDemaged;
    }

    public static TextView get_question20_1() {
        return _question20_1;
    }

    public static void set_question20_1(TextView Question20_1) {
        PropertiesUtils._question20_1 = Question20_1;
    }

    public static int getiAnswer2_2() {
        return iAnswer2_2;
    }

    public static void setiAnswer2_2(int iAnswer2_2) {
        PropertiesUtils.iAnswer2_2 = iAnswer2_2;
    }

    public static Spinner get_spQuestion20_1() {
        return _spQuestion20_1;
    }

    public static void set_spQuestion20_1(Spinner SpQuestion20_1) {
        PropertiesUtils._spQuestion20_1 = SpQuestion20_1;
    }

    public static TextView get_question20_2() {
        return _question20_2;
    }

    public static void set_question20_2(TextView _question20_2) {
        PropertiesUtils._question20_2 = _question20_2;
    }

    public static LinearLayout get_layQuestion20_2() {
        return _layQuestion20_2;
    }

    public static void set_layQuestion20_2(LinearLayout _layQuestion20_2) {
        PropertiesUtils._layQuestion20_2 = _layQuestion20_2;
    }

    public static int get_q20Test() {
        return _q20Test;
    }

    public static void set_q20Test(int _q20Test) {
        PropertiesUtils._q20Test = _q20Test;
    }

    public static EditText get_q30Ref() {
        return _q30Ref;
    }

    public static void set_q30Ref(EditText _q30Ref) {
        PropertiesUtils._q30Ref = _q30Ref;
    }

    public static TextView getTv_QuestionText17() {
        return tv_QuestionText17;
    }

    public static void setTv_QuestionText17(TextView tv_QuestionText17) {
        PropertiesUtils.tv_QuestionText17 = tv_QuestionText17;
    }

    public static TextView getTv_QuestionTast17() {
        return tv_QuestionTast17;
    }

    public static void setTv_QuestionTast17(TextView tv_QuestionTast17) {
        PropertiesUtils.tv_QuestionTast17 = tv_QuestionTast17;
    }

    public static LinearLayout getLayoutTaste17() {
        return layoutTaste17;
    }

    public static void setLayoutTaste17(LinearLayout layoutTaste17) {
        PropertiesUtils.layoutTaste17 = layoutTaste17;
    }

    public static File get_rootAppDir() {
        return _rootAppDir;
    }

    public static void set_rootAppDir(File _rootAppDir) {
        PropertiesUtils._rootAppDir = _rootAppDir;
    }

    public static TextView getTv_QuestionText18() {
        return tv_QuestionText18;
    }

    public static void setTv_QuestionText18(TextView tv_QuestionText18) {
        PropertiesUtils.tv_QuestionText18 = tv_QuestionText18;
    }

    public static LinearLayout getLayoutTaste18() {
        return layoutTaste18;
    }

    public static void setLayoutTaste18(LinearLayout layoutTaste18) {
        PropertiesUtils.layoutTaste18 = layoutTaste18;
    }

    public static TextView getTv_QuestionTast18() {
        return tv_QuestionTast18;
    }

    public static void setTv_QuestionTast18(TextView tv_QuestionTast18) {
        PropertiesUtils.tv_QuestionTast18 = tv_QuestionTast18;
    }

    public static LinearLayout getLayoutTaste19() {
        return layoutTaste19;
    }

    public static void setLayoutTaste19(LinearLayout layoutTaste19) {
        PropertiesUtils.layoutTaste19 = layoutTaste19;
    }

    public static TextView getTv_QuestionText19() {
        return tv_QuestionText19;
    }

    public static void setTv_QuestionText19(TextView tv_QuestionText19) {
        PropertiesUtils.tv_QuestionText19 = tv_QuestionText19;
    }

    public static TextView getTv_QuestionTast19() {
        return tv_QuestionTast19;
    }

    public static void setTv_QuestionTast19(TextView tv_QuestionTast19) {
        PropertiesUtils.tv_QuestionTast19 = tv_QuestionTast19;
    }

    public static EditText get_q31Ref() {
        return _q31Ref;
    }

    public static void set_q31Ref(EditText _q31Ref) {
        PropertiesUtils._q31Ref = _q31Ref;
    }

    public static int get_q21Test() {
        return _q21Test;
    }

    public static void set_q21Test(int _q21Test) {
        PropertiesUtils._q21Test = _q21Test;
    }

    public static TextView getTv_QuestionText20() {
        return tv_QuestionText20;
    }

    public static void setTv_QuestionText20(TextView tv_QuestionText20) {
        PropertiesUtils.tv_QuestionText20 = tv_QuestionText20;
    }

    public static EditText get_q32Ref() {
        return _q32Ref;
    }

    public static int get_q22Test() {
        return _q22Test;
    }

    public static void set_q22Test(int _q22Test) {
        PropertiesUtils._q22Test = _q22Test;
    }

    public static void set_q32Ref(EditText _q32Ref) {
        PropertiesUtils._q32Ref = _q32Ref;
    }

    public static int get_q23Test() {
        return _q23Test;
    }

    public static void set_q23Test(int _q23Test) {
        PropertiesUtils._q23Test = _q23Test;
    }

    public static EditText get_q33Ref() {
        return _q33Ref;
    }

    public static void set_q33Ref(EditText _q33Ref) {
        PropertiesUtils._q33Ref = _q33Ref;
    }

    public static TextView getTv_QuestionText21() {
        return tv_QuestionText21;
    }

    public static void setTv_QuestionText21(TextView tv_QuestionText21) {
        PropertiesUtils.tv_QuestionText21 = tv_QuestionText21;
    }

    public static LinearLayout getImgLayout() {
        return _imgLayout;
    }

    public static void set_imgLayout(LinearLayout ImgLayout) {
        PropertiesUtils._imgLayout = ImgLayout;
    }

    public static int getiAnswer2_1() {
        return iAnswer2_1;
    }

    public static void setiAnswer2_1(int iAnswer2_1) {
        PropertiesUtils.iAnswer2_1 = iAnswer2_1;
    }

    public synchronized static TextView getQuestionImage() {
        return _tv_QuestionImage;
    }

    public synchronized static void setQuestionImage(TextView QuestionImage) {
        PropertiesUtils._tv_QuestionImage = QuestionImage;
    }

    public static LinearLayout get_typedirtLayout() {
        return _typedirtLayout;
    }

    public static void set_typedirtLayout(LinearLayout TypedirtLayout) {
        PropertiesUtils._typedirtLayout = TypedirtLayout;
    }

    public static TextView get_tv_Question_Typedirt() {
        return _tv_Question_Typedirt;
    }

    public static void set_tv_Question_Typedirt(TextView Question_Typedirt) {
        PropertiesUtils._tv_Question_Typedirt = Question_Typedirt;
    }

    public static TextView get_tv_DirtyOther() {
        return _tv_DirtyOther;
    }

    public static void set_tv_DirtyOther(TextView _tv_DirtyOther) {
        PropertiesUtils._tv_DirtyOther = _tv_DirtyOther;
    }

    public static EditText get_ed_DirtyOther() {
        return _ed_DirtyOther;
    }

    public static void set_ed_DirtyOther(EditText _ed_DirtyOther) {
        PropertiesUtils._ed_DirtyOther = _ed_DirtyOther;
    }

    public static int getiAnswerDirt() {
        return iAnswerDirt;
    }

    public static void setiAnswerDirt(int iAnswerDirt) {
        PropertiesUtils.iAnswerDirt = iAnswerDirt;
    }

    public static boolean isViewOther() {
        return _viewOther;
    }

    public static void setViewOther(boolean ViewOther) {
        PropertiesUtils._viewOther = ViewOther;
    }

    public static String getPatientPath() {
        return PATIENT_PATH;
    }

    public static TextView getTv_QuestionText25() {
        return tv_QuestionText25;
    }

    public static void setTv_QuestionText25(TextView tv_QuestionText25) {
        PropertiesUtils.tv_QuestionText25 = tv_QuestionText25;
    }

    public static TextView getTv_QuestionText24() {
        return tv_QuestionText24;
    }

    public static void setTv_QuestionText24(TextView tv_QuestionText24) {
        PropertiesUtils.tv_QuestionText24 = tv_QuestionText24;
    }

    public static TextView getTv_QuestionText23() {
        return tv_QuestionText23;
    }

    public static void setTv_QuestionText23(TextView tv_QuestionText23) {
        PropertiesUtils.tv_QuestionText23 = tv_QuestionText23;
    }

    public static TextView getTv_QuestionText22() {
        return tv_QuestionText22;
    }

    public static void setTv_QuestionText22(TextView tv_QuestionText22) {
        PropertiesUtils.tv_QuestionText22 = tv_QuestionText22;
    }

    public static EditText get_q34Ref() {
        return _q34Ref;
    }

    public static void set_q34Ref(EditText _q34Ref) {
        PropertiesUtils._q34Ref = _q34Ref;
    }

    public static EditText get_q35Ref() {
        return _q35Ref;
    }

    public static void set_q35Ref(EditText _q35Ref) {
        PropertiesUtils._q35Ref = _q35Ref;
    }

    public static EditText get_q36Ref() {
        return _q36Ref;
    }

    public static void set_q36Ref(EditText _q36Ref) {
        PropertiesUtils._q36Ref = _q36Ref;
    }

    public static EditText get_q37Ref() {
        return _q37Ref;
    }

    public static void set_q37Ref(EditText _q37Ref) {
        PropertiesUtils._q37Ref = _q37Ref;
    }

    public static int get_q24Test() {
        return _q24Test;
    }

    public static void set_q24Test(int _q24Test) {
        PropertiesUtils._q24Test = _q24Test;
    }

    public static int get_q25Test() {
        return _q25Test;
    }

    public static void set_q25Test(int _q25Test) {
        PropertiesUtils._q25Test = _q25Test;
    }

    public static int get_q26Test() {
        return _q26Test;
    }

    public static void set_q26Test(int _q26Test) {
        PropertiesUtils._q26Test = _q26Test;
    }

    public static int get_q27Test() {
        return _q27Test;
    }

    public static void set_q27Test(int _q27Test) {
        PropertiesUtils._q27Test = _q27Test;
    }

    public static int get_q28Test() {
        return _q28Test;
    }

    public static void set_q28Test(int _q28Test) {
        PropertiesUtils._q28Test = _q28Test;
    }

    public static TextView getTv_QuestionText26() {
        return tv_QuestionText26;
    }

    public static void setTv_QuestionText26(TextView tv_QuestionText26) {
        PropertiesUtils.tv_QuestionText26 = tv_QuestionText26;
    }

    public static EditText get_q38Ref() {
        return _q38Ref;
    }

    public static void set_q38Ref(EditText _q38Ref) {
        PropertiesUtils._q38Ref = _q38Ref;
    }

    public static TextView getTv_QuestionTast24() {
        return tv_QuestionTast24;
    }

    public static void setTv_QuestionTast24(TextView tv_QuestionTast24) {
        PropertiesUtils.tv_QuestionTast24 = tv_QuestionTast24;
    }

    public static TextView getTv_QuestionTast23() {
        return tv_QuestionTast23;
    }

    public static void setTv_QuestionTast23(TextView tv_QuestionTast23) {
        PropertiesUtils.tv_QuestionTast23 = tv_QuestionTast23;
    }

    public static TextView getTv_QuestionTast22() {
        return tv_QuestionTast22;
    }

    public static void setTv_QuestionTast22(TextView tv_QuestionTast22) {
        PropertiesUtils.tv_QuestionTast22 = tv_QuestionTast22;
    }

    public static TextView getTv_QuestionTast21() {
        return tv_QuestionTast21;
    }

    public static void setTv_QuestionTast21(TextView tv_QuestionTast21) {
        PropertiesUtils.tv_QuestionTast21 = tv_QuestionTast21;
    }

    public static TextView getTv_QuestionTast20() {
        return tv_QuestionTast20;
    }

    public static void setTv_QuestionTast20(TextView tv_QuestionTast20) {
        PropertiesUtils.tv_QuestionTast20 = tv_QuestionTast20;
    }


    public static LinearLayout get_wellplatform() {
        return _wellplatform;
    }

    public static void set_wellplatform(LinearLayout _wellplatform) {
        PropertiesUtils._wellplatform = _wellplatform;
    }

    public static TextView get_tv_QuestionWellplatform() {
        return _tv_QuestionWellplatform;
    }

    public static void set_tv_QuestionWellplatform(TextView _tv_QuestionWellplatform) {
        PropertiesUtils._tv_QuestionWellplatform = _tv_QuestionWellplatform;
    }

    public static LinearLayout get_platformdesign() {
        return _platformdesign;
    }

    public static void set_platformdesign(LinearLayout _platformdesign) {
        PropertiesUtils._platformdesign = _platformdesign;
    }

    public static TextView get_tv_QuestionPlatformdesign() {
        return _tv_QuestionPlatformdesign;
    }

    public static void set_tv_QuestionPlatformdesign(TextView _tv_QuestionPlatformdesign) {
        PropertiesUtils._tv_QuestionPlatformdesign = _tv_QuestionPlatformdesign;
    }

    public static LinearLayout get_draindesign() {
        return _draindesign;
    }

    public static void set_draindesign(LinearLayout _draindesign) {
        PropertiesUtils._draindesign = _draindesign;
    }

    public static TextView get_tv_QuestionDraindesign() {
        return _tv_QuestionDraindesign;
    }

    public static void set_tv_QuestionDraindesign(TextView _tv_QuestionDraindesign) {
        PropertiesUtils._tv_QuestionDraindesign = _tv_QuestionDraindesign;
    }

    public static LinearLayout get_flooded() {
        return _flooded;
    }

    public static void set_flooded(LinearLayout _flooded) {
        PropertiesUtils._flooded = _flooded;
    }

    public static TextView get_tv_QuestionFlooded() {
        return _tv_QuestionFlooded;
    }

    public static void set_tv_QuestionFlooded(TextView _tv_QuestionFlooded) {
        PropertiesUtils._tv_QuestionFlooded = _tv_QuestionFlooded;
    }

    public static LinearLayout get_height() {
        return _height;
    }

    public static void set_height(LinearLayout _height) {
        PropertiesUtils._height = _height;
    }

    public static TextView get_tv_QuestionHeight() {
        return _tv_QuestionHeight;
    }

    public static void set_tv_QuestionHeight(TextView _tv_QuestionHeight) {
        PropertiesUtils._tv_QuestionHeight = _tv_QuestionHeight;
    }


    public static LinearLayout get_taste1Layout() {
        return _taste1Layout;
    }

    public static void set_taste1Layout(LinearLayout _taste1Layout) {
        PropertiesUtils._taste1Layout = _taste1Layout;
    }

    public static TextView get_tv_Question_Taste1() {
        return _tv_Question_Taste1;
    }

    public static void set_tv_Question_Taste1(TextView _tv_Question_Taste1) {
        PropertiesUtils._tv_Question_Taste1 = _tv_Question_Taste1;
    }

    public static EditText get_ed_Taste1Other() {
        return _ed_Taste1Other;
    }

    public static void set_ed_Taste1Other(EditText _ed_Taste1Other) {
        PropertiesUtils._ed_Taste1Other = _ed_Taste1Other;
    }

    public static TextView get_tv_Taste1Other() {
        return _tv_Taste1Other;
    }

    public static void set_tv_Taste1Other(TextView _tv_Taste1Other) {
        PropertiesUtils._tv_Taste1Other = _tv_Taste1Other;
    }

    public static boolean is_viewOther() {
        return _viewOther;
    }

    public static void set_viewOther(boolean _viewOther) {
        PropertiesUtils._viewOther = _viewOther;
    }

    public static LinearLayout get_informLayout() {
        return _informLayout;
    }

    public static void set_informLayout(LinearLayout _informLayout) {
        PropertiesUtils._informLayout = _informLayout;
    }

    public static TextView get_tv_Question_inform() {
        return _tv_Question_inform;
    }

    public static void set_tv_Question_inform(TextView _tv_Question_inform) {
        PropertiesUtils._tv_Question_inform = _tv_Question_inform;
    }

    public static TextView getTv_QuestionTast50() {
        return tv_QuestionTast50;
    }

    public static void setTv_QuestionTast50(TextView tv_QuestionTast50) {
        PropertiesUtils.tv_QuestionTast50 = tv_QuestionTast50;
    }

    public static EditText get_q50Ref() {
        return _q50Ref;
    }

    public static void set_q50Ref(EditText _q50Ref) {
        PropertiesUtils._q50Ref = _q50Ref;
    }

    public static int get_q50Test() {
        return _q50Test;
    }

    public static void set_q50Test(int _q50Test) {
        PropertiesUtils._q50Test = _q50Test;
    }

    public static TextView getTv_QuestionText50() {
        return tv_QuestionText50;
    }

    public static void setTv_QuestionText50(TextView tv_QuestionText50) {
        PropertiesUtils.tv_QuestionText50 = tv_QuestionText50;
    }

    public static LinearLayout get_date_well1Layout() {
        return _date_well1Layout;
    }

    public static void set_date_well1Layout(LinearLayout _date_well1Layout) {
        PropertiesUtils._date_well1Layout = _date_well1Layout;
    }

    public static TextView get_tv_Question_date_well1() {
        return _tv_Question_date_well1;
    }

    public static void set_tv_Question_date_well1(TextView _tv_Question_date_well1) {
        PropertiesUtils._tv_Question_date_well1 = _tv_Question_date_well1;
    }
    //GetSet 2.1.1


    public static TextView get_tv_Question2_1_1() {
        return _tv_Question2_1_1;
    }

    public static void set_tv_Question2_1_1(TextView _tv_Question2_1_1) {
        PropertiesUtils._tv_Question2_1_1 = _tv_Question2_1_1;
    }

    public static EditText get_ed_Answer2_1_1() {
        return _ed_Answer2_1_1;
    }

    public static void set_ed_Answer2_1_1(EditText _ed_Answer2_1_1) {
        PropertiesUtils._ed_Answer2_1_1 = _ed_Answer2_1_1;
    }

    public static int getiAnswer2_8() {
        return iAnswer2_8;
    }

    public static void setiAnswer2_8(int iAnswer2_8) {
        PropertiesUtils.iAnswer2_8 = iAnswer2_8;
    }

    public static int getiAnswerDirtOther() {
        return iAnswerDirtOther;
    }

    public static void setiAnswerDirtOther(int iAnswerDirtOther) {
        PropertiesUtils.iAnswerDirtOther = iAnswerDirtOther;
    }

    private static int iAnswer4_1_9 = 0;

    public static int getiAnswer4_1_9() {
        return iAnswer4_1_9;
    }

    public static void setiAnswer4_1_9(int iAnswer4_1_9) {
        PropertiesUtils.iAnswer4_1_9 = iAnswer4_1_9;
    }

    private static int iAnswerdamaged = 0;
    private static int iAnswerdamagedother = 0;

    public static int getiAnswerdamaged() {
        return iAnswerdamaged;
    }

    public static void setiAnswerdamaged(int iAnswerdamaged) {
        PropertiesUtils.iAnswerdamaged = iAnswerdamaged;
    }

    public static int getiAnswerdamagedother() {
        return iAnswerdamagedother;
    }

    public static void setiAnswerdamagedother(int iAnswerdamagedother) {
        PropertiesUtils.iAnswerdamagedother = iAnswerdamagedother;
    }

    //3.3.2 and 3.3.3
    public static TextView tvQuestion3_3_2;
    public static LinearLayout LayoutQuestion3_3_2;
    public static TextView tvQuestion3_3_3;
    public static LinearLayout LayoutQuestion3_3_3;
    public static int iAnswer3_3 = 0;
    public static int iAnswer3_3_2 = 0;

    public synchronized static TextView getTvQuestion3_3_2() {
        return tvQuestion3_3_2;
    }

    public synchronized static void setTvQuestion3_3_2(TextView tvQuestion3_3_2) {
        PropertiesUtils.tvQuestion3_3_2 = tvQuestion3_3_2;
    }

    public synchronized static LinearLayout getLayoutQuestion3_3_2() {
        return LayoutQuestion3_3_2;
    }

    public synchronized static void setLayoutQuestion3_3_2(LinearLayout layoutQuestion3_3_2) {
        LayoutQuestion3_3_2 = layoutQuestion3_3_2;
    }

    public synchronized static TextView getTvQuestion3_3_3() {
        return tvQuestion3_3_3;
    }

    public synchronized static void setTvQuestion3_3_3(TextView tvQuestion3_3_3) {
        PropertiesUtils.tvQuestion3_3_3 = tvQuestion3_3_3;
    }

    public synchronized static LinearLayout getLayoutQuestion3_3_3() {
        return LayoutQuestion3_3_3;
    }

    public synchronized static void setLayoutQuestion3_3_3(LinearLayout layoutQuestion3_3_3) {
        LayoutQuestion3_3_3 = layoutQuestion3_3_3;
    }

    public synchronized static int getiAnswer3_3() {
        return iAnswer3_3;
    }

    public synchronized static void setiAnswer3_3(int iAnswer3_3) {
        PropertiesUtils.iAnswer3_3 = iAnswer3_3;
    }

    public synchronized static int getiAnswer3_3_2() {
        return iAnswer3_3_2;
    }

    public synchronized static void setiAnswer3_3_2(int iAnswer3_3_2) {
        PropertiesUtils.iAnswer3_3_2 = iAnswer3_3_2;
    }

    //Other 3.3.3
    private static TextView tvQuestionOther3_3_3;
    private static EditText edQuestion3_3_3;
    private static boolean otherQuestion_3_3_3 = false;

    public synchronized static TextView getTvQuestionOther3_3_3() {
        return tvQuestionOther3_3_3;
    }

    public synchronized static void setTvQuestionOther3_3_3(TextView tvQuestionOther3_3_3) {
        PropertiesUtils.tvQuestionOther3_3_3 = tvQuestionOther3_3_3;
    }

    public synchronized static EditText getEdQuestion3_3_3() {
        return edQuestion3_3_3;
    }

    public synchronized static void setEdQuestion3_3_3(EditText edQuestion3_3_3) {
        PropertiesUtils.edQuestion3_3_3 = edQuestion3_3_3;
    }

    public synchronized static boolean isOtherQuestion_3_3_3() {
        return otherQuestion_3_3_3;
    }

    public synchronized static void setOtherQuestion_3_3_3(boolean otherQuestion_3_3_3) {
        PropertiesUtils.otherQuestion_3_3_3 = otherQuestion_3_3_3;
    }

    //Other Location Well 2.4
    private static TextView tvQuestionOtherLocation;
    private static EditText edAnswerOtherLocation;
    private static int iAnswerOtherLocation = 0; //1 Visible, 0 Invisible

    public synchronized static TextView getTvQuestionOtherLocation() {
        return tvQuestionOtherLocation;
    }

    public synchronized static void setTvQuestionOtherLocation(TextView tvQuestionOtherLocation) {
        PropertiesUtils.tvQuestionOtherLocation = tvQuestionOtherLocation;
    }

    public synchronized static EditText getEdAnswerOtherLocation() {
        return edAnswerOtherLocation;
    }

    public synchronized static void setEdAnswerOtherLocation(EditText edAnswerOtherLocation) {
        PropertiesUtils.edAnswerOtherLocation = edAnswerOtherLocation;
    }

    public synchronized static int getiAnswerOtherLocation() {
        return iAnswerOtherLocation;
    }

    public synchronized static void setiAnswerOtherLocation(int iAnswerOtherLocation) {
        PropertiesUtils.iAnswerOtherLocation = iAnswerOtherLocation;
    }

    //3.1 Question
    private static TextView tvQuestion311;
    private static TextView tvQuestion312;

    private static LinearLayout layoutQuestion311;
    private static ExpandedHeightGridView layoutQuestion312;

    //Other Question 3.1.1
    private static TextView tvQuestion311Other;
    private static EditText edQuestion311Other;
    private static int iAnswer311 = 1; //1 Visible, 0 Invisible

    //Other Question 3.1.2
    private static TextView tvQuestion312Other;
    private static EditText edQuestion312Other;
    private static int iAnswer312 = 1; //1 Visible, 0 Invisible

    private static int iAnswer31 = 1; //1 Visible, 0 Invisible

    public static int getiAnswer31() {
        return iAnswer31;
    }

    public static void setiAnswer31(int iAnswer31) {
        PropertiesUtils.iAnswer31 = iAnswer31;
    }

    public static ExpandedHeightGridView getLayoutQuestion312() {
        return layoutQuestion312;
    }

    public static void setLayoutQuestion312(ExpandedHeightGridView layoutQuestion312) {
        PropertiesUtils.layoutQuestion312 = layoutQuestion312;
    }

    public static LinearLayout getLayoutQuestion311() {
        return layoutQuestion311;
    }

    public static void setLayoutQuestion311(LinearLayout layoutQuestion311) {
        PropertiesUtils.layoutQuestion311 = layoutQuestion311;
    }

    public static TextView getTvQuestion312() {
        return tvQuestion312;
    }

    public static void setTvQuestion312(TextView tvQuestion312) {
        PropertiesUtils.tvQuestion312 = tvQuestion312;
    }

    public static TextView getTvQuestion311() {
        return tvQuestion311;
    }

    public static void setTvQuestion311(TextView tvQuestion311) {
        PropertiesUtils.tvQuestion311 = tvQuestion311;
    }

    public static TextView getTvQuestion311Other() {
        return tvQuestion311Other;
    }

    public static void setTvQuestion311Other(TextView tvQuestion311Other) {
        PropertiesUtils.tvQuestion311Other = tvQuestion311Other;
    }

    public static EditText getEdQuestion311Other() {
        return edQuestion311Other;
    }

    public static void setEdQuestion311Other(EditText edQuestion311Other) {
        PropertiesUtils.edQuestion311Other = edQuestion311Other;
    }

    public static int getiAnswer311() {
        return iAnswer311;
    }

    public static void setiAnswer311(int iAnswer311) {
        PropertiesUtils.iAnswer311 = iAnswer311;
    }

    public static TextView getTvQuestion312Other() {
        return tvQuestion312Other;
    }

    public static void setTvQuestion312Other(TextView tvQuestion312Other) {
        PropertiesUtils.tvQuestion312Other = tvQuestion312Other;
    }

    public static EditText getEdQuestion312Other() {
        return edQuestion312Other;
    }

    public static void setEdQuestion312Other(EditText edQuestion312Other) {
        PropertiesUtils.edQuestion312Other = edQuestion312Other;
    }

    public static int getiAnswer312() {
        return iAnswer312;
    }

    public static void setiAnswer312(int iAnswer312) {
        PropertiesUtils.iAnswer312 = iAnswer312;
    }

    //3.2 Question
    private static int iAnswer32 = 1;
    private static int iAnswer321 = 1;

    private static TextView tvQuestion321Other;
    private static EditText edQuestion321Other;

    private static LinearLayout layoutQuestion321;
    private static TextView tvQuestion321;

    public static int getiAnswer32() {
        return iAnswer32;
    }

    public static void setiAnswer32(int iAnswer32) {
        PropertiesUtils.iAnswer32 = iAnswer32;
    }

    public static int getiAnswer321() {
        return iAnswer321;
    }

    public static void setiAnswer321(int iAnswer321) {
        PropertiesUtils.iAnswer321 = iAnswer321;
    }

    public static TextView getTvQuestion321Other() {
        return tvQuestion321Other;
    }

    public static void setTvQuestion321Other(TextView tvQuestion321Other) {
        PropertiesUtils.tvQuestion321Other = tvQuestion321Other;
    }

    public static EditText getEdQuestion321Other() {
        return edQuestion321Other;
    }

    public static void setEdQuestion321Other(EditText edQuestion321Other) {
        PropertiesUtils.edQuestion321Other = edQuestion321Other;
    }

    public static LinearLayout getLayoutQuestion321() {
        return layoutQuestion321;
    }

    public static void setLayoutQuestion321(LinearLayout layoutQuestion321) {
        PropertiesUtils.layoutQuestion321 = layoutQuestion321;
    }

    public static TextView getTvQuestion321() {
        return tvQuestion321;
    }

    public static void setTvQuestion321(TextView tvQuestion321) {
        PropertiesUtils.tvQuestion321 = tvQuestion321;
    }

    //Section 4
    private static int iAnswer4 = 1;
    private static TextView tvQuestion411;
    private static TextView tvQuestion412;
    private static TextView tvQuestion413;
    private static TextView tvQuestion414;
    private static TextView tvQuestion415;
    private static TextView tvQuestion416;
    private static TextView tvQuestion417;
    private static TextView tvQuestion418;
    private static TextView tvQuestion4119;

    private static EditText edQuestion411;
    private static EditText edQuestion412;
    private static EditText edQuestion413;
    private static EditText edQuestion414;
    private static EditText edQuestion415;
    private static EditText edQuestion416;
    private static EditText edQuestion417;
    private static EditText edQuestion418;
    private static LinearLayout edQuestion4119;

    public static int getiAnswer4() {
        return iAnswer4;
    }

    public static void setiAnswer4(int iAnswer4) {
        PropertiesUtils.iAnswer4 = iAnswer4;
    }

    public static TextView getTvQuestion411() {
        return tvQuestion411;
    }

    public static void setTvQuestion411(TextView tvQuestion411) {
        PropertiesUtils.tvQuestion411 = tvQuestion411;
    }

    public static TextView getTvQuestion412() {
        return tvQuestion412;
    }

    public static void setTvQuestion412(TextView tvQuestion412) {
        PropertiesUtils.tvQuestion412 = tvQuestion412;
    }

    public static TextView getTvQuestion413() {
        return tvQuestion413;
    }

    public static void setTvQuestion413(TextView tvQuestion413) {
        PropertiesUtils.tvQuestion413 = tvQuestion413;
    }

    public static TextView getTvQuestion414() {
        return tvQuestion414;
    }

    public static void setTvQuestion414(TextView tvQuestion414) {
        PropertiesUtils.tvQuestion414 = tvQuestion414;
    }

    public static TextView getTvQuestion415() {
        return tvQuestion415;
    }

    public static void setTvQuestion415(TextView tvQuestion415) {
        PropertiesUtils.tvQuestion415 = tvQuestion415;
    }

    public static TextView getTvQuestion416() {
        return tvQuestion416;
    }

    public static void setTvQuestion416(TextView tvQuestion416) {
        PropertiesUtils.tvQuestion416 = tvQuestion416;
    }

    public static TextView getTvQuestion417() {
        return tvQuestion417;
    }

    public static void setTvQuestion417(TextView tvQuestion417) {
        PropertiesUtils.tvQuestion417 = tvQuestion417;
    }

    public static TextView getTvQuestion418() {
        return tvQuestion418;
    }

    public static void setTvQuestion418(TextView tvQuestion418) {
        PropertiesUtils.tvQuestion418 = tvQuestion418;
    }

    public static EditText getEdQuestion411() {
        return edQuestion411;
    }

    public static void setEdQuestion411(EditText edQuestion411) {
        PropertiesUtils.edQuestion411 = edQuestion411;
    }

    public static EditText getEdQuestion412() {
        return edQuestion412;
    }

    public static void setEdQuestion412(EditText edQuestion412) {
        PropertiesUtils.edQuestion412 = edQuestion412;
    }

    public static EditText getEdQuestion413() {
        return edQuestion413;
    }

    public static void setEdQuestion413(EditText edQuestion413) {
        PropertiesUtils.edQuestion413 = edQuestion413;
    }

    public static EditText getEdQuestion414() {
        return edQuestion414;
    }

    public static void setEdQuestion414(EditText edQuestion414) {
        PropertiesUtils.edQuestion414 = edQuestion414;
    }

    public static EditText getEdQuestion415() {
        return edQuestion415;
    }

    public static void setEdQuestion415(EditText edQuestion415) {
        PropertiesUtils.edQuestion415 = edQuestion415;
    }

    public static EditText getEdQuestion416() {
        return edQuestion416;
    }

    public static void setEdQuestion416(EditText edQuestion416) {
        PropertiesUtils.edQuestion416 = edQuestion416;
    }

    public static EditText getEdQuestion417() {
        return edQuestion417;
    }

    public static void setEdQuestion417(EditText edQuestion417) {
        PropertiesUtils.edQuestion417 = edQuestion417;
    }

    public static EditText getEdQuestion418() {
        return edQuestion418;
    }

    public static void setEdQuestion418(EditText edQuestion418) {
        PropertiesUtils.edQuestion418 = edQuestion418;
    }

    public static TextView getTvQuestion4119() {
        return tvQuestion4119;
    }

    public static void setTvQuestion4119(TextView tvQuestion4119) {
        PropertiesUtils.tvQuestion4119 = tvQuestion4119;
    }

    public static LinearLayout getEdQuestion4119() {
        return edQuestion4119;
    }

    public static void setEdQuestion4119(LinearLayout edQuestion4119) {
        PropertiesUtils.edQuestion4119 = edQuestion4119;
    }

    private static int iAns33 = 1;

    private static TextView tvQues332;
    private static TextView tvQues333;
    private static TextView tvQues333Other;

    private static LinearLayout layoutQues332;
    private static LinearLayout layoutQues333;
    private static EditText edQues333Other;
    private static int iAns332 = 1;
    private static int iAns333 = 1;

    public static int getiAns33() {
        return iAns33;
    }

    public static void setiAns33(int iAns33) {
        PropertiesUtils.iAns33 = iAns33;
    }

    public static TextView getTvQues332() {
        return tvQues332;
    }

    public static void setTvQues332(TextView tvQues332) {
        PropertiesUtils.tvQues332 = tvQues332;
    }

    public static TextView getTvQues333() {
        return tvQues333;
    }

    public static void setTvQues333(TextView tvQues333) {
        PropertiesUtils.tvQues333 = tvQues333;
    }

    public static LinearLayout getLayoutQues332() {
        return layoutQues332;
    }

    public static void setLayoutQues332(LinearLayout layoutQues332) {
        PropertiesUtils.layoutQues332 = layoutQues332;
    }

    public static LinearLayout getLayoutQues333() {
        return layoutQues333;
    }

    public static void setLayoutQues333(LinearLayout layoutQues333) {
        PropertiesUtils.layoutQues333 = layoutQues333;
    }

    public static int getiAns332() {
        return iAns332;
    }

    public static void setiAns332(int iAns332) {
        PropertiesUtils.iAns332 = iAns332;
    }

    public static int getiAns333() {
        return iAns333;
    }

    public static void setiAns333(int iAns333) {
        PropertiesUtils.iAns333 = iAns333;
    }

    public static TextView getTvQues333Other() {
        return tvQues333Other;
    }

    public static void setTvQues333Other(TextView tvQues333Other) {
        PropertiesUtils.tvQues333Other = tvQues333Other;
    }

    public static EditText getEdQues333Other() {
        return edQues333Other;
    }

    public static void setEdQues333Other(EditText edQues33Other) {
        PropertiesUtils.edQues333Other = edQues33Other;
    }


    //Section 1

    private static int iAnswer1RecordingOther = 1;
    private static TextView tvQuestionRecordingOther;
    private static EditText edQuestionRecordingOther;

    public static int getiAnswer1RecordingOther() {
        return iAnswer1RecordingOther;
    }

    public static void setiAnswer1RecordingOther(int iAnswer1RecordingOther) {
        PropertiesUtils.iAnswer1RecordingOther = iAnswer1RecordingOther;
    }

    public static TextView getTvQuestionRecordingOther() {
        return tvQuestionRecordingOther;
    }

    public static void setTvQuestionRecordingOther(TextView tvQuestionRecordingOther) {
        PropertiesUtils.tvQuestionRecordingOther = tvQuestionRecordingOther;
    }

    public static EditText getEdQuestionRecordingOther() {
        return edQuestionRecordingOther;
    }

    public static void setEdQuestionRecordingOther(EditText edQuestionRecordingOther) {
        PropertiesUtils.edQuestionRecordingOther = edQuestionRecordingOther;
    }
}

