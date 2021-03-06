package com.example.rail.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.rail.MyDatabaseHelper;
import com.example.rail.R;
import com.example.rail.db.LoginUser;
import com.example.rail.util.ActivityCollector;
import com.example.rail.util.ImageUtils;
import com.example.rail.util.PhotoUtils;
import com.example.rail.util.ProvinceBean;
import com.example.rail.util.ToastUtils;
import com.example.rail.widget.ItemGroup;
import com.example.rail.widget.RoundImageView;
import com.example.rail.widget.TitleLayout;
import com.longsh.optionframelibrary.OptionBottomDialog;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class PersonInfo extends AppCompatActivity implements View.OnClickListener {
    private ItemGroup ig_id,ig_name,ig_gender,ig_region,ig_brithday;
    private LoginUser loginUser = LoginUser.getInstance();
    private LinearLayout ll_portrait;
    private ToastUtils mToast = new ToastUtils();

    private ArrayList<String> optionsItems_gender = new ArrayList<>();
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private OptionsPickerView pvOptions;
    private MyDatabaseHelper dbHelper;
    private RoundImageView ri_portrati;
    private PopupWindow popupWindow;
    private String imagePath;  //????????????????????????
    private PhotoUtils photoUtils = new PhotoUtils();

    private static final int EDIT_NAME = 4;
    private static final int EDIT_AGE = 5;
    private TitleLayout titleLayout;

    //??????????????????
    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private final static int CROP_IMAGE = 3;

    //imageUri??????????????????
    private Uri imageUri;
    //????????????
    File filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_person_info);
//
//
        initOptionData();

        ig_id = (ItemGroup)findViewById(R.id.ig_id);
        ig_name = (ItemGroup)findViewById(R.id.ig_name);
        ig_gender = (ItemGroup)findViewById(R.id.ig_gender);
        // ig_region = (ItemGroup)findViewById(R.id.ig_region);
        ig_brithday = (ItemGroup)findViewById(R.id.ig_brithday);
        ll_portrait = (LinearLayout)findViewById(R.id.ll_portrait);
        ri_portrati = (RoundImageView)findViewById(R.id.ri_portrait);
        titleLayout = (TitleLayout)findViewById(R.id.tl_title);

        ig_name.setOnClickListener(this);
        ig_gender.setOnClickListener(this);     //  ig_region.setOnClickListener(this);
        ig_brithday.setOnClickListener(this);
        ll_portrait.setOnClickListener(this);

        //???????????????????????????
        titleLayout.getTextView_forward().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new MyDatabaseHelper(PersonInfo.this);
                SQLiteDatabase sdb=dbHelper.getReadableDatabase();
                String sql="update user set sex = ? where id= ? ";
                String sql1="update user set username = ? where id= ? ";
                String sql2="update user set age = ? where id= ? ";
                LoginUser loginUser = LoginUser.getInstance();
                Object obj[]={loginUser.getGender(),loginUser.getId()};
                Object obj1[]={loginUser.getName(),loginUser.getId()};
                Object obj2[]={loginUser.getAge(),loginUser.getId()};
                sdb.execSQL(sql,obj);
                sdb.execSQL(sql1,obj1);
                sdb.execSQL(sql2,obj2);
                loginUser.update();

                mToast.showShort(PersonInfo.this,"????????????");
                finish();
            }
        });
//        initOptionData();
//
        ig_id = (ItemGroup)findViewById(R.id.ig_id);
        ig_name = (ItemGroup)findViewById(R.id.ig_name);
        ig_gender = (ItemGroup)findViewById(R.id.ig_gender);
//        ig_region = (ItemGroup)findViewById(R.id.ig_region);
        ig_brithday = (ItemGroup)findViewById(R.id.ig_brithday);
        ll_portrait = (LinearLayout)findViewById(R.id.ll_portrait);
        ri_portrati = (RoundImageView)findViewById(R.id.ri_portrait);
        titleLayout = (TitleLayout)findViewById(R.id.tl_title);
//
//        ig_name.setOnClickListener(this);
//        ig_gender.setOnClickListener(this);
//        ig_region.setOnClickListener(this);
//        ig_brithday.setOnClickListener(this);
        ll_portrait.setOnClickListener(this);
