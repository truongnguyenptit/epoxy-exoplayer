package com.airbnb.epoxy.kotlinsample.collageview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.airbnb.epoxy.kotlinsample.DebugLog;
import com.airbnb.epoxy.kotlinsample.R;
import com.gg.gapo.widget.collageview.CollageData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

public class CollageView extends LinearLayout {
    public static final int INVALID_ICON = -1;

    public static final int MAX_PHOTOS = 5;

    public final int SPLIT_PHOTOS = 2;

    private List<CollageData> urls;
    private int[] resIds;

    private int color = Color.TRANSPARENT;
    private int photoPadding = 0;
    private int photoMargin = 0;
    private int placeHolderResId = 0;
    private int photoFrameColor = Color.TRANSPARENT;
    private boolean useCards = false;
    private boolean useFirstAsHeader = true;

    private IconSelector iconSelector;

    private int defaultPhotosForLine = 3;

    private int maxWidth = -1;

    private int iconSize = 0;

    private int morePhotos = 0;

    private boolean isHorizontal;

    private ImageForm photosForm = ImageForm.IMAGE_FORM_SQUARE;
    private ImageForm headerForm = ImageForm.IMAGE_FORM_HALF_HEIGHT;

    private OnPhotoClickListener onPhotoClickListener;

    public CollageView(Context context) {
        super(context);
    }

    public CollageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CollageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CollageView backgroundColor(int color) {
        this.color = color;
        return this;
    }

    public CollageView photoPadding(int photoPadding) {
        this.photoPadding = photoPadding;
        return this;
    }

    public CollageView iconSelector(IconSelector iconSelector, int iconSize) {
        this.iconSelector = iconSelector;
        this.iconSize = iconSize;
        return this;
    }

    public CollageView iconSelector(IconSelector iconSelector) {
        this.iconSelector = iconSelector;
        return this;
    }

    public CollageView photoMargin(int photoMargin) {
        this.photoMargin = photoMargin;
        return this;
    }

    public CollageView maxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public CollageView morePhotos(int morePhotos) {
        this.morePhotos = morePhotos;
        return this;
    }

    public CollageView photoFrameColor(int photoFrameColor) {
        this.photoFrameColor = photoFrameColor;
        return this;
    }

    public CollageView placeHolder(int resId) {
        this.placeHolderResId = resId;
        return this;
    }

    public CollageView useCards(boolean useCards) {
        this.useCards = useCards;
        return this;
    }

//    public void loadPhotos(String[] urls) {
//        this.urls = new ArrayList<>(Arrays.asList(urls));
//        init();
//    }
//
//    public void loadPhotos(int[] resIds) {
//        this.resIds = resIds;
//        init();
//    }

    public void loadPhotos(List<CollageData> collageData) {
        List<CollageData> finalUrls = new ArrayList<>();
        int maxPhoto = collageData.size();
        if (collageData.size() > MAX_PHOTOS) {
            maxPhoto = MAX_PHOTOS;
            morePhotos = collageData.size() - maxPhoto;
        } else {
            morePhotos = 0;
        }
        if (collageData != null && !collageData.isEmpty()) {
            if (collageData.get(0).getHeight() > collageData.get(0).getWidth()) {
                isHorizontal = false;
            } else {
                isHorizontal = true;
            }
        }
        for (int i = 0; i < maxPhoto; i++) {

            finalUrls.add(collageData.get(i));
        }
        this.urls = finalUrls;
        init();
    }

    public CollageView useFirstAsHeader(boolean useFirstAsHeader) {
        this.useFirstAsHeader = useFirstAsHeader;
        return this;
    }

    public CollageView defaultPhotosForLine(int defaultPhotosForLine) {
        this.defaultPhotosForLine = defaultPhotosForLine;
        return this;
    }

    public CollageView photosForm(ImageForm photosForm) {
        this.photosForm = photosForm;
        return this;
    }

    public CollageView headerForm(ImageForm headerForm) {
        this.headerForm = headerForm;
        return this;
    }

