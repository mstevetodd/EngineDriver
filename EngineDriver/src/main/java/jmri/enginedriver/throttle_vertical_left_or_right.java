/*Copyright (C) 2017 M. Steve Todd mstevetodd@gmail.com

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Original version of the simple throttle is by radsolutions.
 */

package jmri.enginedriver;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.LinkedHashMap;

import jmri.enginedriver.type.max_throttles_current_screen_type;
import jmri.enginedriver.type.throttle_screen_type;
import jmri.enginedriver.type.tick_type;
import jmri.enginedriver.util.VerticalSeekBar;
import jmri.enginedriver.type.slider_type;
import jmri.enginedriver.type.web_view_location_type;

public class throttle_vertical_left_or_right extends throttle {
    static final String activityName = "throttle_vertical_left_or_right";

    protected static final int MAX_SCREEN_THROTTLES = max_throttles_current_screen_type.VERTICAL;
    protected static final int MAX_SCREEN_THROTTLES_LEFT_OR_RIGHT = max_throttles_current_screen_type.VERTICAL_LEFT_OR_RIGHT;
    protected static final int  MAX_SCREEN_THROTTLES_VERTICAL_TABLET_LEFT = max_throttles_current_screen_type.VERTICAL_TABLET;

    private LinearLayout[] lThrottles;
    private LinearLayout[] lUppers;
    private LinearLayout[] lLowers;
    private ScrollView[] svFnBtns;

    protected void removeLoco(int whichThrottle) {
        super.removeLoco(whichThrottle);
        set_function_labels_and_listeners_for_view(whichThrottle);
    }

