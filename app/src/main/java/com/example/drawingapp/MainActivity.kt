package com.example.drawingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.drawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        onClick()
    }

    private fun onClick() {
        binding.pen.setOnClickListener {

            binding.drawingView.setDrawer(DrawingView.Drawer.PEN)
        }



        binding.arrow.setOnClickListener {
            binding.drawingView.setDrawer(DrawingView.Drawer.LINE)
        }



        binding.square.setOnClickListener {
            binding.drawingView.setDrawer(DrawingView.Drawer.RECTANGLE)
        }



        binding.circle.setOnClickListener {
            binding.drawingView.setDrawer(DrawingView.Drawer.CIRCLE)
        }



        binding.color.setOnClickListener {
            binding.linearColor.visibility = View.VISIBLE
        }

        binding.colorBlack.setOnClickListener{
            binding.drawingView.setColor(binding.colorBlack.tag.toString())
            binding.linearColor.visibility = View.GONE

        }
        binding.colorRed.setOnClickListener{
            binding.drawingView.setColor(binding.colorRed.tag.toString())
            binding.linearColor.visibility = View.GONE


        }
        binding.colorBlue.setOnClickListener{
            binding.drawingView.setColor(binding.colorBlue.tag.toString())
            binding.linearColor.visibility = View.GONE


        }
        binding.colorGreen.setOnClickListener{
            binding.drawingView.setColor(binding.colorGreen.tag.toString())
            binding.linearColor.visibility = View.GONE


        }
        binding.undo.setOnClickListener{
            binding.drawingView.onClickUndo()
        }

    }


}