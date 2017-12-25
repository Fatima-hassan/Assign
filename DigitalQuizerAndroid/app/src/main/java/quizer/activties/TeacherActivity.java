package quizer.activties;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import org.springframework.web.client.RestTemplate;

import quizer.server.QuizerRest;
import quizer.enitity.Quiz;

public class TeacherActivity extends Activity {

    private ConstraintLayout c1;
    private ConstraintLayout c2;
    private EditText title;
    private EditText description;
    private Button savequiz;
    private TabHost tabs;
    TabHost.TabSpec mcqtab, tftab, numtab;
    private EditText quesmarks;
    private EditText mcqques;
    private EditText tfques;
    private EditText numques;
    private EditText numans;
    private EditText mcqa;
    private EditText mcqb;
    private EditText mcqc;
    private EditText mcqd;
    private RadioGroup mcqgroup;
    private RadioGroup tfgroup;
    private RadioButton radioa;
    private RadioButton radiob;
    private RadioButton radioc;
    private RadioButton radiod;
    private RadioButton radiot;
    private RadioButton radiof;
    private Button finalquiz;
    private Button submcq;
    private Button subtf;
    private Button subnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        c1 = (ConstraintLayout) findViewById(R.id.c1);
        c2 = (ConstraintLayout) findViewById(R.id.c2);
        c1.setVisibility(View.VISIBLE);
        c2.setVisibility(View.INVISIBLE);
        title = (EditText)findViewById(R.id.etTitle);
        description = (EditText)findViewById(R.id.etDescription);
        savequiz= (Button)findViewById(R.id.btnSaveQuiz);
        tabs = (TabHost) findViewById(R.id.tabHost);
        tabs.setup();
        mcqtab = tabs.newTabSpec("MCQ");
        mcqtab.setContent(R.id.MCQ);
        mcqtab.setIndicator("MCQ");
        tabs.addTab(mcqtab);
        tftab = tabs.newTabSpec("TrueFalse");
        tftab.setContent(R.id.TrueFalse);
        tftab.setIndicator("TrueFalse");
        tabs.addTab(tftab);
        numtab = tabs.newTabSpec("Numeric");
        numtab.setContent(R.id.Numeric);
        numtab.setIndicator("Numeric");
        tabs.addTab(numtab);
        quesmarks = (EditText) findViewById(R.id.etMarks);
        mcqques = (EditText) findViewById(R.id.etQuesMCQ);
        tfques = (EditText) findViewById(R.id.etQuesTF);
        numques = (EditText) findViewById(R.id.etQuesNum);
        numans = (EditText) findViewById(R.id.etAnsNum);
        mcqa = (EditText) findViewById(R.id.etMcqA);
        mcqb = (EditText) findViewById(R.id.etMcqB);
        mcqc = (EditText) findViewById(R.id.etMcqC);
        mcqd = (EditText) findViewById(R.id.etMcqD);
        mcqgroup = (RadioGroup) findViewById(R.id.rdgrpMcq);
        radioa = (RadioButton) findViewById(R.id.radMcqA);
        radiob = (RadioButton) findViewById(R.id.radMcqB);
        radioc = (RadioButton) findViewById(R.id.radMcqC);
        radiod = (RadioButton) findViewById(R.id.radMcqD);
        tfgroup = (RadioGroup) findViewById(R.id.rdgrpTF);
        radiot = (RadioButton) findViewById(R.id.radTrue);
        radiof = (RadioButton) findViewById(R.id.radFalse);
        finalquiz = (Button) findViewById(R.id.btnFinalQuiz);
        submcq = (Button) findViewById(R.id.btnSubMcq);
        subtf = (Button) findViewById(R.id.btnSubTF);
        subnum = (Button) findViewById(R.id.btnSubNum);

    }


    QuizerRest quesrest =new QuizerRest();
    QuizerRest quizrest =new QuizerRest();
    Quiz quiz = new Quiz();
    RestTemplate restTemplate = new RestTemplate();

    private class quizmakemainthread extends AsyncTask<Void, Void, Quiz> {
        @Override
        protected Quiz doInBackground(Void... params) {
            quiz = quizrest.addQuiz(title.getText().toString(), description.getText().toString());
            return quiz;
        }

        @Override
        protected void onPostExecute(Quiz quiz) {
            c1.setVisibility(View.INVISIBLE);
            c2.setVisibility(View.VISIBLE);
        }

    }

    private class quizmakesubthread extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            quesrest.saveQuestions();
            return null;
        }

        @Override
        protected void onPostExecute(Void nothing) {
            c2.setVisibility(View.INVISIBLE);
            c1.setVisibility(View.VISIBLE);
        }

    }

    public void submitmcq(View view){

        String quest = mcqques.getText().toString();
        int selectedRadID = mcqgroup.getCheckedRadioButtonId();
        RadioButton rad = (RadioButton) findViewById(selectedRadID);
        String optA = mcqa.getText().toString();
        String optB = mcqb.getText().toString();
        String optC = mcqc.getText().toString();
        String optD = mcqd.getText().toString();
        int marks = Integer.parseInt(quesmarks.getText().toString());
        String ans;
        if(selectedRadID == radioa.getId()){
            ans = optA;
        }
        else if(selectedRadID == radiob.getId()){
            ans = optB;
        }
        else if(selectedRadID == radioc.getId()){
            ans = optC;
        }
        else {
            ans = optD;
        }

        quesrest.addMCQ(quest, ans, marks, quiz, optA, optB, optC, optD);

    }

    public void submittf(View view){

        String quest = tfques.getText().toString();
        int selectedRadID =tfgroup.getCheckedRadioButtonId();
        RadioButton rad = (RadioButton) findViewById(selectedRadID);
        String optT = "True";
        String optF = "False";
        int marks = Integer.parseInt(quesmarks.getText().toString());
        String ans;
        if(selectedRadID == radiot.getId()){
            ans = optT;
        }
        else {
            ans = optF;
        }

        quesrest.addTrueFalse(quest, ans, marks, quiz, optT, optF);
    }

    public void submitnum(View view){

        String quest = numques.getText().toString();
        int marks = Integer.parseInt(quesmarks.getText().toString());
        String ans =  numans.getText().toString();

        quesrest.addNumeric(quest, ans, marks, quiz);
    }

    public void clickmakemain(View view){
        new quizmakemainthread().execute();
    }

    public void clickmakesub(View view){
        new quizmakesubthread().execute();
    }

}
