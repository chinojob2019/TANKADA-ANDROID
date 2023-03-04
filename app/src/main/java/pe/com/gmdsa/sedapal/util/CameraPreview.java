package pe.com.gmdsa.sedapal.util;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by jmauriciog on 17/12/2015.
 */
@SuppressWarnings("deprecation")
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);

        mCamera = camera;
        mCamera.setDisplayOrientation(90);

        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setJpegQuality(100);

        List<Camera.Size> imageSizes = parameters.getSupportedPictureSizes();
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();

        for (int i = 0; i < imageSizes.size(); i++) {
            if (1200 <= imageSizes.get(i).width && imageSizes.get(i).width <= 1400) {
                parameters.setPictureSize(imageSizes.get(i).width, imageSizes.get(i).height);
                break;
            }
        }

        for (int i = 0; i < previewSizes.size(); i++) {
            if (1200 <= previewSizes.get(i).width && previewSizes.get(i).width <= 1400) {
                parameters.setPreviewSize(previewSizes.get(i).width, previewSizes.get(i).height);
                break;
            }
        }

        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(parameters);

        //get the holder and set this class as the callback, so we can get camera data here
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            //when the surface is created, we can set the camera to draw images in this surfaceholder
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera(mCamera);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
    }

    public void refreshCamera(Camera camera) {
        //before changing the application orientation, you need to stop the preview, rotate and then start it again
        if (mHolder.getSurface() == null)//check if the surface is ready to receive camera data
            return;

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            //this will happen when you are trying the camera if it's not running
        }
        mCamera = camera;

        //now, recreate the camera preview
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceChanged " + e.getMessage());
        }
    }
}