    @SuppressLint({"Recycle", "SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(threaded_application.applicationName, activityName + ": onCreate(): called");

        mainapp = (threaded_application) this.getApplication();
        mainapp.maxThrottlesCurrentScreen = MAX_SCREEN_THROTTLES;
        mainapp.currentScreenSupportsWebView = true;

        prefs = getSharedPreferences("jmri.enginedriver_preferences", 0);
        prefThrottleScreenType = prefs.getString("prefThrottleScreenType", getApplicationContext().getResources().getString(R.string.prefThrottleScreenTypeDefault));
        switch (prefThrottleScreenType) {
            case "Vertical":
                mainapp.maxThrottlesCurrentScreen = MAX_SCREEN_THROTTLES;
                mainapp.throttleLayoutViewId = R.layout.throttle_vertical;
                break;
            case "Vertical Right":
                mainapp.maxThrottlesCurrentScreen = MAX_SCREEN_THROTTLES_LEFT_OR_RIGHT;
                mainapp.throttleLayoutViewId = R.layout.throttle_vertical_right;
                break;
            case "Tablet Vertical Left":
                mainapp.maxThrottlesCurrentScreen = MAX_SCREEN_THROTTLES_VERTICAL_TABLET_LEFT;
                mainapp.throttleLayoutViewId = R.layout.throttle_vertical_tablet_left;
                break;
            case "Vertical Left":
            default:
                mainapp.maxThrottlesCurrentScreen = MAX_SCREEN_THROTTLES_LEFT_OR_RIGHT;
                mainapp.throttleLayoutViewId = R.layout.throttle_vertical_left;
                break;
        }

        super.onCreate(savedInstanceState);

        if (mainapp.appIsFinishing) { return;}

        for (int throttleIndex = 0; throttleIndex < mainapp.maxThrottlesCurrentScreen; throttleIndex++) {
            switch (throttleIndex) {
                case 0:
                    functionButtonViewGroups[throttleIndex] = findViewById(R.id.function_buttons_table_0);
                    break;
                case 1:
                    functionButtonViewGroups[throttleIndex] = findViewById(R.id.function_buttons_table_1);
                    break;
                case 2:
                    functionButtonViewGroups[throttleIndex] = findViewById(R.id.function_buttons_table_2);
                    break;
                case 3:
                    functionButtonViewGroups[throttleIndex] = findViewById(R.id.function_buttons_table_3);
                    break;
                case 4:
                    functionButtonViewGroups[throttleIndex] = findViewById(R.id.function_buttons_table_4);
                    break;
                case 5:
                    functionButtonViewGroups[throttleIndex] = findViewById(R.id.function_buttons_table_5);
                    break;
            }

        }

        lThrottles = new LinearLayout[mainapp.maxThrottlesCurrentScreen];
        llSetSpeeds = new LinearLayout[mainapp.maxThrottlesCurrentScreen];
        svFnBtns = new ScrollView[mainapp.maxThrottlesCurrentScreen];
        vsbSpeeds = new VerticalSeekBar[mainapp.maxThrottlesCurrentScreen];
        lUppers = new LinearLayout[mainapp.maxThrottlesCurrentScreen];
        lLowers = new LinearLayout[mainapp.maxThrottlesCurrentScreen];

        for (int throttleIndex = 0; throttleIndex < mainapp.maxThrottlesCurrentScreen; throttleIndex++) {
            switch (throttleIndex) {
                default:
                case 0:
                    lThrottles[throttleIndex] = findViewById(R.id.throttle_0);
                    lUppers[throttleIndex] = findViewById(R.id.loco_upper_0);
                    lLowers[throttleIndex] = findViewById(R.id.loco_lower_0);
                    llSetSpeeds[throttleIndex] = findViewById(R.id.throttle_0_SetSpeed);
                    vsbSpeeds[throttleIndex] = findViewById(R.id.speed_0);
                    svFnBtns[throttleIndex] = findViewById(R.id.function_buttons_scroller_0);
                    bPauses[throttleIndex] = findViewById(R.id.button_pause_0);
                    break;
                case 1:
                    lThrottles[throttleIndex] = findViewById(R.id.throttle_1);
                    lUppers[throttleIndex] = findViewById(R.id.loco_upper_1);
                    lLowers[throttleIndex] = findViewById(R.id.loco_lower_1);
                    llSetSpeeds[throttleIndex] = findViewById(R.id.throttle_1_SetSpeed);
                    vsbSpeeds[throttleIndex] = findViewById(R.id.speed_1);
                    svFnBtns[throttleIndex] = findViewById(R.id.function_buttons_scroller_1);
                    bPauses[throttleIndex] = findViewById(R.id.button_pause_1);
                    break;
                case 2:
                    lThrottles[throttleIndex] = findViewById(R.id.throttle_2);
                    lUppers[throttleIndex] = findViewById(R.id.loco_upper_2);
                    lLowers[throttleIndex] = findViewById(R.id.loco_lower_2);
                    llSetSpeeds[throttleIndex] = findViewById(R.id.throttle_2_SetSpeed);
                    vsbSpeeds[throttleIndex] = findViewById(R.id.speed_2);
                    svFnBtns[throttleIndex] = findViewById(R.id.function_buttons_scroller_2);
                    bPauses[throttleIndex] = findViewById(R.id.button_pause_2);
                    break;
                case 3:
                    lThrottles[throttleIndex] = findViewById(R.id.throttle_3);
                    lUppers[throttleIndex] = findViewById(R.id.loco_upper_3);
                    lLowers[throttleIndex] = findViewById(R.id.loco_lower_3);
                    llSetSpeeds[throttleIndex] = findViewById(R.id.throttle_3_SetSpeed);
                    vsbSpeeds[throttleIndex] = findViewById(R.id.speed_3);
                    svFnBtns[throttleIndex] = findViewById(R.id.function_buttons_scroller_3);
                    bPauses[throttleIndex] = findViewById(R.id.button_pause_3);
                    break;
                case 4:
                    lThrottles[throttleIndex] = findViewById(R.id.throttle_4);
                    lUppers[throttleIndex] = findViewById(R.id.loco_upper_4);
                    lLowers[throttleIndex] = findViewById(R.id.loco_lower_4);
                    llSetSpeeds[throttleIndex] = findViewById(R.id.throttle_4_SetSpeed);
                    vsbSpeeds[throttleIndex] = findViewById(R.id.speed_4);
                    svFnBtns[throttleIndex] = findViewById(R.id.function_buttons_scroller_4);
                    bPauses[throttleIndex] = findViewById(R.id.button_pause_4);
                    break;
                case 5:
                    lThrottles[throttleIndex] = findViewById(R.id.throttle_5);
                    lUppers[throttleIndex] = findViewById(R.id.loco_upper_5);
                    lLowers[throttleIndex] = findViewById(R.id.loco_lower_5);
                    llSetSpeeds[throttleIndex] = findViewById(R.id.throttle_5_SetSpeed);
                    vsbSpeeds[throttleIndex] = findViewById(R.id.speed_5);
                    svFnBtns[throttleIndex] = findViewById(R.id.function_buttons_scroller_5);
                    bPauses[throttleIndex] = findViewById(R.id.button_pause_5);
                    break;
            }

            vsbSpeeds[throttleIndex].setTickType(tick_type.TICK_0_100);
            PauseSpeedButtonTouchListener pauseSpeedButtonTouchListener = new PauseSpeedButtonTouchListener(throttleIndex);
            bPauses[throttleIndex].setOnTouchListener(pauseSpeedButtonTouchListener);
        }

        sliderType = slider_type.VERTICAL;
    } // end of onCreate()

    @Override
    public void onPause() {
        super.onPause();
        threaded_application.activityPaused(activityName);
    }

    @Override
    public void onResume() {
        Log.d(threaded_application.applicationName, activityName + ": onResume(): called");
        super.onResume();
        threaded_application.activityResumed(activityName);

        if (mainapp.appIsFinishing) { return;}

        for (int throttleIndex = 0; throttleIndex < mainapp.maxThrottlesCurrentScreen; throttleIndex++) {
            if (throttleIndex < mainapp.numThrottles) {
                lThrottles[throttleIndex].setVisibility(LinearLayout.VISIBLE);
            } else {
                lThrottles[throttleIndex].setVisibility(LinearLayout.GONE);
            }
        }

    } // end of onResume()

    @Override
    protected void getDirectionButtonPrefs() {
        super.getDirectionButtonPrefs();
        super.DIRECTION_BUTTON_LEFT_TEXT = getApplicationContext().getResources().getString(R.string.prefLeftDirectionButtonsShortDefaultValue);
        super.DIRECTION_BUTTON_RIGHT_TEXT = getApplicationContext().getResources().getString(R.string.prefRightDirectionButtonsShortDefaultValue);

        super.prefLeftDirectionButtons = prefs.getString("prefLeftDirectionButtonsShort", getApplicationContext().getResources().getString(R.string.prefLeftDirectionButtonsShortDefaultValue)).trim();
        super.prefRightDirectionButtons = prefs.getString("prefRightDirectionButtonsShort", getApplicationContext().getResources().getString(R.string.prefRightDirectionButtonsShortDefaultValue)).trim();
    }


    protected void set_labels() {
        super.set_labels();
         Log.d(threaded_application.applicationName, activityName + ": set_labels(): starting");

        if (mainapp.appIsFinishing) { return;}

        // avoid NPE by not letting this run too early (reported to Play Store)
        if (tvVols[0] == null) return;

        final int conNomTextSize = 24;
        final double minTextScale = 0.5;
        String bLabel;
        String bLabelPlainText;
        for (int throttleIndex = 0; throttleIndex < mainapp.maxThrottlesCurrentScreen; throttleIndex++) {
            Button b = bSels[throttleIndex];
            if ( (mainapp.consists != null) && (mainapp.consists[throttleIndex] != null)
                    && (mainapp.consists[throttleIndex].isActive()) ) {
                if (!prefShowAddressInsteadOfName) {
                    if (!overrideThrottleNames[throttleIndex].isEmpty()) {
                        bLabel = overrideThrottleNames[throttleIndex];
                        bLabelPlainText = overrideThrottleNames[throttleIndex];
                    } else {
                        bLabel = mainapp.consists[throttleIndex].toHtml();
                        bLabelPlainText = mainapp.consists[throttleIndex].toString();
                    }

//                    bLabel = mainapp.consists[throttleIndex].toString();
//                    bLabelPlainText = mainapp.consists[throttleIndex].toString();
//                    bLabel = mainapp.consists[throttleIndex].toHtml();
                } else {
                    if (overrideThrottleNames[throttleIndex].isEmpty()) {
                        bLabel = mainapp.consists[throttleIndex].formatConsistAddr();
                    } else {
                        bLabel = overrideThrottleNames[throttleIndex];
                    }
                    bLabelPlainText = bLabel;
                }
                bLabel = mainapp.locoAndConsistNamesCleanupHtml(bLabel);
                tvbSelsLabels[throttleIndex].setVisibility(View.GONE);

            } else {
                bLabel = getApplicationContext().getResources().getString(R.string.locoPressToSelect);
                bLabelPlainText = bLabel;
                tvbSelsLabels[throttleIndex].setVisibility(View.VISIBLE);
            }

            double textScale = 1.0;
            int bWidth = b.getWidth(); // scale text if required to fit the textView
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, conNomTextSize);
//            double textWidth = b.getPaint().measureText(bLabel);
            double textWidth = b.getPaint().measureText(bLabelPlainText);
            if (bWidth == 0)
                selectLocoRendered = false;
            else {
                selectLocoRendered = true;
                if (textWidth > 0 && textWidth > bWidth) {
                    textScale = bWidth / textWidth;
                    if (textScale < minTextScale)
                        textScale = minTextScale;
                }
            }
            int textSize = (int) (conNomTextSize * textScale * 0.95);
            if (!prefThrottleScreenType.equals(throttle_screen_type.VERTICAL)) {
                textSize = (int) (conNomTextSize * textScale);
            }
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
//            b.setText(bLabel);
            b.setText(Html.fromHtml(bLabel));
            b.setSelected(false);
            b.setPressed(false);
        }

        if (webView != null) {
            setImmersiveModeOn(webView, false);
        }

        for (int throttleIndex = 0; throttleIndex < mainapp.maxThrottlesCurrentScreen; throttleIndex++) {

            //show speed buttons based on pref
            vsbSpeeds[throttleIndex].setVisibility(View.VISIBLE); //always show as a default
            if (prefs.getBoolean("hide_slider_preference", getResources().getBoolean(R.bool.prefHideSliderDefaultValue))) {
                vsbSpeeds[throttleIndex].setVisibility(View.GONE);
            }
        }

        // update the direction indicators
        showDirectionIndications();


        final DisplayMetrics dm = getResources().getDisplayMetrics();
        // Get the screen's density scale
        final float denScale = dm.density;


        int screenWidth = vThrotScrWrap.getWidth(); // get the width of usable area
        int throttleWidth = screenWidth / mainapp.numThrottles;
        for (int throttleIndex = 0; throttleIndex < mainapp.maxThrottlesCurrentScreen; throttleIndex++) {
            lThrottles[throttleIndex].getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
            lThrottles[throttleIndex].getLayoutParams().width = throttleWidth;
            lThrottles[throttleIndex].requestLayout();
            llSetSpeeds[throttleIndex].requestLayout();
        }

        int screenHeight = vThrotScrWrap.getHeight(); // get the Height of usable area
        int fullScreenHeight = screenHeight;
        if ((toolbar != null) && (!prefThrottleViewImmersiveModeHideToolbar))  {
            titleBar = mainapp.getToolbarHeight(toolbar, statusLine,  screenNameLine);
            if (screenHeight!=0) {
                screenHeight = screenHeight - titleBar;
            }
        }
//        int keepHeight = screenHeight;  // default height
        if (screenHeight == 0) {
            // throttle screen hasn't been drawn yet, so use display metrics for now
            screenHeight = dm.heightPixels - (int) (titleBar * (dm.densityDpi / 160.)); // allow for title bar, etc
            //Log.d(threaded_application.applicationName, activityName + ": set_labels(): vThrotScrWrap.getHeight()=0, new screenHeight=" + screenHeight);
        }

        if (webView != null) {
            setImmersiveModeOn(webView, false);
        }

        // save part the screen for webview
        if (webViewLocation.equals(web_view_location_type.TOP) || webViewLocation.equals(web_view_location_type.BOTTOM)) {
            webViewIsOn = true;
            if (!prefIncreaseWebViewSize) {
                screenHeight *= 0.5; // save half the screen
            } else {
                screenHeight *= 0.6; // save 60% of the screen for web view
            }
            LinearLayout.LayoutParams webViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,fullScreenHeight - titleBar - screenHeight);
            webView.setLayoutParams(webViewParams);
        }