    private void init() {
        boolean fromResources = resIds != null && urls == null;

        setOrientation(VERTICAL);
        setBackgroundColor(color);
        removeAllViews();
        ArrayList<CollageData> addUrls = new ArrayList<>();
        ArrayList<Integer> addRes = new ArrayList<>();

        ArrayList<Integer> photosCount = buildPhotosCounts();

        maxWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        if (urls != null || resIds != null) {
            int size = getPhotosSize();
            if (size > 0) {
                int number = 0;
                int i = 0;
                while (i < size) {
                    int photosInLine = photosCount.get(getChildCount());
                    if (fromResources) {
                        addRes.add(resIds[i]);
                    } else {
                        addUrls.add(urls.get(i));
                    }
                    number++;
                    if (number == photosInLine || size == i + 1) {
                        final LinearLayout photosLine = new LinearLayout(getContext());
                        photosLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        photosLine.setOrientation(LinearLayout.HORIZONTAL);
                        photosLine.setWeightSum(photosInLine * 1f);
                        for (int j = 0; j < photosInLine; j++) {
                            ViewGroup photoFrame;
                            photoFrame = new FrameLayout(getContext());
                            String url = null;
                            boolean isVideo = false;
                            int resId = 0;
                            int width = 0;
                            int height = 0;
                            String thumb = "";
                            int position = 0;
                            if (fromResources) {
                                resId = addRes.get(j);
                            } else {
                                url = addUrls.get(j).getUrl();
                                thumb = addUrls.get(j).getThumb();
                                isVideo = addUrls.get(j).isVideo();
                                width = addUrls.get(j).getWidth();
                                height = addUrls.get(j).getHeight();
                                position = addUrls.get(j).getPosition();
                            }
                            LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
                            layoutParams.setMargins(photoMargin, photoMargin, photoMargin, photoMargin);
                            photoFrame.setLayoutParams(layoutParams);
                            ImageForm imageForm = useFirstAsHeader && i == 0 ? headerForm : photosForm;
                            ImageView imageView = new RectangleImageView(getContext(), imageForm);
//                            imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            DebugLog.Companion.logD("height: " + height);
                            if (height > 500) {
                                imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) dipToPixels(500)));
                            } else {
                                imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            imageView.setAdjustViewBounds(true);
                            imageView.setBackgroundColor(photoFrameColor);
                            imageView.setPadding(photoPadding, photoPadding, photoPadding, photoPadding);
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            photoFrame.addView(imageView);

                            if (iconSelector != null
                                    && iconSelector.getIconResId(i - (photosInLine - j) + 1) != INVALID_ICON
                                    && iconSelector.getIconResId(i - (photosInLine - j) + 1) != 0) {
                                LinearLayout iconContainer = new LinearLayout(getContext());
                                iconContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                iconContainer.setGravity(Gravity.CENTER);
                                ImageView icon = new RectangleImageView(getContext(), ImageForm.IMAGE_FORM_SQUARE);
                                int side = iconSize == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : iconSize;
                                icon.setLayoutParams(new LayoutParams(side, side));
                                icon.setImageResource(iconSelector.getIconResId(i - (photosInLine - j) + 1));
                                iconContainer.addView(icon);
                                photoFrame.addView(iconContainer);
                            }

                            if (morePhotos != 0 && i == size - 1 && j == photosInLine - 1) {
                                AppCompatTextView textView = new AppCompatTextView(getContext());
                                textView.setTextAppearance(getContext(), R.style.TextAppearance_AppCompat_Display1);
                                textView.setTextColor(Color.WHITE);
                                textView.setText("+" + morePhotos);
                                textView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.semiTransparent));
                                textView.setGravity(Gravity.CENTER);
                                photoFrame.addView(textView);
                            }


                            try {
                                Picasso picasso = Picasso.get();
                                RequestCreator requestCreator = null;
                                if (fromResources) {
                                    if (resId != 0) {
                                        requestCreator = picasso.load(resId);
                                    }
                                } else {
                                    if (url != null && url.startsWith("http")) {
                                        requestCreator = picasso.load(url);
                                    }
                                }

                                if (maxWidth != -1) {
                                    assert requestCreator != null;
                                    requestCreator.resize(maxWidth, 0).onlyScaleDown();
                                }

                                requestCreator.resize(maxWidth, 0);

                                if (requestCreator != null) {
                                    if (placeHolderResId != 0) {
                                        requestCreator.placeholder(placeHolderResId);
                                    }
                                    requestCreator.into(imageView);
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                            photoFrame.setOnClickListener(null);
                            final int finalI = i - (photosInLine - j) + 1;
                            photoFrame.setOnClickListener(view -> {
                                if (onPhotoClickListener != null) {
                                    onPhotoClickListener.onPhotoClick(finalI);
                                }
                            });

                            if (isVideo) {
                                AppCompatImageView imagePlay = new AppCompatImageView(getContext());

                                LinearLayout iconContainer = new LinearLayout(getContext());
                                iconContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                iconContainer.setGravity(Gravity.CENTER);

                                int imageWidth = (int) (getResources().getDimension(R.dimen.image_play_video) / getResources().getDisplayMetrics().density);

                                if (i == 0 && j == 0) {
                                    imageWidth = (int) (getResources().getDimension(R.dimen.image_play_video_large) / getResources().getDisplayMetrics().density);
                                }

                                LayoutParams imagePlayLayoutParams = new LayoutParams(imageWidth, imageWidth);
                                imagePlayLayoutParams.gravity = Gravity.CENTER;
                                imagePlay.setLayoutParams(imagePlayLayoutParams);
                                imagePlay.setAdjustViewBounds(true);
                                imagePlay.setImageResource(R.drawable.ic_play_video_black);
                                iconContainer.addView(imagePlay);

                                photoFrame.addView(iconContainer);
                            }

                            photosLine.addView(photoFrame);
                        }
                        addView(photosLine);
                        addUrls.clear();
                        addRes.clear();
                        number = 0;
                    }
                    i++;
                }
            }
        }
    }

    public float dipToPixels(float dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem().getDisplayMetrics());
    }

    private Transformation cropPosterTransformation = new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (maxWidth * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, maxWidth, targetHeight, false);
            if (result != source) {
                // Same bitmap is returned if sizes are the same
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "cropPosterTransformation" + maxWidth;
        }
    };

    private int getPhotosSize() {
        int size = 0;
        if (urls != null) {
            size = urls.size();
        } else if (resIds != null) {
            size = resIds.length;
        }
        return size;
    }

    private ArrayList<Integer> buildPhotosCounts() {

        if (getPhotosSize() == 1) {
            headerForm = ImageForm.IMAGE_ORIGIN;
        } else if (getPhotosSize() == 2) {
            if (isHorizontal) {
                useFirstAsHeader = true;
                headerForm = ImageForm.IMAGE_FORM_HALF_HEIGHT;
                photosForm = ImageForm.IMAGE_FORM_HALF_HEIGHT;
            } else {
                useFirstAsHeader = false;
                photosForm = ImageForm.IMAGE_FORM_HALF_WIDTH;
            }
        } else {
            headerForm = ImageForm.IMAGE_FORM_HALF_HEIGHT;
            photosForm = ImageForm.IMAGE_FORM_SQUARE;
        }

        int headerDecreaser = useFirstAsHeader ? 1 : 0;
        int photosSize = getPhotosSize() - headerDecreaser;

        if (useFirstAsHeader && photosSize > 2) headerForm = ImageForm.IMAGE_FORM_HALF_HEIGHT;

        int remainder = photosSize % defaultPhotosForLine;
        int lineCount = photosSize / defaultPhotosForLine;

        ArrayList<Integer> photosCounts = new ArrayList<>();


//        if (photosSize == 1) {
//            headerDecreaser = 1;
//            remainder = 0;
//            lineCount = 0;
//        } else if (photosSize == SPLIT_PHOTOS) {
//            headerForm = ImageForm.IMAGE_FORM_SQUARE;
//            photosForm = ImageForm.IMAGE_FORM_SQUARE;
//
//            headerDecreaser = 1;
//
//            remainder = 0;
//            lineCount = 0;
//        } else if (photosSize == 3) {
//            headerDecreaser = 1;
//            remainder = 0;
//            lineCount = 2;
//        } else {
//
//        }

        if (useFirstAsHeader) {
            photosCounts.add(1);
            lineCount++;
        }
        for (int i = 0; i < lineCount; i++) {
            photosCounts.add(defaultPhotosForLine);
        }
        if (remainder >= lineCount) {
            photosCounts.add(headerDecreaser, remainder);
        } else {
            for (int i = lineCount - 1; i > lineCount - remainder - 1; i--) {
                photosCounts.set(i, photosCounts.get(i) + 1);
            }
        }

        return photosCounts;
    }

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    public interface OnPhotoClickListener {
        void onPhotoClick(int position);
    }


    public enum ImageForm {
        IMAGE_ORIGIN(1), IMAGE_FORM_SQUARE(2), IMAGE_FORM_HALF_HEIGHT(3), IMAGE_FORM_HALF_WIDTH(4);

        private int divider = 1;

        ImageForm(int divider) {
            this.divider = divider;
        }

        public int getDivider() {
            return divider;
        }
    }

    /**
     * Created by alan on 14.06.17.
     */

    public interface IconSelector {
        int getIconResId(int pos);
    }
}
