/*******************************************************************************
 * Copyright 2012 Steven Rudenko
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.arquitetaweb.restaurantes;

import shared.ui.actionscontentview.ActionsContentView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arquitetaweb.command.R;
import com.arquitetaweb.restaurantes.adapter.ActionsAdapter;
import com.arquitetaweb.restaurantes.fragment.AboutFragment;
import com.arquitetaweb.restaurantes.fragment.CardapioFragment;
import com.arquitetaweb.restaurantes.fragment.SandboxFragment;
import com.arquitetaweb.restaurantes.fragment.WebViewFragment;

public class ExamplesActivity extends FragmentActivity {

	private static final String STATE_URI = "state:uri";
	private static final String STATE_FRAGMENT_TAG = "state:fragment_tag";

	private ActionsContentView viewActionsContentView;

	private Uri currentUri = AboutFragment.ABOUT_URI;
	private String currentContentFragmentTag = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.example);

		viewActionsContentView = (ActionsContentView) findViewById(R.id.actionsContentView);
		viewActionsContentView.setSwipingType(ActionsContentView.SWIPING_ALL);

		final ListView viewActionsList = (ListView) findViewById(R.id.actions);
		final ActionsAdapter actionsAdapter = new ActionsAdapter(this);
		viewActionsList.setAdapter(actionsAdapter);
		viewActionsList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View v,
							int position, long flags) {
						final Uri uri = actionsAdapter.getItem(position);

						updateContent(uri);
						viewActionsContentView.showContent();
					}
				});

		if (savedInstanceState != null) {
			currentUri = Uri.parse(savedInstanceState.getString(STATE_URI));
			currentContentFragmentTag = savedInstanceState
					.getString(STATE_FRAGMENT_TAG);
		}

		updateContent(currentUri);
	}

	@Override
	public void onBackPressed() {
		final Fragment currentFragment = getSupportFragmentManager()
				.findFragmentByTag(currentContentFragmentTag);
		if (currentFragment instanceof WebViewFragment) {
			final WebViewFragment webFragment = (WebViewFragment) currentFragment;
			if (webFragment.onBackPressed())
				return;
		}

		super.onBackPressed();
	}

	public void onActionsButtonClick(View view) {
		if (viewActionsContentView.isActionsShown())
			viewActionsContentView.showContent();
		else
			viewActionsContentView.showActions();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(STATE_URI, currentUri.toString());
		outState.putString(STATE_FRAGMENT_TAG, currentContentFragmentTag);

		super.onSaveInstanceState(outState);
	}

	public void onSourceCodeClick(View view) {
		final Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(getString(R.string.sources_link)));
		startActivity(i);
	}

	public void updateContent(Uri uri) {
		final Fragment fragment;
		final String tag;

		final FragmentManager fm = getSupportFragmentManager();
		final FragmentTransaction tr = fm.beginTransaction();

		if (!currentUri.equals(uri)) {
			final Fragment currentFragment = fm
					.findFragmentByTag(currentContentFragmentTag);
			if (currentFragment != null)
				tr.hide(currentFragment);
		}

		if (AboutFragment.ABOUT_URI.equals(uri)) {
			tag = AboutFragment.TAG;
			final Fragment foundFragment = fm.findFragmentByTag(tag);
			if (foundFragment != null) {
				fragment = foundFragment;
			} else {
				fragment = new AboutFragment();
			}
		} else if (CardapioFragment.CARDAPIO_URI.equals(uri)) {
			tag = CardapioFragment.TAG;
			final Fragment foundFragment = fm.findFragmentByTag(tag);
			if (foundFragment != null) {
				fragment = foundFragment;
			} else {
				fragment = new CardapioFragment();
			}
		} else if (SandboxFragment.SETTINGS_URI.equals(uri)) {
			tag = SandboxFragment.TAG;
			final SandboxFragment foundFragment = (SandboxFragment) fm
					.findFragmentByTag(tag);
			if (foundFragment != null) {
				fragment = foundFragment;
			} else {
				final SandboxFragment settingsFragment = new SandboxFragment();
				fragment = settingsFragment;
			}
		} else if (uri != null) {
			tag = WebViewFragment.TAG;
			final WebViewFragment webViewFragment;
			final Fragment foundFragment = fm.findFragmentByTag(tag);
			if (foundFragment != null) {
				fragment = foundFragment;
				webViewFragment = (WebViewFragment) fragment;
			} else {
				webViewFragment = new WebViewFragment();
				fragment = webViewFragment;
			}
			webViewFragment.setUrl(uri.toString());
		} else {
			return;
		}

		if (fragment.isAdded()) {
			tr.show(fragment);
		} else {
			tr.replace(R.id.content, fragment, tag);
		}
		tr.commit();

		currentUri = uri;
		currentContentFragmentTag = tag;
	}	
}
