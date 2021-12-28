package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var questionText: TextView
    lateinit var progressBar: ProgressBar
    lateinit var progressText: TextView
    lateinit var image: ImageView
    lateinit var option1: TextView
    lateinit var option2: TextView
    lateinit var option3: TextView
    lateinit var option4: TextView
    lateinit var btnSubmit: Button

    private var mCurrentPosition:Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        questionText = findViewById<TextView>(R.id.tv_question)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressText = findViewById<TextView>(R.id.tv_progress)
        image = findViewById<ImageView>(R.id.iv_image)
        option1 = findViewById<TextView>(R.id.tv_option_one)
        option2 = findViewById<TextView>(R.id.tv_option_two)
        option3 = findViewById<TextView>(R.id.tv_option_three)
        option4 = findViewById<TextView>(R.id.tv_option_four)
        btnSubmit = findViewById<Button>(R.id.btn_submit)

        mUsername = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestions()

        setQuestion()

        option1.setOnClickListener(this)
        option2.setOnClickListener(this)
        option3.setOnClickListener(this)
        option4.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    private fun setQuestion(){

        val question = mQuestionsList!![mCurrentPosition-1]

        defaultOptionsView()

        if(mCurrentPosition == mQuestionsList!!.size){
            btnSubmit.text = "FINISH"
        }else{
            btnSubmit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        progressText.text = "$mCurrentPosition/${progressBar.max}"

        questionText.text = question!!.question
        image.setImageResource(question.image)
        option1.text = question!!.optionOne
        option2.text = question!!.optionTwo
        option3.text = question!!.optionThree
        option4.text = question!!.optionFour
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0, option1)
        options.add(1, option2)
        options.add(2, option3)
        options.add(3, option4)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option_one ->{
                selectedOptionView(option1, 1)
            }
            R.id.tv_option_two ->{
                selectedOptionView(option2, 2)
            }
            R.id.tv_option_three ->{
                selectedOptionView(option3, 3)
            }
            R.id.tv_option_four ->{
                selectedOptionView(option4, 4)
            }
            R.id.btn_submit ->{
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size ->{
                            setQuestion()
                        }else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUsername)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition -1)
                    if(question!!.correctOption != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctOption, R.drawable.correct_option_border_bg)

                    if(mCurrentPosition == mQuestionsList!!.size){
                        btnSubmit.text = "FINISH"
                    }else{
                        btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }

        }

    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 -> {
                option1.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                option2.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                option3.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                option4.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}