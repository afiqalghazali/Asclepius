package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.HistoryEntity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.viewmodel.ResultViewModel
import com.dicoding.asclepius.viewmodel.ViewModelFactory
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var resultViewModel: ResultViewModel
    private var historyResult: HistoryEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory(application)
        resultViewModel = ViewModelProvider(this, viewModelFactory)[ResultViewModel::class.java]

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))

//        Display the Image
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
            analyzeImage(it)
        }

        binding.homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    //        Classify Function
    private fun analyzeImage(uri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object: ImageClassifierHelper.ClassifierListener {

                override fun onError(error: String) {
                    showToast(error)
                }

                override fun onResult(results: List<Classifications>?) {
//                    Format the Result
                    results?.let {
                        if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                            val result = it[0].categories[0]
                            val resultType = result.label
                            val resultScore = result.score
                            binding.resultType.text = resultType
                            if (resultType.equals("Cancer", ignoreCase = true)) {
                                binding.resultType.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorError))
                            } else {
                                binding.resultType.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorPrimary))
                            }
                            binding.resultScore.text = getString(R.string.result_score, NumberFormat.getPercentInstance().format(result.score))

                            historyResult = HistoryEntity(
                                imageUri = uri.toString(),
                                type = resultType,
                                score = resultScore
                            )

                            resultViewModel.insertHistory(historyResult!!)
                            showToast("History saved")
                        } else {
                            showToast(getString(R.string.image_classifier_failed))
                        }
                    }
                }
            }
        )
//    Launch TFLite Image Classify
        imageClassifierHelper.classifyStaticImage(uri)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }

}