//
//        //???????????????????????????
//        titleLayout.getTextView_forward().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //loginUser.update();
//                mToast.showShort(PersonInfo.this,"????????????");
//                finish();
//            }
//        });

        initInfo();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //??????????????????loginUser?????????????????????????????????????????????????????????
        loginUser.reinit();
        ActivityCollector.removeActivity(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            //????????????????????????
//            case R.id.ig_region:
//                pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                        //???????????????????????????LoginUser?????????????????????????????????
//                        String tx = options1Items.get(options1).getPickerViewText()
//                                + options2Items.get(options1).get(options2);
//                       // ig_region.getContentEdt().setText(tx);
//                       // loginUser.setRegion(tx);
//                    }
//                }).setCancelColor(Color.GRAY).build();
//                pvOptions.setPicker(options1Items, options2Items);//???????????????
//                pvOptions.show();
//                break;

            //????????????????????????
            case R.id.ig_gender:
                //???????????????
                pvOptions = new OptionsPickerBuilder(PersonInfo.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 , View v) {
//                        //???????????????????????????LoginUser?????????????????????????????????
                        String tx = optionsItems_gender.get(options1);
                        ig_gender.getContentEdt().setText(tx);
                        loginUser.setGender(tx);
                    }
                }).setCancelColor(Color.GRAY).build();
                pvOptions.setPicker(optionsItems_gender);
                pvOptions.show();
                break;

            //????????????????????????
            case R.id.ig_brithday:
                Intent intent  = new Intent(PersonInfo.this, EditAge.class);
                startActivityForResult(intent, EDIT_AGE);
                break;
            //???????????????????????????
            case R.id.ll_portrait:
                //????????????????????????????????????????????????
                show_popup_windows();
                break;
            //???????????????????????????
            case R.id.ig_name:
                Intent intent1  = new Intent(PersonInfo.this, EditName.class);
                startActivityForResult(intent1, EDIT_NAME);
                break;
            default:
                break;
        }
    }
    //????????????????????????
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        // switch (requestCode){
//            //??????????????????
//            case TAKE_PHOTO:
//                if(resultCode == RESULT_OK){
//                    try {
//                        //??????????????????????????????????????????
//                        Bitmap bitmap = BitmapFactory.decodeStream((getContentResolver().openInputStream(imageUri)));
//                        ri_portrati.setImageBitmap(bitmap);
//                        loginUser.setPortrait(photoUtils.bitmap2byte(bitmap));
//                    }catch (FileNotFoundException e){
//                        e.printStackTrace();
//                    }
//                }
//                break;
//            //????????????????????????
//            case FROM_ALBUMS:
//                if(resultCode == RESULT_OK){
//                    //?????????????????????
//                    if(Build.VERSION.SDK_INT >= 19){
//                        imagePath =  photoUtils.handleImageOnKitKat(this, data);
//                    }else {
//                        imagePath = photoUtils.handleImageBeforeKitKat(this, data);
//                    }
//                }
//                if(imagePath != null){
//                    //??????????????????????????????????????????
//                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//                    ri_portrati.setImageBitmap(bitmap);
//                    loginUser.setPortrait(photoUtils.bitmap2byte(bitmap));
//                }else{
//                    Log.d("food","??????????????????");
//                }
//                break;
//            //???????????????????????????????????????
//            case EDIT_NAME:
//                if(resultCode == RESULT_OK){
//                    ig_name.getContentEdt().setText(loginUser.getName());
//                }
//                break;
//            default:
//                break;
//        }
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //??????????????????????????????????????????
                    //??????????????????????????????BitmapFactory???decodeStream()????????????????????????Bitmap?????????????????????
                    Log.i( "Test", "onActivityResult TakePhoto : "+imageUri );
                    //Bitmap bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageUri ) );
                    //takephoto.setImageBitmap( bitmap );
                    //???????????????????????????????????????
                    File saveFile = ImageUtils.getTempFile();
                    filePath = ImageUtils.getTempFile();
                    startImageCrop( saveFile,imageUri );
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //????????????????????????
                    Log.i( "Test", "onActivityResult: ????????????????????????" );
                    try {
                        imageUri = data.getData(); //??????????????????????????????Uri
                        Log.i( "Test", "onActivityResult: uriImage is " +imageUri );
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(imageUri,
                                filePathColumn, null, null, null);//???????????????????????????Uri???????????????
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //??????????????????
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        //photo_taken.setImageBitmap(bitmap);
                        //???????????????????????????????????????
                        File saveFile = ImageUtils.setTempFile(PersonInfo.this);
                        filePath = ImageUtils.getTempFile();
                        startImageCrop( saveFile,imageUri );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CROP_IMAGE:
                if(resultCode == RESULT_OK){
                    Log.i( "Test", "onActivityResult: CROP_IMAGE" + "?????????CROP");
                    // ????????????URI??????????????????
                    //bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageUri ) );
                    //??????FileName????????????
                    Bitmap bitmap = BitmapFactory.decodeFile( filePath.toString() );
                    Bitmap bitmap1 = BitmapFactory.decodeFile( filePath.toString() );
                    int id = LoginUser.login_user.getId();
                    Log.d("Test",Integer.toString(id));
                    String sql="update user set image = ? where id= ? ";
                    dbHelper=new MyDatabaseHelper(this);
                    SQLiteDatabase sdb=dbHelper.getWritableDatabase();
                    Object obj[]={bitmabToBytes(bitmap),id};
                    sdb.execSQL(sql,obj);
                    String sql1="select * from user where id=? ";
                    Cursor cursor=sdb.rawQuery(sql1, new String[]{Integer.toString(id)});
                    byte[] imgData=null;
                    if(cursor.moveToNext()){
                        //???Blob???????????????????????????
                        imgData=cursor.getBlob(cursor.getColumnIndex("image"));
                        Bitmap imagebitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                        //????????????????????????
                        ri_portrati.setImageBitmap(imagebitmap);
                    }
                    //?????????????????????????????????
//                    ri_portrati.setImageBitmap(bitmap1);
//                    ContentValues values = new ContentValues();
//                    values.put("id",id);
//                    sd b.update("user",values,"image=?",new String[]{String.valueOf(bitmabToBytes(bitmap))});
                    //ImageUtils.Compress( bitmap );
                }
                break;
            case EDIT_NAME:
                if(resultCode == RESULT_OK){
                    ig_name.getContentEdt().setText(loginUser.getName());
                }
                break;
            case EDIT_AGE:
                if(resultCode == RESULT_OK){
                    ig_brithday.getContentEdt().setText(Integer.toString(loginUser.getAge()));
                }
            default:
                break;
        }
    }
    //???????????????????????????????????????
    private void initInfo(){
        LoginUser loginUser = LoginUser.getInstance();
        ig_id.getContentEdt().setText(String.valueOf(loginUser.getId()));  //ID???int??????string
        ig_name.getContentEdt().setText(loginUser.getName());
        //   ri_portrati.setImageBitmap(photoUtils.byte2bitmap(loginUser.getPortrait()));
        ig_gender.getContentEdt().setText(loginUser.getGender());
        // ig_region.getContentEdt().setText(loginUser.getRegion());
        ig_brithday.getContentEdt().setText(String.valueOf(loginUser.getAge()));
        String sql1="select * from user where id=? ";
        int id = LoginUser.login_user.getId();
        dbHelper=new MyDatabaseHelper(this);
        SQLiteDatabase sdb=dbHelper.getWritableDatabase();
        Cursor cursor=sdb.rawQuery(sql1, new String[]{Integer.toString(id)});
        byte[] imgData=null;
        if(cursor.moveToNext()){
            //???Blob???????????????????????????
            imgData=cursor.getBlob(cursor.getColumnIndex("image"));
            if (imgData!=null){
                Bitmap imagebitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                //????????????????????????
                ri_portrati.setImageBitmap(imagebitmap);
            }

        }
    }

    //??????????????????????????????????????????
    private void initOptionData(){
        //?????????????????????
        optionsItems_gender.add(new String("??????"));
        optionsItems_gender.add(new String("???"));
        optionsItems_gender.add(new String("???"));

        //?????????????????????
//        String province_data = readJsonFile("province.json");
//        String city_data = readJsonFile("city.json");
//
//        Gson gson = new Gson();
//
//        options1Items = gson.fromJson(province_data, new TypeToken<ArrayList<ProvinceBean>>(){}.getType());
//        ArrayList<CityBean> cityBean_data = gson.fromJson(city_data, new TypeToken<ArrayList<CityBean>>(){}.getType());
//        for(ProvinceBean provinceBean:options1Items){
//            ArrayList<String> temp = new ArrayList<>();
//            for (CityBean cityBean : cityBean_data){
//                if(provinceBean.getProvince().equals(cityBean.getProvince())){
//                    temp.add(cityBean.getName());
//                }
//            }
//            options2Items.add(temp);
        //  }

    }

    //?????????asset????????????json?????????
    //??????????????????String
    private String readJsonFile(String file){
        StringBuilder newstringBuilder = new StringBuilder();
        try {
            InputStream inputStream = getResources().getAssets().open(file);

            InputStreamReader isr = new InputStreamReader(inputStream);

            BufferedReader reader = new BufferedReader(isr);

            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                newstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data =  newstringBuilder.toString();
        return data;
    }

    //???????????????????????????????????????????????????????????????
    private void show_popup_windows(){
        List<String> stringList = new ArrayList<String>();
        stringList.add("??????");
        stringList.add("???????????????");
        final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(PersonInfo.this, stringList);
        optionBottomDialog.setItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
//????????????
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //??????????????????????????????????????????????????????
                switch (position) {
                    case 0:
                        //??????????????????????????????position= 0
                        //Toast.makeText(RegisterIn.this,"position 0", Toast.LENGTH_SHORT ).show();

                        //??????????????????
                        //??????Intent
                        Intent intent_photo = new Intent( "android.media.action.IMAGE_CAPTURE" );
                        //putExtra()???????????????????????????????????????????????????Uri??????
                        imageUri = ImageUtils.getImageUri( PersonInfo.this );
                        intent_photo.putExtra( MediaStore.EXTRA_OUTPUT, imageUri );
                        //startActivity( intent_photo );
                        startActivityForResult( intent_photo, TAKE_PHOTO );
                        //??????????????????
                        optionBottomDialog.dismiss();
                        break;
                    case 1:
                        //??????????????????????????????position= 1
                        //Toast.makeText(RegisterIn.this,"position 1", Toast.LENGTH_SHORT ).show();
                        //????????????
                        openAlbum();
                        //??????????????????
                        optionBottomDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
//        RelativeLayout layout_photo_selected = (RelativeLayout) getLayoutInflater().inflate(R.layout.photo_select,null);
//        if(popupWindow==null){
//            popupWindow = new PopupWindow(layout_photo_selected, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
//        }
//        //??????popupwindows
//        popupWindow.showAtLocation(layout_photo_selected, Gravity.CENTER, 0, 0);
//        //???????????????
//        TextView take_photo =  (TextView) layout_photo_selected.findViewById(R.id.take_photo);
//        TextView from_albums = (TextView)  layout_photo_selected.findViewById(R.id.from_albums);
//        LinearLayout cancel = (LinearLayout) layout_photo_selected.findViewById(R.id.cancel);
//        //??????????????????
//        take_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(popupWindow != null && popupWindow.isShowing()) {
//                    /*imageUri = photoUtils.take_photo_util(PersonInfo.this, "com.foodsharetest.android.fileprovider", "output_image.jpg");
//                    //????????????????????????????????????imageUri?????????outputImage???
//                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                    intent.putExtra(EXTRA_OUTPUT, imageUri);
//                    startActivityForResult(intent, TAKE_PHOTO);*/
//                    //???????????????
//                    popupWindow.dismiss();
//                }
//            }
//        });
//        //??????????????????
//        from_albums.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //????????????
//                /*if(ContextCompat.checkSelfPermission(PersonInfo.this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(PersonInfo.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                }else {
//                    //????????????
//                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
//                    intent.setType("image/*");
//                    startActivityForResult(intent, FROM_ALBUMS);
//                }*/
//                //???????????????
//                popupWindow.dismiss();
//            }
//        });
//        //??????????????????
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (popupWindow != null && popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                }
//            }
//        });

    }
    //????????????
    private void startImageCrop(File saveToFile,Uri uri) {
        if(uri == null){
            return ;
        }
        Intent intent = new Intent( "com.android.camera.action.CROP" );
        Log.i( "Test", "startImageCrop: " + "????????????????????????" + "uri is " + uri );
        intent.setDataAndType( uri, "image/*" );//??????Uri?????????
        //uri??????????????????????????????   ????????????????????????????????????
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra( "crop", "true" );//
        intent.putExtra( "aspectX", 1 );//X??????????????????
        intent.putExtra( "aspectY", 1 );//Y??????????????????
        intent.putExtra( "outputX", 150 );//????????????X?????????
        intent.putExtra( "outputY", 150 );//????????????Y?????????
        intent.putExtra( "scale", true );//??????????????????
        intent.putExtra( "outputFormat", Bitmap.CompressFormat.PNG.toString() );
        intent.putExtra( "return-data", false );//????????????????????????Bitmap?????????dataParcelable?????????Bitmap?????????????????????OOM?????????false
        //????????????????????????
        //File saveToFile = ImageUtils.getTempFile();
        if (!saveToFile.getParentFile().exists()) {
            saveToFile.getParentFile().mkdirs();
        }
        //???????????????????????????????????????
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveToFile));
        Log.i( "Test", "startImageCrop: " + "????????????????????????" );
        startActivityForResult( intent, CROP_IMAGE );
    }

    //????????????
    private void openAlbum() {
        Intent intent_album = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );

        startActivityForResult( intent_album, CHOOSE_PHOTO );
    }

    //???????????????????????????
    public byte[] bitmabToBytes(Bitmap bitmap){
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        //?????????????????????????????????,???????????????size
        ByteArrayOutputStream baos= new ByteArrayOutputStream(size);
        try {
            //???????????????????????????????????????100%????????????????????????????????????
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //?????????????????????????????????????????????byte[]
            byte[] imagedata = baos.toByteArray();
            return imagedata;
        }catch (Exception e){
        }finally {
            try {
                bitmap.recycle();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

}
