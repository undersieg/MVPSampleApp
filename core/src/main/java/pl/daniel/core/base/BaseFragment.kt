package pl.daniel.core.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import pl.daniel.data.utils.doNothing
import pl.daniel.presenter.base.BasePresenter
import pl.daniel.presenter.base.BaseView
import javax.inject.Inject

abstract class BaseFragment<P, V>(@LayoutRes layout: Int) :
    Fragment(layout), BaseView, HasAndroidInjector where V : BaseView, P : BasePresenter<V> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    protected lateinit var presenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ViewModelProvider(this, viewModelFactory).get(presenterClass())
        onPresenterPrepared()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
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

    open fun onPresenterPrepared() = doNothing

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}