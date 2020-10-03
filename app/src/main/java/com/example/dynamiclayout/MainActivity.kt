package com.example.dynamiclayout

import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val margin=16;
val Int.pixel:Int

    get()=(this* Resources.getSystem().displayMetrics.density).toInt();
class MainActivity : AppCompatActivity() {
    private var questions: MutableList<Question> = mutableListOf();
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setquestion()
        setupQuiz()
        setbutton()
    }

    private fun setquestion() {
        questions.add(
            Question(
                id = 1,
                QuestionType.Text,
                qtext = "kangna ranaut ko shiv sena k neta ne kya kha ?",
                null,
                listOf("haramkhor")
            )
        )
        questions.add(
            Question(
                id = 2,
                QuestionType.Radio,
                qtext = "Kya rhea jaal jaayegi ?",
                listOf("YES", "NO"),
                listOf("YES")
            )
        )
        questions.add(
            Question(
                id = 3,
                QuestionType.CheckBox,
                qtext = "Sushant ki mot ka jimedaar kon h ?",
                listOf("nepotisium", "Drugs", "Rhea chakarwari", "karan johar", "jyaad paisa"),
                listOf("nepotisium", "Drugs", "Rhea chakarwari", "karan johar")
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun setupQuiz() {
        var index1: Int = 0;
        for (i in questions) {
        index1++;

        when (i.type) {
                QuestionType.Text -> setTextQuestion(index1, i);
                QuestionType.Radio -> setRadioQuestion(index1, i);
                QuestionType.CheckBox -> setCheckQuestion(index1, i);

            }
        }
    }
    private fun setTextQuestion(count:Int,q:Question)
    {
       val  textview=getQuestionTestVeiwer(count,q.qtext);
        val edittext=EditText(this);
        edittext.id=q.id;
        edittext.setSingleLine(true);
        edittext.layoutParams=LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
      out.addView(textview);
        out.addView(edittext);
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private  fun setRadioQuestion(count:Int, q:Question)
    {
        val textView=getQuestionTestVeiwer(count,q.qtext);
        val radioG=RadioGroup(this)
        radioG.id=q.id;
        radioG.orientation=RadioGroup.VERTICAL
        radioG.layoutParams=LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        q.options?.forEachIndexed{
            index,element->
            val radiobutton=RadioButton(this);
            radiobutton.id=(q.id.toString()+index.toString()).toInt();
            radiobutton.text=element;
            radioG.addView(radiobutton);
        }
        out.addView(textView);
        out.addView(radioG);
    }
    private  fun setCheckQuestion(count:Int,q: Question)
    {
        val textView=getQuestionTestVeiwer(count,q.qtext);
        out.addView(textView);
        val param=LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        q.options?.forEachIndexed { index, element ->
            val checkbox=CheckBox(this)
            checkbox.id=(q.id.toString()+index.toString()).toInt();
            checkbox.text=element
            checkbox.layoutParams=param;
            out.addView(checkbox);
        }

    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun setbutton()
    {
      val prama=LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.WRAP_CONTENT,
          LinearLayout.LayoutParams.WRAP_CONTENT
      )
        prama.gravity=Gravity.CENTER_HORIZONTAL;
        prama.topMargin= margin.pixel;
        val button1=Button(this)
        button1.layoutParams=prama;
        button1.text="SUBMIT";

        button1.setOnClickListener{


            equlateQuiz()

        }
        out.addView(button1);

    }

    private  fun equlateQuiz()
    {
        var score=0;
        questions.forEachIndexed{
            index,element->

            if(QuestionType.Text==element.type)
            {

                val text=out.findViewById<EditText>(element.id);

               text?.let{
                   if(it.text.toString()==element.answers[0])
                   {
//                       Toast.makeText(this,"Ashu",Toast.LENGTH_SHORT).show()
                       score++;
                   }
               }

            }
            else if(QuestionType.Radio==element.type)
            {
                val radioG=out.findViewById<RadioGroup>(element.id);
                radioG?.let {
                   val c= it.checkedRadioButtonId
                    if(c>0)
                    {
                      val  text1=out.findViewById<RadioButton>(c);
                        if(text1.text==element.answers[0])
                            score++;
                    }
                }
                }
            else
            {
               var correct=true;
                element.options?.forEachIndexed{ index,str->
                    val  checkid=  (element.id.toString()+index.toString()).toInt();
                    val text=out.findViewById<CheckBox>(checkid);
                    if(element.answers.contains(text.text)) {
                        if(!text.isChecked)
                            correct=false;
                        }
                    else if(text.isChecked)
                        correct=false;
                    }
                if(correct)
                    score++;
                }

            }
        Toast.makeText(this,"Your score is $score out of 3",Toast.LENGTH_SHORT).show()
            }



    private fun getQuestionTestVeiwer(count: Int, ques:String):TextView
    {
       val textview=TextView(this)
        textview.text="Q$count  $ques";
        textview.layoutParams=LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply{topMargin=margin.pixel}
        return textview;
    }

}


