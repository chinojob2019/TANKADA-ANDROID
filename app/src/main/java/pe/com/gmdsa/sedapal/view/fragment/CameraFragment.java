package pe.com.gmdsa.sedapal.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.view.adapter.GalleryAdapter;
import pe.com.gmdsa.sedapal.util.CameraPreview;

public class CameraFragment extends Fragment {

    private Camera mCamera;
    private FrameLayout cameraView;
    private CameraPreview mCameraPreview;
    private Camera.PictureCallback mPicture;
    private boolean frontalCamera;

    private ImageButton closeCamera, switchCameraBtn, capturePhotoBtn;
    private RecyclerView recyclerGallery;

    public static String TAG = "CameraFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frontalCamera = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        recyclerGallery = (RecyclerView) view.findViewById(R.id.recycler_album);
        cameraView = (FrameLayout) view.findViewById(R.id.camera_view);
        closeCamera = (ImageButton) view.findViewById(R.id.btn_close_camera);
        switchCameraBtn = (ImageButton) view.findViewById(R.id.btn_switch_camera);
        capturePhotoBtn = (ImageButton) view.findViewById(R.id.btn_capture_photo);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerGallery.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerGallery.setLayoutManager(layoutManager);

        GalleryAdapter galleryAdapter = new GalleryAdapter(getImagesPath(getActivity()), R.layout.item_gallery);
        recyclerGallery.setAdapter(galleryAdapter);

        recyclerGallery.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String imagePath = (String) view.getTag();
                        changeToImagePreview(imagePath, 0);
                    }
                })
        );

        mPicture = getPictureCallback();

        closeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        switchCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseCamera();
                switchCamera();
            }
        });

        capturePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCamera != null) {
                    capturePhotoBtn.setEnabled(false);
                    mCamera.takePicture(null, null, mPicture);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("on resume", "camera on resume");
        if (!hasCamera(getContext())) {
            getActivity().finish();
        }
        enableCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("on pause", "camera on pause");
        releaseCamera();
    }

    /**
     * Callback de la captura de imágen de la cámara
     *
     * @return PictureCallback
     */
    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                File pictureFile = getOutputMediaFile();
                if (pictureFile == null) {
                    mCameraPreview.refreshCamera(mCamera);
                    capturePhotoBtn.setEnabled(true);
                    return;
                }

                boolean imageSaved = false;
                try {
                    data = adjustImage(data, 1f, true, frontalCamera);
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    //Toast toast = Toast.makeText(ctx, "Picture saved: " + pictureFile.getAbsolutePath(), Toast.LENGTH_LONG);
                    //toast.show();
                    imageSaved = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //refresh camera to continue preview
                mCameraPreview.refreshCamera(mCamera);
                capturePhotoBtn.setEnabled(true);

                if (imageSaved) {
                    changeToImagePreview(pictureFile.getAbsolutePath(), 1);
                }

            }
        };
        return picture;
    }

    private void enableCamera() {
        if (mCamera == null) {
            if (findFrontFacingCamera() < 0) {
                switchCameraBtn.setVisibility(View.GONE);
            }
            openCamera(findBackFacingCamera());
            frontalCamera = false;
        }
    }

    /**
     * Libera objeto Camara y su Preview(GUI)
     */
    private void releaseCamera() {
        if (mCameraPreview != null) {
            mCameraPreview.getHolder().removeCallback(mCameraPreview);
            mCameraPreview = null;
        }
        // stop and release camera
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * ajustar una imagen
     *
     * @param image         imagen en bytes
     * @param scale         proporcion para escalar imagen, ejm 0.50
     * @param rotate        boolean para rotar la imagen 90°
     * @param frontalCamera boolean para invertir la imagen (si fue tomada por la camara frontal)
     * @return
     */
    public static byte[] adjustImage(byte[] image, float scale, boolean rotate, boolean frontalCamera) {

        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        if (rotate) {
            if (frontalCamera) {
                float[] mirrorY = {-1, 0, 0, 0, 1, 0, 0, 0, 1};
                Matrix matrixMirrorY = new Matrix();
                matrixMirrorY.setValues(mirrorY);

                matrix.postConcat(matrixMirrorY);
            }
            matrix.postRotate(90);
        }
        // scale resolution
        matrix.postScale(scale, scale);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                width, height, matrix, true);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        return stream.toByteArray();
    }

    /**
     * Cambia de camara (frontal o posterior)
     */
    private void switchCamera() {
        if (frontalCamera) {
            openCamera(findBackFacingCamera());
            frontalCamera = false;
        } else {
            openCamera(findFrontFacingCamera());
            frontalCamera = true;
        }
    }

    /**
     * Abre la camara de acuerdo al id
     *
     * @param idCamera id camara frontal o posterior
     */
    private void openCamera(int idCamera) {
        try {
            mCamera = Camera.open(idCamera);
        } catch (Exception e) {
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if (mCamera != null) {
            mCameraPreview = new CameraPreview(getContext(), mCamera);//create a SurfaceView to show camera data

            cameraView.removeAllViews();
            cameraView.addView(mCameraPreview);//add the SurfaceView to the layout
        } else {
            Log.d("ERROR", "Failed to show preview");
        }
    }

    /**
     * verifica si el dispositivo tiene camara disponible
     *
     * @param context
     * @return
     */
    private boolean hasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * obtiene el id de la camara posterior
     *
     * @return
     */
    private int findBackFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                break;
            }
        }

        return cameraId;
    }

    /**
     * obtiene el id de la camara frontal
     *
     * @return
     */
    private int findFrontFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    /**
     * genera ruta única para una imagen
     *
     * @return
     */
    private static File getOutputMediaFile() {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/", "InterrupcionesIMG");

        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        //and make a media file:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    /**
     * Obtiene la ruta de todas las imagenes del dispositivo
     *
     * @param activity
     * @return
     */
    public static ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<>();
        Cursor cursor;
        int column_index_data;
        String PathOfImage;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(PathOfImage);
        }
        cursor.close();
        return listOfAllImages;
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    /**
     * cambia de fragmento y muestra el visor de la imagen
     *
     * @param imagePath
     * @param removalFlag
     */
    private void changeToImagePreview(String imagePath, int removalFlag) {
        Fragment imagePreviewFragment = new ImagePreviewFragment();

        Bundle parameters = new Bundle();
        parameters.putString("image_path", imagePath);
        parameters.putInt("removal_flag", removalFlag);

        imagePreviewFragment.setArguments(parameters);
        FragmentManager FM = getActivity().getSupportFragmentManager();

        FragmentTransaction FT = FM.beginTransaction();
        FT.replace(R.id.camera_content, imagePreviewFragment, ImagePreviewFragment.TAG);
        FT.addToBackStack(CameraFragment.TAG);
        FT.commit();
    }
}