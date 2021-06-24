package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Context.BIND_ADJUST_WITH_ACTIVITY;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ArActivity extends AppCompatActivity {

    TextView textName;
    String productid;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference modelRef;
    private ArFragment arFragment;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        productid = getIntent().getStringExtra("id");
        Log.d("Furqan", "onCreate:" + productid);

        FirebaseApp.initializeApp(this);
        reference = FirebaseDatabase.getInstance().getReference().child("product").child(productid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                product = dataSnapshot.getValue(Product.class);
                System.out.println(product);
                Log.d("product",product.path);

                storage = FirebaseStorage.getInstance();
                modelRef = storage.getReference().child(product.path);

                arFragment = (ArFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.arFragment);

                reference.get();

                findViewById(R.id.downloadBtn)
                        .setOnClickListener(v -> {
                            try {
                                File file = File.createTempFile(product.name,product.extension);
                                modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        buildModel(file);
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        });

                arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                    AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
                    anchorNode.setRenderable(renderable);
                    arFragment.getArSceneView().getScene().addChild(anchorNode);
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        findViewById(R.id.ssButton).setOnClickListener(v -> {
            View view1 = getWindow().getDecorView().getRootView();
            view1.setDrawingCacheEnabled(true);

            Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
            view1.setDrawingCacheEnabled(false);

            String filepath = Environment.getExternalStorageDirectory()+"/Download/"+ Calendar.getInstance().getTime().toString()+".jpg";
            File fileScreenshot = new File(filepath);

            FileOutputStream fileOutputStream = null;

            try {
                fileOutputStream = new FileOutputStream(fileScreenshot);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(fileScreenshot);
            intent.setDataAndType(uri,"image/jpeg");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        });
    }
//
//    public void ScreenshotButton(View view){
//        View view1 = getWindow().getDecorView().getRootView();
//        view1.setDrawingCacheEnabled(true);
//
//        Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
//        view1.setDrawingCacheEnabled(false);
//
//        String filepath = Environment.getExternalStorageDirectory()+"/Download/"+ Calendar.getInstance().getTime().toString()+".jpg";
//        File fileScreenshot = new File(filepath);
//
//        FileOutputStream fileOutputStream = null;
//
//        try {
//            fileOutputStream = new FileOutputStream(fileScreenshot);
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
//            fileOutputStream.flush();
//            fileOutputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(fileScreenshot);
//        intent.setDataAndType(uri,"image/jpeg");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        this.startActivity(intent);
//
//    }

    private ModelRenderable renderable;

    private void buildModel(File file) {
        RenderableSource renderableSource = RenderableSource
                .builder()
                .setSource(this, Uri.parse(file.getPath()),RenderableSource.SourceType.GLB)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build();

        ModelRenderable
                .builder()
                .setSource(this, renderableSource)
                .setRegistryId(file.getPath())
                .build()
                .thenAccept(modelRenderable -> {
                    Toast.makeText(this,"Model Build",Toast.LENGTH_SHORT).show();
                    renderable = modelRenderable;
                });
    }

}