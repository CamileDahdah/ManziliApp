package com.example.camilledahdah.manzili;

import android.text.Html;
import android.util.Log;
import android.util.Pair;

/**
 * Created by camilledahdah on 3/11/18.
 */

public class ArabicText {


    static float totalScore;


    public static TextResponse detectArabicWords (String speechText, String theCorrectWord){


        if (speechText != "") {

            String[] speechWords = speechText.split (" ");

            Log.d("theCorrectWord", "theCorrectWord: " + theCorrectWord + "  speechText: " + speechText);

            int wordLength = 0;
            float letterCounter = 0;
            float theLetterCounter = 0;

            String finalText = "";

            for (String speechWord : speechWords) {

                wordLength = speechWord.length();

                int firstIndex = 0, lastIndex = 0;
                int colorFirstIndex = 0, colorLastIndex = 0;
                int colorLength = 0;

                //for (Pair<String, Float> correctWord : correctWords) {

                letterCounter = 0;

                boolean isFirstIndex;

                int I = theCorrectWord.length() - 1;
                int J = speechWord.length() - 1;

                int totalSteps = I + J + 1;

                int i1, i2, j1, j2;

                for (int i = 1; i <= totalSteps; i++) {

                    i1 = Math.max ( (i - J) - 1, 0 );

                    i2 = Math.min (i - 1, I);

                    j1 = Math.min (J, (I + J) - (i - 1));

                    j2 = Math.max (J - (i - 1), 0);

                    isFirstIndex = true;

                    for( ; i1 <= i2; i1++){


                        if ( theCorrectWord.charAt(i1) == speechWord.charAt(j2) ) {

                            letterCounter += Math.min(100f / speechWord.length(), 100f / theCorrectWord.length());


                            if (isFirstIndex) {
                                firstIndex = j2;
                                isFirstIndex = false;

                            } else {
                                lastIndex = j2;
                            }
                        }


                        j2++;
                    }

                    if (lastIndex - firstIndex > colorLength) { //check best sequence
                        colorLength = lastIndex - firstIndex;
                        colorFirstIndex = firstIndex;
                        colorLastIndex = lastIndex;
                    }

                    if (letterCounter > theLetterCounter) {
                        theLetterCounter = letterCounter;
                    }

                    //}

                    //word by word
					/*if (speechword == correctWord.Key) {

						if (wordCounter < correctWord.Value) {
							wordCounter = correctWord.Value;
						}

					}*/
                }

                finalText = speechWord;

                //change Color
                if (colorLastIndex > colorFirstIndex) {

                    finalText =  new StringBuilder(finalText).insert (colorLastIndex + 1, "</font>").toString();
                    finalText = new StringBuilder(finalText).insert (colorFirstIndex, "<font color=#02dfa5>").toString();
                }

                speechText = speechText.replace (speechWord, finalText);

            }
            Log.d ("speechText", speechText);

            setTotalScore (theLetterCounter);


            Log.d ("accuracy", "Current Accuracy: " + getAccuracy() );
            Log.d ("score", "Score (LetterCounter): " + theLetterCounter);

            if ( theLetterCounter >= getAccuracy() ) {
                return new TextResponse(finalText, getAccuracy(), true);

            } else {
                return new TextResponse(finalText, getAccuracy(), false);
            }

        } else {
            return  null;
        }
        
    }



    private static float getAccuracy() {
        return 100;
    }

    public static float getTotalScore() {
        return totalScore;
    }

    private static void setTotalScore(float score) {
        totalScore = score;
    }


    public static class TextResponse{

        private String finalTextResult;
        private float accuracy;
        private boolean correct;

        public TextResponse(String finalTextResult, float accuracy, boolean correct){
            this.accuracy = accuracy;
            this.finalTextResult = finalTextResult;
            this.correct = correct;

        }

        public String getFinalTextResult() {
            return finalTextResult;
        }

        public void setFinalTextResult(String finalTextResult) {
            this.finalTextResult = finalTextResult;
        }

        public float getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(float accuracy) {
            this.accuracy = accuracy;
        }

        public boolean isCorrect() {
            return correct;
        }

        public void setCorrect(boolean correct) {
            this.correct = correct;
        }
    }


}