        ImageView myImage = findViewById(R.id.backgroundImgView);
        myImage.getLayoutParams().height = screenHeight;

        int speedButtonHeight = (int) (50 * denScale);

        LinearLayout.LayoutParams stopButtonParams;
        stopButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        int prefVerticalStopButtonMargin = threaded_application.getIntPrefValue(prefs, "prefVerticalStopButtonMargin", "0");
        stopButtonParams.topMargin = Math.max(prefVerticalStopButtonMargin, (int) (speedButtonHeight * 0.5));
        stopButtonParams.bottomMargin = prefVerticalStopButtonMargin;
        stopButtonParams.height = speedButtonHeight;

        if (prefs.getBoolean("hide_slider_preference", getResources().getBoolean(R.bool.prefHideSliderDefaultValue))) {
            speedButtonHeight = (int) ((screenHeight
                    - stopButtonParams.topMargin
                    - stopButtonParams.bottomMargin
                    - (220 * denScale)) / 2);
        }

        for (int throttleIndex = 0; throttleIndex < mainapp.maxThrottlesCurrentScreen; throttleIndex++) {
            //show speed buttons based on pref
            if (prefs.getBoolean("display_speed_arrows_buttons", false)) {
                bLSpds[throttleIndex].setVisibility(View.VISIBLE);
                bRSpds[throttleIndex].setVisibility(View.VISIBLE);

                bLSpds[throttleIndex].getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                bLSpds[throttleIndex].getLayoutParams().height = speedButtonHeight;
                bLSpds[throttleIndex].requestLayout();
                bRSpds[throttleIndex].getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                bRSpds[throttleIndex].getLayoutParams().height = speedButtonHeight;
                bRSpds[throttleIndex].requestLayout();
            } else {
                bLSpds[throttleIndex].setVisibility(View.GONE);
                bRSpds[throttleIndex].setVisibility(View.GONE);
            }
            //bLSpds[throttleIndex].setText(speedButtonLeftText);
            //bRSpds[throttleIndex].setText(speedButtonRightText);

//            bStops[throttleIndex].getLayoutParams().height = (int) (speedButtonHeight * 0.8);
            bStops[throttleIndex].setLayoutParams(stopButtonParams);
        }

