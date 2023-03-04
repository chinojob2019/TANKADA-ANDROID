package pe.com.gmdsa.sedapal.view.fragment;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import pe.com.gmdsa.sedapal.R;

public class ImagePreviewFragment extends Fragment {

    private ImageView imagePreview;
    private ImageButton cancelBtn, confirmBtn;

    private String imagePath;
    private int removalFlag;

    public static String TAG = "ImagePreviewFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imagePath = getArguments().getString("image_path");
        removalFlag = getArguments().getInt("removal_flag");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_imagepreview, container, false);

        imagePreview = (ImageView) view.findViewById(R.id.image_preview);
        cancelBtn = (ImageButton) view.findViewById(R.id.btn_cancel);
        confirmBtn = (ImageButton) view.findViewById(R.id.btn_confirm);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(this)
                .load(Uri.parse("file://" + imagePath))
                .into(imagePreview);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (removalFlag == 1) {
                    deleteFileIfExists(imagePath);
                }
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getFragmentManager().popBackStackImmediate();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void deleteFileIfExists(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
}
