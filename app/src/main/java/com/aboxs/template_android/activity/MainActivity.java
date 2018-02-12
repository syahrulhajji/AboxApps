package com.aboxs.template_android.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboxs.template_android.BuildConfig;
import com.aboxs.template_android.R;
import com.aboxs.template_android.database.SharePreference;
import com.aboxs.template_android.fragment.LoginFragment;
import com.aboxs.template_android.fragment.AboxFragment;
import com.aboxs.template_android.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AboxActivity implements AboxFragment.FragmentInteractionListener {
    private LayoutInflater inflater;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public static final int CAMERA_REQUEST = 25;
    public static final int GALERY_REQUEST = 26;
    private Bitmap mBitmap;
    private Resources mResources;
    File f;
    private Bitmap bitmap;
    String ImageFile;
    private ImageView ivProfile;
    private SharePreference sharePreference;
    private Utility utility;
    final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

    @BindView(R.id.navigation_menu) NavigationView navigationMenu;
    @BindView(R.id.drawer_page) DrawerLayout drawerPage;
    @BindView(R.id.toolbar_title) Toolbar toolbarTitle;
    @BindView(R.id.tv_title) TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(checkAndRequestPermissions()) {}
        cannotReplaceFragment(new LoginFragment());
        sharePreference = new SharePreference(getApplicationContext());
        utility = new Utility();
        setSupportActionBar(toolbarTitle);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerPage, toolbarTitle, R.string.open, R.string.close);
        drawerPage.setDrawerListener(toggle);
        toggle.syncState();
        Utility utility = new Utility();

        mResources = getResources();
        View hView =  navigationMenu.getHeaderView(0);
        ivProfile = (ImageView)hView.findViewById(R.id.iv_profile);
        TextView tvName = (TextView)hView.findViewById(R.id.tv_name);
        mBitmap = BitmapFactory.decodeResource(mResources,R.drawable.ic_me);
        RoundedBitmapDrawable drawable = utility.createCircleImage(mBitmap);
        ivProfile.setImageDrawable(drawable);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add Photo :");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            try{
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                f = new File(android.os.Environment.getExternalStorageDirectory(), ".jpg");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID+".provider",f));
                                startActivityForResult(intent, CAMERA_REQUEST);
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(),"Please Enable Permission",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (options[item].equals("Choose from Gallery")) {
                            Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, GALERY_REQUEST);
                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        navigationMenu.inflateMenu(R.menu.item_menu);
        navigationMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.isChecked()){item.setChecked(false);}
                else{item.setChecked(true);}
                drawerPage.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_logout:
                        inflater = getLayoutInflater();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        View view = inflater.inflate(R.layout.dialog_exit,null);
                        builder.setView(view);
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        Button btnYes = (Button) view.findViewById(R.id.btn_yes);
                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cannotReplaceFragment(new LoginFragment());
                                alertDialog.dismiss();
                            }
                        });
                        Button btnNo = (Button)view.findViewById(R.id.btn_no);
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Wrong Choice ",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(resultCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            try {
                System.out.println("ImageFile : "+f);
                bitmap = decodeFile(f);
                ivProfile.setImageBitmap(bitmap);
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(f.getAbsolutePath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ImageFile = f.getAbsolutePath();
                sharePreference.saveImage(ImageFile);
            }catch (Exception e){e.printStackTrace();}
        }
        else if(requestCode == GALERY_REQUEST){
            if(data==null){
                Toast.makeText(getApplicationContext(),"Cancel Select Image",Toast.LENGTH_SHORT).show();
            }else {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getApplicationContext().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                ImageFile= c.getString(columnIndex);
                c.close();
                try {
                    sharePreference.saveImage(ImageFile);
                    bitmap = (BitmapFactory.decodeFile(ImageFile));
                    RoundedBitmapDrawable drawable = utility.createCircleImage(bitmap);
                    ivProfile.setImageDrawable(drawable);
                    FileOutputStream out = null;
                    try{
                        out = new FileOutputStream(ImageFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100, out);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        try{
                            if(out!=null){
                                out.close();
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    private Bitmap decodeFile(File f){
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            final int REQUIRED_SIZE=200;
            int scale=1;

            while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
                scale*=2;

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
    private  boolean checkAndRequestPermissions() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int callPhonePermision = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int WritePermision = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPhonePermision = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (callPhonePermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (WritePermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readPhonePermision != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)||
                                ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)||
                                ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                            showDialogOK("Camera Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    break;
                                            }
                                        }
                                    });
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
    @Override
    public void onFragmentInteraction(String title, boolean isTabSolid, boolean isTabVisible) {
//        title = getString(R.string.app_name);
        if(title!=null){
            tvTitle.setText(title);
        }toolbarTitle.setVisibility(isTabVisible?View.VISIBLE:View.GONE);
        if(isTabSolid) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                toolbarTitle.setBackgroundColor(getResources().getColor(R.color.colorGray, getTheme()));
            } else {
                toolbarTitle.setBackgroundColor(getResources().getColor(R.color.colorGray));
            }
            toolbarTitle.setVisibility(View.VISIBLE);
            DrawerLayout.LayoutParams layoutParam = (DrawerLayout.LayoutParams) navigationMenu.getLayoutParams();
            layoutParam.setMargins(layoutParam.leftMargin,0,layoutParam.rightMargin,layoutParam.bottomMargin);
        }else {
            toolbarTitle.setVisibility(View.GONE);
            DrawerLayout.LayoutParams layoutParam = (DrawerLayout.LayoutParams) navigationMenu.getLayoutParams();
            layoutParam.setMargins(layoutParam.leftMargin,layoutParam.topMargin+getToolbarHeight(),layoutParam.rightMargin,layoutParam.bottomMargin);
        }
    }

    @Override
    public int getToolbarHeight() {
        return toolbarTitle.getHeight();
    }

    public void hideMenuSlide(){
        drawerPage.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        navigationMenu.setVisibility(View.GONE);
        navigationMenu.setEnabled(false);
        toolbarTitle.setVisibility(View.GONE);
    }

    public void showMenuSlide(){
        drawerPage.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        navigationMenu.setEnabled(true);
        toolbarTitle.setVisibility(View.VISIBLE);
        navigationMenu.setVisibility(View.VISIBLE);
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.page_fragment, fragment).addToBackStack(null).commit();
    }
    public void cannotReplaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.page_fragment, fragment).commit();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE)
                && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
