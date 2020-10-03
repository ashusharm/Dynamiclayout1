package com.example.dynamiclayout
enum class QuestionType
{
    Text,Radio,CheckBox


}
data class Question(val id:Int,val type:QuestionType,val qtext:String,val options:List<String>?,val answers:List<String>)