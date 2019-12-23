/*
 * Copyright (c) 2017  STMicroelectronics – All rights reserved
 * The STMicroelectronics corporate logo is a trademark of STMicroelectronics
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions
 *   and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of
 *   conditions and the following disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name nor trademarks of STMicroelectronics International N.V. nor any other
 *   STMicroelectronics company nor the names of its contributors may be used to endorse or
 *   promote products derived from this software without specific prior written permission.
 *
 * - All of the icons, pictures, logos and other images that are provided with the source code
 *   in a directory whose title begins with st_images may only be used for internal purposes and
 *   shall not be redistributed to any third party or modified in any way.
 *
 * - Any redistributions in binary form shall not include the capability to display any of the
 *   icons, pictures, logos and other images that are provided with the source code in a directory
 *   whose title begins with st_images.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */

package com.st.BlueMS;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import com.st.BlueMS.demos.AccEvent.AccEventFragment;
import com.st.BlueMS.demos.Audio.DirOfArrival.SourceLocFragment;
import com.st.BlueMS.demos.Audio.SpeechToText.SpeechToTextFragment;
import com.st.BlueMS.demos.Cloud.CloudLogFragment;
import com.st.BlueMS.demos.MotionIntensityFragment;
import com.st.BlueMS.demos.NodeStatus.NodeStatusFragment;
import com.st.BlueMS.demos.PedometerFragment;
import com.st.BlueMS.demos.PlotFeatureFragment;
import com.st.BlueMS.demos.SwitchFragment;
import com.st.BlueMS.demos.memsSensorFusion.MemsSensorFusionFragment;
import com.st.BlueMS.physiobiometrics.H2TFeatureFragment;
import com.st.BlueMS.preference.nucleo.SettingsWithNucleoConfiguration;
import com.st.BlueSTSDK.Node;
import com.st.BlueSTSDK.Utils.ConnectionOption;
import com.st.BlueSTSDK.gui.demos.DemoFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Activity that display all the demo available for the node
 */
public class DemosActivity extends com.st.BlueSTSDK.gui.DemosActivity {

    /**
     * create an intent for start this activity
     *
     * @param c          context used for create the intent
     * @param node       node to use for the demo
     * @param options    options to use during the connection
     * @return intent for start a demo activity that use the node as data source
     */
    public static Intent getStartIntent(Context c, @NonNull Node node, ConnectionOption options) {
        Intent i = new Intent(c, DemosActivity.class);
        setIntentParameters(i, node, options);
        return i;
    }//getStartIntent

    public static Intent getStartIntent(Context c, @NonNull Node node) {
        return  getStartIntent(c,node, ConnectionOption.buildDefault());
    }//getStartIntent

    //@DemoDescriptionAnnotation(name="Firmware Upgrade",
    //        requareAll = {RebootOTAModeFeature.class},
     //       iconRes = com.st.BlueSTSDK.gui.R.drawable.ota_upload_fw )
    public static class StartOtaRebootFragment extends com.st.STM32WB.fwUpgrade.statOtaConfig.StartOtaRebootFragment{
        //empty class redefined just to set the icon res in the annotation
    }

   // @DemoDescriptionAnnotation(name="Led Control",
    //        requareAll = {FeatureSwitchStatus.class,FeatureControlLed.class},
         //   iconRes = com.st.BlueSTSDK.gui.R.drawable.stm32wb_led_on)
    public static class LedButtonControlFragment extends com.st.STM32WB.p2pDemo.LedButtonControlFragment{
        //empty class redefined just to set the icon res in the annotation
    }

    /**
     * List of all the class that extend DemoFragment class, if the board match the requirement
     * for the demo it will displayed
     */
    @SuppressWarnings("unchecked")
    private final static Class<? extends DemoFragment> ALL_DEMOS[] = new Class[]{
            //EnvironmentalSensorsFragment.class,
            H2TFeatureFragment.class,
            MemsSensorFusionFragment.class,
            //FFTAmplitudeFragment.class,
            PlotFeatureFragment.class,
            //SDLogFragment.class,
            //ActivityRecognitionFragment.class,
            //CarryPositionFragment.class,
            //ProximityGestureRecognitionFragment.class,
            //MemsGestureRecognitionFragment.class,
            PedometerFragment.class,
            AccEventFragment.class,
            SwitchFragment.class,
            //BlueVoiceFragment.class,
            //SpeechToTextFragment.class,
            //BeamformingFragment.class,
            SourceLocFragment.class,
            //AudioSceneClassificationFragment.class,
            //HeartRateFragment.class,
            MotionIntensityFragment.class,
            //CompassFragment.class,
            //LevelDemoFragment.class,
            //COSensorDemoFragment.class,
            LedButtonControlFragment.class,
            //StartOtaRebootFragment.class,
            //AIDataLogDemoFragment.class,
            CloudLogFragment.class,
            //PredictiveMaintenanceFragment.class,
            NodeStatusFragment.class
            //FeatureDebugFragment.class
    };

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends DemoFragment>[] getAllDemos() {
        if(getNode().getType() == Node.Type.STEVAL_BCN002V1){
            ArrayList<Class<? extends DemoFragment>> demoList = new ArrayList<>(Arrays.asList(ALL_DEMOS));
            demoList.remove(SpeechToTextFragment.class);
            return demoList.toArray(new Class[demoList.size()]);
        }
        return ALL_DEMOS;
    }

    @Override
    protected boolean enableFwUploading() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demos_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            keepConnectionOpen(true,false);
            startActivity(SettingsWithNucleoConfiguration.getStartIntent(this, getNode()));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
