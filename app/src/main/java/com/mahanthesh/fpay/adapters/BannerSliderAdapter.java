package com.mahanthesh.fpay.adapters;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class BannerSliderAdapter extends SliderAdapter {
    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        switch (position){
            case 0:
                imageSlideViewHolder.bindImageSlide("https://image.freepik.com/free-vector/credit-score-isometric-landing-page-hand-holding-smartphone-with-application-meter-bank-consumer-loan-personal-rating-risk-control-banking-mobile-service-3d-line-art-web-banner-template_107791-3586.jpg");
                break;
            case 1:
                imageSlideViewHolder.bindImageSlide("https://image.freepik.com/free-vector/money-transfer-isometric-landing-page-online-bank_107791-1543.jpg");
                break;
            case 2:
                imageSlideViewHolder.bindImageSlide("https://image.freepik.com/free-vector/online-loan-banner-financial-lending-by-mobile-application-computer_107791-3002.jpg");
                break;
            case 3:
                imageSlideViewHolder.bindImageSlide("https://image.freepik.com/free-vector/guarantee-money-security-isometric-landing-page_107791-4684.jpg");
                break;
            case 4:
               imageSlideViewHolder.bindImageSlide("https://image.freepik.com/free-vector/smart-wallet-isometric-landing-page-web-banner_107791-1822.jpg");
                break;

        }

    }
}
