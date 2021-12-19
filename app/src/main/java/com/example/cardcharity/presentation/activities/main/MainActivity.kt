package com.example.cardcharity.presentation.activities.main

import android.content.res.Configuration
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivityMainBinding
import com.example.cardcharity.databinding.ActivityMainContentBinding
import com.example.cardcharity.domen.auth.Authentication
import com.example.cardcharity.domen.glide.GlideApp
import com.example.cardcharity.presentation.activities.main.list.ShopDividerDecoration
import com.example.cardcharity.presentation.activities.settings.SettingsActivity
import com.example.cardcharity.presentation.activities.splash.SplashActivity
import com.example.cardcharity.presentation.anim.HideableManager
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.presentation.component.recyclerview.sticky.StickyHeaderDecoration
import com.example.cardcharity.repository.model.User
import com.example.cardcharity.utils.*
import com.example.cardcharity.utils.extensions.*
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.onEach

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {
    private val viewModel: MainViewModel by viewModels()

    //Dynamic ui elements
    private lateinit var messageView: HideableManager
    private lateinit var progressBar: HideableManager

    //Cheeseburger in paradise
    private lateinit var cheeseburger: ActionBarDrawerToggle

    //Root layout with recycler view, toolbar etc.
    private val content: ActivityMainContentBinding
        get() = binding.content

    private val container: View
        get() = content.container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setTranslucentStatus()


        binding.navigationView.setNavigationItemSelectedListener(this@MainActivity)

        with(content) {
            //Toolbar
            setupToolbar(toolbar)
            setupDrawerToggle()
        }

        //Recycler View
        initializeRecyclerView()

        Authentication.user.onEach {
            if (it == null) {
                openActivity(this, SplashActivity::class)
                finish()
            } else {
                viewModel.initialize(it.uid)
                setupUi(it)
                setupDrawerHeader(it)
                setAdapter()
            }
        }.launchWhenStarted(lifecycleScope)

        initializeAnimators()


        content.buttonRefresh.setOnClickListener {
            viewModel.requestModels()
        }

        viewModel.shops.onEach {
            viewModel.adapter.setModels(it)
        }.launchWhenStarted(lifecycleScope)

        viewModel.uiState.onEach {
            if (it != null) {
                setUiState(it)
            }
        }.launchWhenStarted(lifecycleScope)

        viewModel.requestModels()
    }

    private fun setupUi(user: User) {
        with(content) {
            val dayTimeState = getCurrentDayTimeState()
            textViewHello.text = getGreeting(
                context = this@MainActivity,
                state = dayTimeState,
                name = user.name
            )
        }
    }

    private fun setupDrawerHeader(user: User) {
        binding.navigationView.getHeaderView(0)?.apply {
            val container = find<View>(R.id.header)
            val imageViewAvatar = find<ImageView>(R.id.imageViewAvatar)
            val textVieUsername = find<TextView>(R.id.textViewUsername)
            val textViewEmail = find<TextView>(R.id.textViewEmail)

            val (email, _, name, avatar) = user
            loadAvatar(imageViewAvatar, avatar)
            textVieUsername.text = name
            textViewEmail.text = email
            container.setOnClickListener(this@MainActivity)
        }
    }

    private fun loadAvatar(into: ImageView, uri: Uri) {
        GlideApp.with(this)
            .load(uri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .priority(Priority.HIGH)
            .into(into)
    }


    private fun initializeRecyclerView() {
        with(content.recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(false)
            //TODO
            addItemDecoration(ShopDividerDecoration(this@MainActivity))
        }
    }

    private fun setAdapter() {
        with(content.recyclerView) {
            val adapter = viewModel.adapter
            this.adapter = adapter
            addItemDecoration(StickyHeaderDecoration(this, adapter))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> SettingsActivity.start(this)
            R.id.about -> {

            }

            else -> return false
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.header -> SettingsActivity.start(this)
        }
    }


    private fun setUiState(state: MainViewModel.UiState) {
        progressBar.show = (state == MainViewModel.UiState.LOADING)
        setMessageState(state)
    }

    private fun setMessageState(state: MainViewModel.UiState) {
        messageView.show = when (state) {
            MainViewModel.UiState.NO_CONNECTION -> setMessageNoConnection()
            MainViewModel.UiState.ERROR -> setMessageError()
            else -> false
        }
    }

    private fun setMessageError(): Boolean {
        return setMessageCustom(
            iconResId = R.drawable.ic_warning_24,
            iconColorAttr = R.attr.colorError,
            messageResId = R.string.error
        )
    }

    private fun setMessageNoConnection(): Boolean {
        return setMessageCustom(
            iconResId = R.drawable.ic_wifi_off_24,
            iconColorAttr = R.attr.colorPrimary,
            messageResId = R.string.internet_connection_error
        )
    }

    private fun setMessageCustom(
        @DrawableRes iconResId: Int,
        @AttrRes iconColorAttr: Int,
        @StringRes messageResId: Int
    ): Boolean {
        with(content) {
            textViewMessage.text = getString(messageResId)

            imageViewMessage.apply {
                setImageDrawable(drawable(iconResId))
                setColorFilter(attr(iconColorAttr), PorterDuff.Mode.SRC_IN)
            }
        }

        return true
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        cheeseburger.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        cheeseburger.onConfigurationChanged(newConfig)
    }

    private fun setupDrawerToggle() {
        cheeseburger = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                with(container) {

                    val slideX = drawerView.width * slideOffset
                    translationX = slideX
//                    val offset = slideOffset * (width / 100F)// 1%
//                    translationX = offset
//                    translationY = -offset
                }
            }
        }
        binding.drawerLayout.addDrawerListener(cheeseburger)
    }

    private fun initializeAnimators() {
        progressBar = HideableManager(this, content.progressBar)
        messageView = HideableManager(this, content.cardViewMessage)
    }
}