package fr.mm.yourfilmsvf
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.zxing.Result
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QRCodeScannerFragment : Fragment() {

    private lateinit var barcodeView: CompoundBarcodeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)
        barcodeView = view.findViewById(R.id.barcode_scanner_view)
        return view
    }

    override fun onResume() {
        super.onResume()
        requestCameraPermission()
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST
            )
        } else {
            startCameraPreview()
        }
    }

    private fun startCameraPreview() {
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    val scannedResult = it.text
                    Log.d("scan:",scannedResult)
                    onPause()
                    val context = requireContext()
                    val intent = Intent(context, activity_film_details::class.java)
                    val gson = Gson()

                    val retrofit: Retrofit = Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/3/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    val movieService = retrofit.create(MyService::class.java)
                    val result = movieService.getIdFromExternalId(scannedResult)

                    Log.d("Etape ", "1")

                    var external_id = ""
                    var filmString = ""

                    result.enqueue(object : Callback<IdInfos> {
                        override fun onResponse(call: Call<IdInfos>, response: Response<IdInfos>) {
                            if (response.isSuccessful) {
                                Log.d("HomeActivity", "Réponse réussie")
                                val infos = response.body()
                                external_id = infos?.imdb_id?: ""
                                Log.d("external_id : ", external_id)

                                val retrofit2: Retrofit = Retrofit.Builder()
                                    .baseUrl("https://api.themoviedb.org/3/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()

                                Log.d("Etape ", "2")

                                val movieService2 = retrofit.create(MyService::class.java)
                                val result2 = movieService2.getFilmById(external_id)
                                result2.enqueue(object : Callback<MovieResponse> {
                                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                                        if (response.isSuccessful) {
                                            Log.d("HomeActivity", "Réponse réussie")
                                            val infos = response.body()
                                            val results = infos?.movie_results ?: emptyList()
                                            if (results.isNotEmpty()) {
                                                val film = results[0]
                                                val filmClass = convertToFilm(film)
                                                filmString = gson.toJson(filmClass)

                                                intent.putExtra("film", filmString)
                                                context.startActivity(intent)
                                            }
                                        }
                                    }
                                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                                        Log.e("API Error", "API call failed", t)
                                    }
                                })
                            } else {
                                Log.d("API Error", "Response code: ${response.code()}")
                            }
                        }
                        override fun onFailure(call: Call<IdInfos>, t: Throwable) {
                            Log.e("API Error", "API call failed", t)
                        }
                    })
                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
                // Fonction vide, peut être ignorée
            }
        })
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST = 123
    }
}
