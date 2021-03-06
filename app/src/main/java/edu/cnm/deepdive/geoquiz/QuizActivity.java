package edu.cnm.deepdive.geoquiz;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class QuizActivity extends AppCompatActivity {

  public static final String TAG = "QuizActivity";
  public static final String KEY_INDEX = "index";
  public static final String KEY_CHEATER = "cheater";
  public static final String KEY_CHEATS = "cheats";
  private static final int REQUEST_CHEAT_CODE = 0;

  private boolean mIsCheater;
  private int cheatsAvail = 3;

  private Button mTrueButton;
  private Button mFalseButton;
  private Button mNextButton;
  private Button mPreviousButton;
  private Button mCheatButton;
  private TextView mQuestionTextView;

  private Question[] mQuestionBank = new Question[] {
      new Question(R.string.question_australia,true),
      new Question(R.string.question_oceans, true),
      new Question(R.string.question_mideast, false),
      new Question(R.string.question_africa, false),
      new Question(R.string.question_americas, true),
      new Question(R.string.question_asia, true),
  };

  private int mCurrentIndex = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate(Bundle) called");
    setContentView(R.layout.activity_quiz);
    if (savedInstanceState != null) {
      mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
    }
    mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
    mTrueButton = (Button) findViewById((R.id.true_button));
    mTrueButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(true);
      }
    });
    mFalseButton = (Button) findViewById(R.id.false_button);
    mFalseButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(false);
      }
    });
    mNextButton = (Button) findViewById(R.id.next_button);
    mNextButton.setOnClickListener (new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        mIsCheater = false;
        updateQuestion();
      }
    });
    mPreviousButton = (Button) findViewById(R.id.previous_button);
    mPreviousButton.setOnClickListener (new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(mCurrentIndex==0){
          mCurrentIndex = mQuestionBank.length-1;
        }else{
          mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
        }
        updateQuestion();
      }
    });
    mCheatButton = findViewById(R.id.cheat_button);
    mCheatButton.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue, isCheatAvail());
        startActivityForResult(intent, REQUEST_CHEAT_CODE);
      }
    });
    updateQuestion();
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.d(TAG, "onStart() called");
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d(TAG, "onResume() called");
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.d(TAG, "onPause() called");
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    Log.i(TAG, "onSaveInstanceState");
    savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);
    savedInstanceState.putInt(KEY_CHEATS, cheatsAvail);
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.d(TAG, "onStop() called");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy() called");
  }

  private void updateQuestion() {
    int question = mQuestionBank[mCurrentIndex].getTextResId();
    mQuestionTextView.setText(question);
  }

  private void checkAnswer(boolean userPressedTrue) {
    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
    int messageResId = 0;
    if(mIsCheater){
      messageResId = R.string.judgment_toast;
    }else {
      if (userPressedTrue == answerIsTrue) {
        messageResId = R.string.correct_toast;
      } else {
        messageResId = R.string.incorrect_toast;
      }
    }
      Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.TOP, Gravity.CENTER, 0);
      toast.show();

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if(resultCode != Activity.RESULT_OK){
      return;
    }
    if(requestCode == REQUEST_CHEAT_CODE){
      if(data == null){
        return;
      }
      mIsCheater = CheatActivity.wasAnswerShown(data);
      cheatsAvail--;
    }
  }

  boolean isCheatAvail(){
    return (cheatsAvail > 0);
  }

}