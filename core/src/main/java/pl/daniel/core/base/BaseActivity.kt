package pl.daniel.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import pl.daniel.presenter.base.BasePresenter
import pl.daniel.presenter.base.BaseView
import javax.inject.Inject

abstract class BaseActivity<P, V>(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId),
    HasAndroidInjector where V : BaseView, P : BasePresenter<V> {

    lateinit var presenter: P

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        presenter = ViewModelProvider(this, viewModelFactory).get(presenterClass())
    }

    @Suppress("UNCHECKED_CAST")
    override fun onStart() {
        super.onStart()

        if (::presenter.isInitialized) {
            presenter.bind(this as V)
        }
    }

    override fun onStop() {
        if (::presenter.isInitialized) {
            presenter.unbind()
        }

        super.onStop()
    }

    abstract fun presenterClass(): Class<P>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}