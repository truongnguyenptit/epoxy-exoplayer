package com.airbnb.epoxy.kotlinsample.collageview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class RectangleImageView extends AppCompatImageView {
    private CollageView.ImageForm imageForm = CollageView.ImageForm.IMAGE_FORM_SQUARE;

    public RectangleImageView(Context context, CollageView.ImageForm imageForm) {
        super(context);
        this.imageForm = imageForm;
    }

    public RectangleImageView(Context context, AttributeSet attrs, CollageView.ImageForm imageForm) {
        super(context, attrs);
        this.imageForm = imageForm;
    }

    public RectangleImageView(Context context, AttributeSet attrs, CollageView.ImageForm imageForm, int defStyle) {
        super(context, attrs, defStyle);
        this.imageForm = imageForm;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getParent() != null) {
            if (imageForm == CollageView.ImageForm.IMAGE_FORM_SQUARE) {
                getLayoutParams().height = widthMeasureSpec;
                setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
            } else if (imageForm == CollageView.ImageForm.IMAGE_FORM_HALF_WIDTH) {
                getLayoutParams().height = widthMeasureSpec * 2;
                setMeasuredDimension(widthMeasureSpec, widthMeasureSpec * 2);
            } else if (imageForm == CollageView.ImageForm.IMAGE_FORM_HALF_HEIGHT) {
                getLayoutParams().height = widthMeasureSpec / 2;
                setMeasuredDimension(widthMeasureSpec, widthMeasureSpec / 2);
            } else if (imageForm == CollageView.ImageForm.IMAGE_ORIGIN) {
//                getLayoutParams().height = heightMeasureSpec;
//                setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//                heightMeasureSpec = 500;

//                int hSize = MeasureSpec.getSize(heightMeasureSpec);
//                int hMode = MeasureSpec.getMode(heightMeasureSpec);
//
//                switch (hMode) {
//                    case MeasureSpec.AT_MOST:
//                        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(hSize, 500), MeasureSpec.AT_MOST);
//                        break;
//                    case MeasureSpec.UNSPECIFIED:
//                        heightMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.AT_MOST);
//                        break;
//                    case MeasureSpec.EXACTLY:
//                        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(hSize, 500), MeasureSpec.EXACTLY);
//                        break;
//                }
            }
        }
    }
}
