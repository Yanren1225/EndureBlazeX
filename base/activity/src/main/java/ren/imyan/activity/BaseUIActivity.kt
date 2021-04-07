package ren.imyan.activity

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding


abstract class BaseUIActivity<viewBinding : ViewBinding, viewModel : ViewModel> : BaseActivity() {

    private var _binding: viewBinding? = null
    private var _viewModel: viewModel? = null
    private var isSetView = true
    private var isSetToolbar = true

    val binding get() = _binding!!
    val viewModel get() = _viewModel!!

    fun notSetView() {
        isSetView = false
    }

    fun notSetToolbar() {
        isSetToolbar = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = initBinding()
        _viewModel = initViewModel()
        if (isSetView) {
            setContentView(binding.root)
        }
        if (isSetToolbar) {
            setSupportActionBar(initToolbar().first)
            when (val title = initToolbar().second) {
                is String -> setToolBarTitle(title)
                is Int -> setToolBarTitle(title)
            }
        }
    }

    abstract fun initViewModel(): viewModel

    abstract fun initBinding(): viewBinding

    abstract fun initToolbar(): Pair<Toolbar, *>

    fun setToolBarTitle(@StringRes titleId: Int) {
        supportActionBar?.setTitle(titleId);
    }

    fun setToolBarTitle(title: CharSequence) {
        supportActionBar?.title = title;
    }
}