package edu.cnm.deepdive.geoquiz;

public class Question {

  private int mTextResId;
  private boolean mAnswerTrue;

  public int getTextResId() {
    return mTextResId;
  }

  public boolean isAnswerTrue() {
    return mAnswerTrue;
  }

  public void setTextResId(int mTextResId) {
    this.mTextResId = mTextResId;
  }

  public void setAnswerTrue(boolean mAnswerTrue) {
    this.mAnswerTrue = mAnswerTrue;
  }

  public Question (int mTextResId, boolean answerTrue) {
    this.mTextResId = mTextResId;
    mAnswerTrue = answerTrue;
  }
}