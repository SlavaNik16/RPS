package com.example.rps

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.rps.databinding.ActivityMainBinding
import java.util.Objects
import java.util.zip.Inflater

class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding

    val PlayerColor = Color.argb(255,0,155,0)
    val PCColor = Color.argb(255,155,0,0)
    val ColorWhite = Color.argb(255,255,255,255)
    val DrawColor = Color.argb(255,205,205,0)
    var array:Array<IntArray> = Array(5) {IntArray(5)}
    var arrayStr:Array<Array<String>> = Array(5) {Array<String>(5){""}}

    lateinit var handler: Handler
    var seconds: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(Looper.getMainLooper())

        binding.butNext.isEnabled = false;
        binding.butStart.isEnabled = true
        ChoiseButton(false)

        array[0] = intArrayOf(0,-1,1,1,-1);
        array[1] = intArrayOf(1,0,-1,-1,1);
        array[2] = intArrayOf(-1,1,0,1,-1);
        array[3] = intArrayOf(-1,1,-1,0,1);
        array[4] = intArrayOf(1,-1,1,-1,0);


        arrayStr[0] = arrayOf<String>(
            "",
            "Бумага заворачивает камень",
            "Камень разбивает ножницы",
            "Камень давит ящерицу",
            "Спок испаряет камень");
        arrayStr[1] = arrayOf<String>(
            "Бумага заворачивает камень>",
            "",
            "Ножницы режут бумагу",
            "Ящерица ест бумагу",
            "Бумага подставляет спока");
        arrayStr[2] = arrayOf<String>(
            "Камень разбивает ножницы",
            "Ножницы режут бумагу",
            "",
            "Ножницы отрезают ящерицу",
            "Спок ломает ножницы");
        arrayStr[3] = arrayOf<String>(
            "Камень давит ящерицу",
            "Ящерица ест бумагу",
            "Ножницы отрезуют ящерицу",
            "",
            "Ящерица травит спока");
        arrayStr[4] = arrayOf<String>(
            "Спок испаряет камень",
            "Бумага подставляет спока",
            "Спок ломает ножницы",
            "Ящерица травит спока",
            "");
    }

    private val updateText = object: Runnable {
        override fun run(){
            Time()
            handler.postDelayed(this,1000)
        }
    }

    fun Time(){

        seconds++
        binding.txtSec.text = (seconds%60).toString()
        binding.txtMin.text = (seconds/60%60).toString()
        binding.txtHour.text = (seconds/60/60%64).toString()
    }
    fun ChoiseButton(Turn:Boolean){
        binding.butSpock.isEnabled = Turn
        binding.butLizard.isEnabled = Turn
        binding.butPaper.isEnabled = Turn
        binding.butRock.isEnabled = Turn
        binding.butScissors.isEnabled = Turn
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun ChoiseClear(){
        ButtonClear(binding.butSpock)
        ButtonClear(binding.butLizard)
        ButtonClear(binding.butPaper)
        ButtonClear(binding.butRock)
        ButtonClear(binding.butScissors)
        binding.txtChoise.text = "Сделайте ваш выбор!"
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun ButtonClear(but: Button){
        but.setBackgroundColor(getColor(R.color.purple_500));
        but.setTextColor(ColorWhite)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun butStart(view: View){
        super.onResume()
        handler.post(updateText)

        if(binding.butStart.text == "Играть") {
            ChoiseButton(true)
            ChoiseClear()
            binding.butStart.text = "Закончить"
        }else if(binding.butStart.text == "Закончить") {
            super.onPause()
            handler.removeCallbacks(updateText)
            binding.txtChoise.text = "Игра закончена!"
            ChoiseClear()
            ChoiseButton(false)
            binding.butNext.isEnabled = false
            binding.butStart.text = "Выйти"
            binding.txtChoise.text = "Спасибо за игру!"
        }else{
            System.exit(0)
        }

    }
    fun PCChoise(): Int = arrayOf("Rock","Paper","Scissors","Lizard","Spock").indices.random()
    fun ColorPC(indexPlayer:Int,indexPC: Int){
        when(indexPC){

            0 -> {
                if (indexPlayer == 0)
                    ButtonColorDraw(binding.butRock)
                else
                    ButtonColorNoDraw(binding.butRock)
            }
            1 -> {
                if(indexPlayer == 1)
                    ButtonColorDraw(binding.butPaper)
                else
                    ButtonColorNoDraw(binding.butPaper)
            }
            2 -> {
                if(indexPlayer == 2)
                    ButtonColorDraw(binding.butScissors)
                else
                    ButtonColorNoDraw(binding.butScissors)
            }
            3 -> {
                if(indexPlayer == 3)
                    ButtonColorDraw(binding.butLizard)
                else
                    ButtonColorNoDraw(binding.butLizard)
            }
            4 -> {
                if(indexPlayer == 4)
                    ButtonColorDraw(binding.butSpock)
                else
                    ButtonColorNoDraw(binding.butSpock)
            }

        }
    }
    fun ButtonColorNoDraw(but: Button){
        but.setBackgroundColor(PCColor)
    }
    fun ButtonColorDraw(but: Button){
        but.setBackgroundColor(DrawColor)
    }


    fun butRock(view:View){
        binding.butRock.setBackgroundColor(PlayerColor)
        val PC = PCChoise()
        ColorPC(0,PC);
        var result = array[0][PC]
        var text = arrayStr[0][PC]

        Result(result,text)
    }
    fun butPaper(view:View){
        binding.butPaper.setBackgroundColor(PlayerColor)
        val PC = PCChoise()
        ColorPC(1,PC);
        var result = array[1][PC]
        var text = arrayStr[1][PC]

        Result(result,text)
    }
    fun butScissors(view:View){

        binding.butScissors.setBackgroundColor(PlayerColor)
        val PC = PCChoise()
        ColorPC(2,PC);
        var result = array[2][PC]
        var text = arrayStr[2][PC]

        Result(result,text)
    }
    fun butLizard(view:View){
        binding.butLizard.setBackgroundColor(PlayerColor)
        val PC = PCChoise()
        ColorPC(3,PC);
        var result = array[3][PC]
        var text = arrayStr[3][PC]

        Result(result,text)
    }

    fun butSpock(view:View){
        binding.butSpock.setBackgroundColor(PlayerColor)
        val PC = PCChoise()
        ColorPC(4,PC);
        var result = array[4][PC]
        var text = arrayStr[4][PC]

        Result(result,text)
    }

    fun Result(result:Int,txt:String){
        var textWin:String
        if(result == 1) {
            textWin = "Победа! "
            binding.txtPlayerCount.text = (binding.txtPlayerCount.text.toString().toInt() + 1).toString()
        } else if(result == 0){
            textWin = "Ничья! "
            binding.txtDrawCount.text = (binding.txtDrawCount.text.toString().toInt() + 1).toString()
        }else{
            textWin = "Поражение! "
            binding.txtPCCount.text = (binding.txtPCCount.text.toString().toInt() + 1).toString()
        }
        textWin = textWin + txt
        binding.txtChoise.text = textWin

        ChoiseButton(false)
        binding.butNext.isEnabled = true

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun butNext(view: View){
        ChoiseButton(true)
        ChoiseClear()
        binding.butNext.isEnabled = false
    }
}