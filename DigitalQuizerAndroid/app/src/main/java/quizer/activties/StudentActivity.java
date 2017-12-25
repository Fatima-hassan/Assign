package quizer.activties;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quizer.server.*;
import quizer.enitity.*;

public class StudentActivity extends Activity {

    private ConstraintLayout c1;
    private ConstraintLayout c2;
    private ConstraintLayout c3;
    private ConstraintLayout clMcq;
    private ConstraintLayout clTF;
    private ConstraintLayout clNum;
    private RadioGroup rdgrpMcqAns;
    private RadioGroup rdgrpTFAns;
    private RadioButton radAnsA;
    private RadioButton radAnsB;
    private RadioButton radAnsC;
    private RadioButton radAnsD;
    private RadioButton radAnsTrue;
    private RadioButton radAnsFalse;
    private TextView tvQuesNum;
    private TextView tvMarks;
    private TextView tvQues;
    private EditText etAns;
    private Button btnNext;
    private TextView tvQuizDesc;
    private Button btnSelectQuiz;
    private Spinner spQuiz;
    private TextView tvScore;
    private TextView tvTotal;

    List<Numeric> quesList;
    Numeric quest = null;
    QuizerRest quesbo = new QuizerRest();
    QuizerRest quizbo =new QuizerRest();
    List<Quiz> quizList;
    int userScore = 0;
    int maxScore = 0;

    private class HttpRequestQuiz extends AsyncTask<Void, Void, List<Quiz>> {

        @Override
        protected List<Quiz> doInBackground(Void... params) {
            try {

                List<Quiz> quizes = quizbo.getQuizes();
                return quizes;

            } catch (Exception e) {
                Log.e("SelectQuizActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Quiz> quizes) {
            quizList = quizes;
            spQuiz = (Spinner) findViewById(R.id.spQuiz);
            List<String> list = new ArrayList<String>();

            for(int i=0; i<quizes.size(); i++){
                list.add(quizes.get(i).getTitle());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(StudentActivity.this, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spQuiz.setAdapter(dataAdapter);

        }

    }

    //called on submit button click
    public void startQuiz(View view){

        int quizPos = spQuiz.getSelectedItemPosition();
        int quizid = quizList.get(quizPos).getId();

        c1.setVisibility(View.INVISIBLE);
        c2.setVisibility(View.INVISIBLE);
        c3.setVisibility(View.VISIBLE);

        new HttpRequestQuestion().execute(quizid);

    }

    private class HttpRequestQuestion extends AsyncTask<Integer, Void, List<Numeric>> {

        @Override
        protected List<Numeric> doInBackground(Integer... params) {
            try {

                quesList = quesbo.getQuestions(params[0]);
                return quesList;

            } catch (Exception e) {
                Log.e("StudentActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Numeric> questions) {

            quesList = questions;
            nextQuestion(new View(StudentActivity.this));

        }
    }

    //called on next button click
    public void nextQuestion(View view){

        //getting the question number
        int quesNum = 1;

        if(quest != null)
            quesNum = quesList.indexOf(quest) + 2;

        //marking the question
        if(quesNum>1)
            markQuestion();

        //if no more questions then end the quiz
        if(quesNum == quesList.size()+1)
            endQuiz();

        else{
            if(quesNum == quesList.size())
                btnNext.setText("Finish");
            else
                btnNext.setText("Next");

            quest = quesList.get(quesNum-1);
            tvQues.setText(quest.getQuestion());
            tvMarks.setText("Max Marks ( " + quest.getMarks() + " )" );


            if(quest instanceof TrueFalse)
                showTrueFalse(quest);

            else if (quest instanceof Mcq)
                showMcq(quest);

            else
                showNumeric(quest);

        }
    }

    public void markQuestion(){

        String correctAnswer = quest.getAnswer();
        String userAns = "";

        if(quest instanceof TrueFalse){
            if(radAnsTrue.isSelected())
                userAns = radAnsTrue.getText().toString();
            else if(radAnsFalse.isSelected())
                userAns = radAnsFalse.getText().toString();
        }
        else if(quest instanceof Mcq){
            if(radAnsA.isSelected())
                userAns = radAnsA.getText().toString();
            else if(radAnsB.isSelected())
                userAns = radAnsB.getText().toString();
            else if(radAnsC.isSelected())
                userAns = radAnsC.getText().toString();
            else if(radAnsD.isSelected())
                userAns = radAnsD.getText().toString();
        }
        else{
            userAns = etAns.getText().toString();
        }

        maxScore += quest.getMarks();
        if(userAns.equals(correctAnswer))
            userScore += quest.getMarks();

    }

    private void showMcq(Numeric quest) {

        clMcq.setVisibility(View.VISIBLE);
        clTF.setVisibility(View.INVISIBLE);
        clNum.setVisibility(View.INVISIBLE);

        radAnsA.setText(((Mcq)quest).getOptionA());
        radAnsB.setText(((Mcq)quest).getOptionB());
        radAnsC.setText(((Mcq)quest).getOptionC());
        radAnsD.setText(((Mcq)quest).getOptionD());
    }

    private void showTrueFalse(Numeric quest) {

        clMcq.setVisibility(View.INVISIBLE);
        clTF.setVisibility(View.VISIBLE);
        clNum.setVisibility(View.INVISIBLE);

        radAnsTrue.setText("True");
        radAnsFalse.setText("False");

    }

    private void showNumeric(Numeric quest) {

        clMcq.setVisibility(View.INVISIBLE);
        clTF.setVisibility(View.INVISIBLE);
        clNum.setVisibility(View.VISIBLE);
    }

    public void endQuiz(){

        c1.setVisibility(View.INVISIBLE);
        c2.setVisibility(View.VISIBLE);
        c3.setVisibility(View.INVISIBLE);

        tvScore.setText("" + userScore);
        tvTotal.setText("" + maxScore);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        c1 = (ConstraintLayout) findViewById(R.id.c1);
        c2 = (ConstraintLayout) findViewById(R.id.c2);
        c3 = (ConstraintLayout) findViewById(R.id.c3);

        c1.setVisibility(View.VISIBLE);
        c2.setVisibility(View.INVISIBLE);
        c3.setVisibility(View.INVISIBLE);

        clMcq = (ConstraintLayout) findViewById(R.id.constraintLayoutMcq);
        clNum = (ConstraintLayout) findViewById(R.id.constraintLayoutNum);
        clTF = (ConstraintLayout) findViewById(R.id.constraintLayoutTF);
        rdgrpMcqAns = (RadioGroup) findViewById(R.id.rdgrpMcqAns);
        rdgrpTFAns = (RadioGroup) findViewById(R.id.rdgrpTFAns);
        radAnsA = (RadioButton) findViewById(R.id.radAnsA);
        radAnsB = (RadioButton) findViewById(R.id.radAnsB);
        radAnsC = (RadioButton) findViewById(R.id.radAnsC);
        radAnsD = (RadioButton) findViewById(R.id.radAnsD);
        radAnsTrue = (RadioButton) findViewById(R.id.radAnsTrue);
        radAnsFalse = (RadioButton) findViewById(R.id.radAnsFalse);
        tvQuesNum = (TextView) findViewById(R.id.tvQuesNum);
        tvMarks = (TextView) findViewById(R.id.tvMarks);
        tvQues = (TextView) findViewById(R.id.tvQues);
        etAns = (EditText) findViewById(R.id.etAns);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnSelectQuiz = (Button) findViewById(R.id.btnSelectQuiz);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        new HttpRequestQuiz().execute();

    }
}
