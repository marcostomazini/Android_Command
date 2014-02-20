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
package com.arquitetaweb.restaurantes.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arquitetaweb.command.R;

public class SandboxFragment extends Fragment implements View.OnClickListener {
	public static final String TAG = SandboxFragment.class.getSimpleName();

	public interface OnSettingsChangedListener {
		void onSettingChanged(int prefId, int value);
	}

	private static final String SETTINGS_SCHEME = "settings";
	private static final String SETTINGS_AUTHORITY = "sandbox";
	public static final Uri SETTINGS_URI = new Uri.Builder()
			.scheme(SETTINGS_SCHEME).authority(SETTINGS_AUTHORITY).build();

	private View viewRoot;

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		viewRoot = inflater.inflate(R.layout.sandbox, container, false);
		EditText deviceId = (EditText) viewRoot.findViewById(R.id.edtDeviceId);
		String devId = Secure.getString(viewRoot.getContext()
				.getContentResolver(), Secure.ANDROID_ID);
		deviceId.setText(devId);
		return viewRoot;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
}
