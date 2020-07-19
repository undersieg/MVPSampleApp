package pl.daniel.presenter.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import pl.daniel.data.utils.doNothing
import java.util.*
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V> : ViewModel(), CoroutineScope where V : BaseView {

    private var job: Job = SupervisorJob()
    private var latestViewChanges: Queue<V.() -> Unit> = LinkedList()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var view: V? = null
    private var isFirstBind = true

    fun present(viewChange: (V.() -> Unit)?) = view?.let {
        launch(Dispatchers.Main) {
            viewChange?.invoke(it)
        }
    } ?: run {
        latestViewChanges.add(viewChange)
    }

    fun bind(view: V) {
        if (this.view != null) throw RuntimeException(
            "Concurrent view bind! Unexpected, " +
                    "second instance of view occurred before first one unbind."
        )

        this.view = view
        notifyBindState()
        checkLastChanges()
    }

    private fun notifyBindState() = if (isFirstBind) {
        isFirstBind = false
        onFirstBind()
    } else {
        onViewRestoreState()
    }

    private fun checkLastChanges() {
        while (!latestViewChanges.isEmpty()) {
            present(latestViewChanges.poll())
        }
    }

    open fun unbind() {
        this.view = null
    }

    open fun onFirstBind() = doNothing

    open fun onViewRestoreState() = doNothing

    override fun onCleared() {
        finish()
        super.onCleared()
    }

    open fun finish() {
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
}