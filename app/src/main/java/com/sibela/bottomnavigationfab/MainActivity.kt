package com.sibela.bottomnavigationfab

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_bottomsheet.*

class MainActivity : AppCompatActivity(), StringAdapter.Callbacks {

    private val namesAdapter: StringAdapter by lazy { StringAdapter(this) }

    private var fabStatus = FabStatus.INVISIBLE
    private var snackStatus = SnackStatus.INVISIBLE
    private lateinit var snack: Snackbar

    private val bottomSheetBehavior: BottomSheetBehavior<MaterialCardView> by lazy {
        BottomSheetBehavior.from(bottomSheet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFab.hideInstantaneously()
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        snack = getSnackbar()
        setupRecycler()
        setupBottomSheetFab()
        displayBottomSheet.setOnClickListener { openBottomSheet() }
        warningFab.setOnClickListener { changeSnackState() }
    }

    private fun setupRecycler() {
        namesRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(baseContext, DividerItemDecoration.VERTICAL))
            adapter = namesAdapter
        }

        namesAdapter.names = getSomeNames()
    }

    private fun setupBottomSheetFab() {
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (fabStatus == FabStatus.INVISIBLE) return

                mainFab.animate().scaleX(1 + slideOffset).scaleY(1 + slideOffset).setDuration(0).start()
                if (isFullyCollapsed(slideOffset)) {
                    mainFab.hideInstantaneously()
                    fabStatus = FabStatus.INVISIBLE
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            private fun isFullyCollapsed(slideOffset: Float) = slideOffset == -1F

            private fun isFullyExpanded(slideOffset: Float) = slideOffset.isNaN()
        })
    }

    private fun getSomeNames() = arrayListOf<String>().apply {
        add("Albert Einstein")
        add("Isaac Newton")
        add("Marie Curie")
        add("Galileu Galilei")
        add("Nicolau Copérnico")
        add("Michael Faraday")
    }

    override fun onNameSelected(name: String) {
        mainFab.showWithAnimation()
        fabStatus = FabStatus.VISIBLE
    }

    private fun openBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun changeSnackState() {
        snackStatus = if (snackStatus == SnackStatus.INVISIBLE) {
            snack.show()
            SnackStatus.VISIBLE
        } else {
            snack.dismiss()
            SnackStatus.INVISIBLE
        }
    }

    private fun getSnackbar(): Snackbar {
        val snack = Snackbar.make(container, "Warning message", Snackbar.LENGTH_INDEFINITE)
        snack.setActionTextColor(getColor(R.color.colorAccent))
        snack.view.setBackgroundColor(getColor(R.color.colorPrimaryDark))
        return snack
    }

    enum class FabStatus {
        VISIBLE, INVISIBLE
    }

    enum class SnackStatus {
        VISIBLE, INVISIBLE
    }
}