        for (int throttleIndex = 0; throttleIndex < mainapp.maxThrottlesCurrentScreen; throttleIndex++) {
            // set height of each function button area
            svFnBtns[throttleIndex].getLayoutParams().height = screenHeight - lUppers[throttleIndex].getHeight();
            svFnBtns[throttleIndex].requestLayout();
            lLowers[throttleIndex].getLayoutParams().height = screenHeight - lUppers[throttleIndex].getHeight();
            lLowers[throttleIndex].requestLayout();

            // update throttle slider top/bottom
//            tops[throttleIndex] = llThrottleLayouts[throttleIndex].getTop() + sbs[throttleIndex].getTop() + bSels[throttleIndex].getHeight() + bFwds[throttleIndex].getHeight();
//            bottoms[throttleIndex] = llThrottleLayouts[throttleIndex].getTop() + sbs[throttleIndex].getBottom() + bSels[throttleIndex].getHeight() + bFwds[throttleIndex].getHeight();

            int[] location = new int[2];
            throttleOverlay.getLocationOnScreen(location);
            int ovx = location[0];
            int ovy = location[1];

            location = new int[2];
            vsbSpeeds[throttleIndex].getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];

            sliderTopLeftX[throttleIndex] = x - ovx;
            sliderTopLeftY[throttleIndex] = y - ovy;
            sliderBottomRightX[throttleIndex] = x + vsbSpeeds[throttleIndex].getWidth() - ovx;
            sliderBottomRightY[throttleIndex] = y + vsbSpeeds[throttleIndex].getHeight() - ovy;

//            Log.d(threaded_application.applicationName, activityName + ": set_labels(): slider: " + throttleIndex + " Top: " + sliderTopLeftX[throttleIndex] + ", " + sliderTopLeftY[throttleIndex]
//                    + " Bottom: " + sliderBottomRightX[throttleIndex] + ", " + sliderBottomRightY[throttleIndex]);

        }



