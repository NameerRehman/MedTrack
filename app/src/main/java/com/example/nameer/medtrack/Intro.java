package com.example.nameer.medtrack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class Intro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_intro);


        // Instead of fragments, you can also use our default slide.
        // Just create a `SliderPage` and provide title, description, background and image.
        // AppIntro will do the rest.
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle("Welcome to MedTrak");
        sliderPage.setDescription("MedTrak makes it easy to track your health, well being, and medications. All in one place.");
        sliderPage.setImageDrawable(R.drawable.ic_icon);
        sliderPage.setBgColor(Color.parseColor("#3F51B5"));
        addSlide(AppIntroFragment.newInstance(sliderPage));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle("Add Medications");
        sliderPage2.setDescription("Fill in medication name and various fields. Document their effects and usage through the notepad. ");
        sliderPage2.setImageDrawable(R.drawable.ic_addmed);
        sliderPage2.setBgColor(Color.parseColor("#3F51B5"));
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle("Track your Medications");
        sliderPage3.setDescription("Control how you see them. Sort through your medication history through a variety of filters");
        sliderPage3.setImageDrawable(R.drawable.ic_trackmeds);
        sliderPage3.setBgColor(Color.parseColor("#3F51B5"));
        addSlide(AppIntroFragment.newInstance(sliderPage3));

        SliderPage sliderPage4 = new SliderPage();
        sliderPage4.setTitle("Calendar");
        sliderPage4.setDescription("Track day-to-day symptoms, moods, and notes in the calendar");
        sliderPage4.setImageDrawable(R.drawable.ic_calendar);
        sliderPage4.setBgColor(Color.parseColor("#3F51B5"));
        addSlide(AppIntroFragment.newInstance(sliderPage4));

        SliderPage sliderPage5 = new SliderPage();
        sliderPage5.setTitle("You're All Set!");
        sliderPage5.setDescription("Use the Med Tracker and Calendar to refer back to important notes when discussing your health with your doctor!");
        sliderPage5.setImageDrawable(R.drawable.check);
        sliderPage5.setBgColor(Color.parseColor("#3F51B5"));
        addSlide(AppIntroFragment.newInstance(sliderPage5));

        /*
        Fragment slide1 = new IntroSlide1();
        Fragment slide2 = new IntroSlide2();

        addSlide(slide1);
        addSlide(slide2);
        //addSlide(third_fragment);
        //addSlide(fourth_fragment);
*/

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#303F9F"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent i = new Intent(Intro.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
    Intent i = new Intent(Intro.this, MainActivity.class);
    startActivity(i);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }


}