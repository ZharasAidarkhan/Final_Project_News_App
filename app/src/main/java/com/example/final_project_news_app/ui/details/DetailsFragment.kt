package com.example.final_project_news_app.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.final_project_news_app.databinding.FragmentDetailsBinding
import com.example.final_project_news_app.ui.details.DetailsFragmentArgs

class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null
    private val mBinding get() = binding!!
    private val bundleArgs: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleArg = bundleArgs.article

        articleArg.let{ article->
            article.urlToImage.let {
                Glide.with(this).load(article.urlToImage).into(mBinding.headerImage)
            }
            mBinding.headerImage.clipToOutline = true
            mBinding.articleDetailsTitle.text = article.title
            mBinding.articleDetailsDescriptionText.text = article.description

            mBinding.articleDetailsButton.setOnClickListener{
                try {
                    Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .addCategory(Intent.CATEGORY_BROWSABLE)
                        .setData(Uri.parse((takeIf { URLUtil.isValidUrl(article.url)}
                            ?.let {
                                article.url
                            } ?: "https://google.com/") as String?))
                            .let {
                              ContextCompat.startActivity(requireContext(), it, null)
                            }
                }catch (e: Exception) {
                    Toast.makeText(context, "The device doesn't have any browser to view the document!", Toast.LENGTH_SHORT)
                }
            }
        }
    }
}