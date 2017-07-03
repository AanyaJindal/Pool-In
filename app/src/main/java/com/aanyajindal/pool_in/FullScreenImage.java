package com.aanyajindal.pool_in;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class FullScreenImage extends Fragment {



    public FullScreenImage() {
        // Required empty public constructor
    }

    public static FullScreenImage newInstance(Uri uri) {
        FullScreenImage fragment = new FullScreenImage();
        Bundle args = new Bundle();
        args.putString("hehe",uri.toString());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_full_screen_image, container, false);
        Bundle bundle = getArguments();
        Uri uri = Uri.parse(bundle.getString("hehe"));
        ImageView imageView = (ImageView) rootView.findViewById(R.id.iv_full_screen_image);
        imageView.setImageURI(uri);
        return rootView;
    }


}