//        // update the state of each function button based on shared variable
        for (int throttleIndex = 0; throttleIndex < mainapp.maxThrottlesCurrentScreen; throttleIndex++) {
            setAllFunctionStates(throttleIndex);
        }

        // Log.d(threaded_application.applicationName, activityName + ": set_labels() end");

    }

    @Override
    void enableDisableButtons(int whichThrottle, boolean forceDisable) {
        boolean newEnabledState = false;
        // avoid index and null crashes
        if (mainapp.consists == null || whichThrottle >= mainapp.consists.length
                || bFwds[whichThrottle] == null) {
            return;
        }
        if (!forceDisable) { // avoid index crash, but may simply push to next line
            newEnabledState = mainapp.consists[whichThrottle].isActive(); // set false if lead loco is not assigned
        }

        super.enableDisableButtons(whichThrottle, forceDisable);

    } // end of enableDisableButtons

    // helper function to enable/disable all children for a group
    @Override
    void enableDisableButtonsForView(ViewGroup vg, boolean newEnabledState) {
        // Log.d(threaded_application.applicationName, activityName + ": enableDisableButtonsForView " + newEnabledState);

        if (vg == null) { return;}
        if (mainapp.appIsFinishing) { return;}

        ViewGroup r; // row
        Button b; // button
        for (int i = 0; i < vg.getChildCount(); i++) {
            r = (ViewGroup) vg.getChildAt(i);
            for (int j = 0; j < r.getChildCount(); j++) {
                b = (Button) r.getChildAt(j);
                b.setEnabled(newEnabledState);
            }
        }
    } // enableDisableButtonsForView

    // update the appearance of all function buttons
    @Override
    void setAllFunctionStates(int whichThrottle) {
        // Log.d(threaded_application.applicationName, activityName + ": set_function_states()");

        if (mainapp.appIsFinishing) { return;}

        LinkedHashMap<Integer, Button> fMap;
        fMap = functionMaps[whichThrottle];

        for (Integer f : fMap.keySet()) {
            set_function_state(whichThrottle, f);
        }
    }


    // update a function button appearance based on its state
    @Override
    void set_function_state(int whichThrottle, int function) {
        // Log.d(threaded_application.applicationName, activityName + ": set_function_request()");

        Button b;
        boolean[] fs;   // copy of this throttle's function state array
        b = functionMaps[whichThrottle].get(function);
        fs = mainapp.function_states[whichThrottle];

        if (b != null && fs != null) {
            if (fs[function]) {
                b.setTypeface(null, Typeface.ITALIC + Typeface.BOLD);
                b.setPressed(true);
            } else {
                b.setTypeface(null, Typeface.NORMAL);
                b.setPressed(false);
            }
        }
    }
}