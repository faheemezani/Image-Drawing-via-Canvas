package io.github.faheemezani.drawdimensions

import android.content.ContentResolver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import jp.co.cyberagent.android.gpuimage.GLTextureView
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLaplacianFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePosterizeFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVignetteFilter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var gpuImage: GPUImage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupImage()
    }

    private fun setupUI() {

        btTwoByTwo.setOnClickListener {
            gpuimageview.visibility = View.GONE
            dimensionGlSurface.visibility = View.VISIBLE
            dimensionGlSurface.draw(Dimension.TWO_BY_TWO)
        }

        btThreeByThree.setOnClickListener {
            gpuimageview.visibility = View.GONE
            dimensionGlSurface.visibility = View.VISIBLE
            dimensionGlSurface.draw(Dimension.THREE_BY_THREE)
        }

        btPosterizeFilter.setOnClickListener {
            gpuimageview.visibility = View.VISIBLE
            gpuimageview.invalidate()
            dimensionGlSurface.visibility = View.GONE
            applyFilter(Filters.POSTERIZE)
        }

        btVignetteFilter.setOnClickListener {
            gpuimageview.visibility = View.VISIBLE
            gpuimageview.invalidate()
            dimensionGlSurface.visibility = View.GONE
            applyFilter(Filters.VIGNETTE)
        }

        btLaplacianFilter.setOnClickListener {
            gpuimageview.visibility = View.VISIBLE
            gpuimageview.invalidate()
            dimensionGlSurface.visibility = View.GONE
            applyFilter(Filters.LAPLACIAN)
        }

    }

    private fun setupImage() {
        if (gpuImage == null) {
            // Image resource
            val imageUri = Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.drawable.vangogh))
                .appendPath(resources.getResourceTypeName(R.drawable.vangogh))
                .appendPath(resources.getResourceEntryName(R.drawable.vangogh))
                .build()

            gpuImage = GPUImage(this)
            val glTextureView: GLTextureView = findViewById<GLTextureView>(R.id.gpuimageview)
            gpuImage?.setGLTextureView(glTextureView)
            gpuImage?.setImage(imageUri)
        }
    }

    private fun applyFilter(filter: Filters) {
        when (filter) {
            Filters.POSTERIZE -> { gpuImage?.setFilter(GPUImagePosterizeFilter()) }
            Filters.VIGNETTE -> { gpuImage?.setFilter(GPUImageVignetteFilter()) }
            Filters.LAPLACIAN -> { gpuImage?.setFilter(GPUImageLaplacianFilter()) }
        }
    }

}

enum class Filters {
    POSTERIZE, VIGNETTE, LAPLACIAN
}
