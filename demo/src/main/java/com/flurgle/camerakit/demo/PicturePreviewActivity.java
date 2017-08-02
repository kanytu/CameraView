package com.flurgle.camerakit.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.flurgle.camerakit.AspectRatio;
import com.flurgle.camerakit.Size;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicturePreviewActivity extends Activity {

    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.nativeCaptureResolution)
    TextView nativeCaptureResolution;

    @BindView(R.id.actualResolution)
    TextView actualResolution;

    @BindView(R.id.approxUncompressedSize)
    TextView approxUncompressedSize;

    @BindView(R.id.captureLatency)
    TextView captureLatency;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_preview);
        ButterKnife.bind(this);

        Bitmap bitmap = ResultHolder.getImage();
        if (bitmap == null) {
            finish();
            return;
        }

        imageView.setImageBitmap(bitmap);

        // Native sizes are landscape, activity might now. <- not clear what this means but OK
        // TODO: ncr and ar might be different when cropOutput is true.
        AspectRatio aspectRatio = AspectRatio.of(bitmap.getHeight(), bitmap.getWidth());
        nativeCaptureResolution.setText(bitmap.getHeight() + " x " + bitmap.getWidth() + " (" + aspectRatio.toString() + ")");

        actualResolution.setText(bitmap.getWidth() + " x " + bitmap.getHeight());
        approxUncompressedSize.setText(getApproximateFileMegabytes(bitmap) + "MB");
        captureLatency.setText(ResultHolder.getTimeToCallback() + " milliseconds");
    }

    private static float getApproximateFileMegabytes(Bitmap bitmap) {
        return (bitmap.getRowBytes() * bitmap.getHeight()) / 1024 / 1024;
    }

}