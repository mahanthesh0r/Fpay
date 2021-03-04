package com.mahanthesh.fpay.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.mahanthesh.fpay.R;
import com.mahanthesh.fpay.fragment.QRCodeFragment;
import com.mahanthesh.fpay.fragment.QRCodeScannerFragment;

import java.util.ArrayList;
import java.util.List;

public class QRCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPagerQrCode;
    private TabLayout tabLayoutQrCode;
    private QRCodeFragment qrCodeFragment;
    private QRCodeScannerFragment qrCodeScannerFragment;
    private ImageButton imageButtonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code);

        init();
        listeners();
    }

    private void listeners(){
        imageButtonCancel.setOnClickListener(this);
    }

    private void init() {
        viewPagerQrCode = findViewById(R.id.viewPagerQrCode);
        tabLayoutQrCode = findViewById(R.id.tabLayoutQrCode);
        qrCodeFragment = new QRCodeFragment();
        qrCodeScannerFragment = new QRCodeScannerFragment();
        tabLayoutQrCode.setupWithViewPager(viewPagerQrCode);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(qrCodeFragment, "QR Code");
        viewPagerAdapter.addFragment(qrCodeScannerFragment, "QR Code Scanner");
        viewPagerQrCode.setAdapter(viewPagerAdapter);
        imageButtonCancel = findViewById(R.id.imageButton_cancel);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.imageButton_cancel){
            finish();
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}