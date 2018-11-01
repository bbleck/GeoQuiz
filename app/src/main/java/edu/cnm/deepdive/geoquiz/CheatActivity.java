package edu.cnm.deepdive.geoquiz;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

  public static final String EXTRA_ANSWER_IS_TRUE = "edu.cnm.deepdive.geoquiz.answer_is_true";
  private static final String EXTRA_ANSWER_SHOWN = "edu.cnm.deepdive.geoquiz.answer_shown";
  public static final String CHEATS_REMAIN = "cheats_remain";
  public static final String API_LEVEL = "API Level " + VERSION.SDK_INT;

  private boolean mAnswerIsTrue;
  private TextView mAnswerTextView;
  private Button mShowAnswerButton;
  private TextView apiLevel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);
    mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
    mAnswerTextView = findViewById(R.id.answer_text_view);
    mShowAnswerButton = findViewById(R.id.show_answer_button);
    apiLevel = findViewById(R.id.tv_cheat_api_level);
    apiLevel.setText(API_LEVEL);
    if(getIntent().getBooleanExtra(CHEATS_REMAIN, true)){
      mShowAnswerButton.setVisibility(View.VISIBLE);
    }else{
      mShowAnswerButton.setVisibility(View.INVISIBLE);
    }
    mShowAnswerButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if(mAnswerIsTrue){
          mAnswerTextView.setText(R.string.true_button);
        }else{
          mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);
        if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
          int cx = mShowAnswerButton.getWidth() / 2;
          int cy = mShowAnswerButton.getHeight() / 2;
          float radius = mShowAnswerButton.getWidth();
          Animator anim = ViewAnimationUtils
              .createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
          anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              super.onAnimationEnd(animation);
              mShowAnswerButton.setVisibility(View.INVISIBLE);
            }
          });
          anim.start();
        }else{
          mShowAnswerButton.setVisibility(View.INVISIBLE);
        }
      }
    });
  }

  public static Intent newIntent(Context packageContext, boolean answerIsTrue, boolean cheatsRemain){
    Intent intent = new Intent(packageContext, CheatActivity.class);
    intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
    intent.putExtra(CHEATS_REMAIN, cheatsRemain);
    return intent;
  }

  private void setAnswerShownResult(boolean isAnswerShown){
    Intent data = new Intent();
    data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
    setResult(RESULT_OK, data);
  }

  public static boolean wasAnswerShown(Intent result){
    return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
  }


}
