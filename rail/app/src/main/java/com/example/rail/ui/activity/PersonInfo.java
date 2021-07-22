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
    private String imagePath;  //从相册中选的地址
    private PhotoUtils photoUtils = new PhotoUtils();

    private static final int EDIT_NAME = 4;
    private static final int EDIT_AGE = 5;
    private TitleLayout titleLayout;

    //拍照功能参数
    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private final static int CROP_IMAGE = 3;

    //imageUri照片真实路径
    private Uri imageUri;
    //照片存储
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

        //设置点击保存的逻辑
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

                mToast.showShort(PersonInfo.this,"保存成功");
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
//        //设置点击保存的逻辑
//        titleLayout.getTextView_forward().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //loginUser.update();
//                mToast.showShort(PersonInfo.this,"保存成功");
//                finish();
//            }
//        });

        initInfo();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //如果是退出则loginUser的数据重新初始化（也就是不保存数据库）
        loginUser.reinit();
        ActivityCollector.removeActivity(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            //点击修改地区逻辑
//            case R.id.ig_region:
//                pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                        //选择了则显示并暂存LoginUser，退出时在保存至数据库
//                        String tx = options1Items.get(options1).getPickerViewText()
//                                + options2Items.get(options1).get(options2);
//                       // ig_region.getContentEdt().setText(tx);
//                       // loginUser.setRegion(tx);
//                    }
//                }).setCancelColor(Color.GRAY).build();
//                pvOptions.setPicker(options1Items, options2Items);//二级选择器
//                pvOptions.show();
//                break;

            //点击修改性别逻辑
            case R.id.ig_gender:
                //性别选择器
                pvOptions = new OptionsPickerBuilder(PersonInfo.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 , View v) {
//                        //选择了则显示并暂存LoginUser，退出时在保存至数据库
                        String tx = optionsItems_gender.get(options1);
                        ig_gender.getContentEdt().setText(tx);
                        loginUser.setGender(tx);
                    }
                }).setCancelColor(Color.GRAY).build();
                pvOptions.setPicker(optionsItems_gender);
                pvOptions.show();
                break;

            //点击修改年龄逻辑
            case R.id.ig_brithday:
                Intent intent  = new Intent(PersonInfo.this, EditAge.class);
                startActivityForResult(intent, EDIT_AGE);
                break;
            //点击修改头像的逻辑
            case R.id.ll_portrait:
                //展示选择框，并设置选择框的监听器
                show_popup_windows();
                break;
            //点击修改名字的逻辑
            case R.id.ig_name:
                Intent intent1  = new Intent(PersonInfo.this, EditName.class);
                startActivityForResult(intent1, EDIT_NAME);
                break;
            default:
                break;
        }
    }
    //处理拍摄照片回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        // switch (requestCode){
//            //拍照得到图片
//            case TAKE_PHOTO:
//                if(resultCode == RESULT_OK){
//                    try {
//                        //将拍摄的图片展示并更新数据库
//                        Bitmap bitmap = BitmapFactory.decodeStream((getContentResolver().openInputStream(imageUri)));
//                        ri_portrati.setImageBitmap(bitmap);
//                        loginUser.setPortrait(photoUtils.bitmap2byte(bitmap));
//                    }catch (FileNotFoundException e){
//                        e.printStackTrace();
//                    }
//                }
//                break;
//            //从相册中选择图片
//            case FROM_ALBUMS:
//                if(resultCode == RESULT_OK){
//                    //判断手机版本号
//                    if(Build.VERSION.SDK_INT >= 19){
//                        imagePath =  photoUtils.handleImageOnKitKat(this, data);
//                    }else {
//                        imagePath = photoUtils.handleImageBeforeKitKat(this, data);
//                    }
//                }
//                if(imagePath != null){
//                    //将拍摄的图片展示并更新数据库
//                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//                    ri_portrati.setImageBitmap(bitmap);
//                    loginUser.setPortrait(photoUtils.bitmap2byte(bitmap));
//                }else{
//                    Log.d("food","没有找到图片");
//                }
//                break;
//            //如果是编辑名字，则修改展示
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
                    //需要对拍摄的照片进行处理编辑
                    //拍照成功的话，就调用BitmapFactory的decodeStream()方法把图片解析成Bitmap对象，然后显示
                    Log.i( "Test", "onActivityResult TakePhoto : "+imageUri );
                    //Bitmap bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageUri ) );
                    //takephoto.setImageBitmap( bitmap );
                    //设置照片存储文件及剪切图片
                    File saveFile = ImageUtils.getTempFile();
                    filePath = ImageUtils.getTempFile();
                    startImageCrop( saveFile,imageUri );
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //选中相册照片显示
                    Log.i( "Test", "onActivityResult: 执行到打开相册了" );
                    try {
                        imageUri = data.getData(); //获取系统返回的照片的Uri
                        Log.i( "Test", "onActivityResult: uriImage is " +imageUri );
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(imageUri,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        //photo_taken.setImageBitmap(bitmap);
                        //设置照片存储文件及剪切图片
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
                    Log.i( "Test", "onActivityResult: CROP_IMAGE" + "进入了CROP");
                    // 通过图片URI拿到剪切图片
                    //bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageUri ) );
                    //通过FileName拿到图片
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
                        //将Blob数据转化为字节数组
                        imgData=cursor.getBlob(cursor.getColumnIndex("image"));
                        Bitmap imagebitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                        //将位图显示为图片
                        ri_portrati.setImageBitmap(imagebitmap);
                    }
                    //把裁剪后的图片展示出来
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
    //从数据库中初始化数据并展示
    private void initInfo(){
        LoginUser loginUser = LoginUser.getInstance();
        ig_id.getContentEdt().setText(String.valueOf(loginUser.getId()));  //ID是int，转string
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
            //将Blob数据转化为字节数组
            imgData=cursor.getBlob(cursor.getColumnIndex("image"));
            if (imgData!=null){
                Bitmap imagebitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                //将位图显示为图片
                ri_portrati.setImageBitmap(imagebitmap);
            }

        }
    }

    //初始化性别、地址和生日的数据
    private void initOptionData(){
        //性别选择器数据
        optionsItems_gender.add(new String("保密"));
        optionsItems_gender.add(new String("男"));
        optionsItems_gender.add(new String("女"));

        //地址选择器数据
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

    //传入：asset文件夹中json文件名
    //返回：读取的String
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

    //展示修改头像的选择框，并设置选择框的监听器
    private void show_popup_windows(){
        List<String> stringList = new ArrayList<String>();
        stringList.add("拍照");
        stringList.add("从相册选择");
        final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(PersonInfo.this, stringList);
        optionBottomDialog.setItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
//点击事件
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击事件的逻辑，这里统一写成取消弹框
                switch (position) {
                    case 0:
                        //测试使用，验证是否为position= 0
                        //Toast.makeText(RegisterIn.this,"position 0", Toast.LENGTH_SHORT ).show();

                        //启动相机程序
                        //隐式Intent
                        Intent intent_photo = new Intent( "android.media.action.IMAGE_CAPTURE" );
                        //putExtra()指定图片的输出地址，填入之前获得的Uri对象
                        imageUri = ImageUtils.getImageUri( PersonInfo.this );
                        intent_photo.putExtra( MediaStore.EXTRA_OUTPUT, imageUri );
                        //startActivity( intent_photo );
                        startActivityForResult( intent_photo, TAKE_PHOTO );
                        //底部弹框消失
                        optionBottomDialog.dismiss();
                        break;
                    case 1:
                        //测试使用，验证是否为position= 1
                        //Toast.makeText(RegisterIn.this,"position 1", Toast.LENGTH_SHORT ).show();
                        //打开相册
                        openAlbum();
                        //底部弹框消失
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
//        //显示popupwindows
//        popupWindow.showAtLocation(layout_photo_selected, Gravity.CENTER, 0, 0);
//        //设置监听器
//        TextView take_photo =  (TextView) layout_photo_selected.findViewById(R.id.take_photo);
//        TextView from_albums = (TextView)  layout_photo_selected.findViewById(R.id.from_albums);
//        LinearLayout cancel = (LinearLayout) layout_photo_selected.findViewById(R.id.cancel);
//        //拍照按钮监听
//        take_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(popupWindow != null && popupWindow.isShowing()) {
//                    /*imageUri = photoUtils.take_photo_util(PersonInfo.this, "com.foodsharetest.android.fileprovider", "output_image.jpg");
//                    //调用相机，拍摄结果会存到imageUri也就是outputImage中
//                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                    intent.putExtra(EXTRA_OUTPUT, imageUri);
//                    startActivityForResult(intent, TAKE_PHOTO);*/
//                    //去除选择框
//                    popupWindow.dismiss();
//                }
//            }
//        });
//        //相册按钮监听
//        from_albums.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //申请权限
//                /*if(ContextCompat.checkSelfPermission(PersonInfo.this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(PersonInfo.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                }else {
//                    //打开相册
//                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
//                    intent.setType("image/*");
//                    startActivityForResult(intent, FROM_ALBUMS);
//                }*/
//                //去除选择框
//                popupWindow.dismiss();
//            }
//        });
//        //取消按钮监听
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (popupWindow != null && popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                }
//            }
//        });

    }
    //剪切图片
    private void startImageCrop(File saveToFile,Uri uri) {
        if(uri == null){
            return ;
        }
        Intent intent = new Intent( "com.android.camera.action.CROP" );
        Log.i( "Test", "startImageCrop: " + "执行到压缩图片了" + "uri is " + uri );
        intent.setDataAndType( uri, "image/*" );//设置Uri及类型
        //uri权限，如果不加的话，   会产生无法加载图片的问题
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra( "crop", "true" );//
        intent.putExtra( "aspectX", 1 );//X方向上的比例
        intent.putExtra( "aspectY", 1 );//Y方向上的比例
        intent.putExtra( "outputX", 150 );//裁剪区的X方向宽
        intent.putExtra( "outputY", 150 );//裁剪区的Y方向宽
        intent.putExtra( "scale", true );//是否保留比例
        intent.putExtra( "outputFormat", Bitmap.CompressFormat.PNG.toString() );
        intent.putExtra( "return-data", false );//是否将数据保留在Bitmap中返回dataParcelable相应的Bitmap数据，防止造成OOM，置位false
        //判断文件是否存在
        //File saveToFile = ImageUtils.getTempFile();
        if (!saveToFile.getParentFile().exists()) {
            saveToFile.getParentFile().mkdirs();
        }
        //将剪切后的图片存储到此文件
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveToFile));
        Log.i( "Test", "startImageCrop: " + "即将跳到剪切图片" );
        startActivityForResult( intent, CROP_IMAGE );
    }

    //打开相册
    private void openAlbum() {
        Intent intent_album = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );

        startActivityForResult( intent_album, CHOOSE_PHOTO );
    }

    //图片转为二进制数据
    public byte[] bitmabToBytes(Bitmap bitmap){
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos= new ByteArrayOutputStream(size);
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //将字节数组输出流转化为字节数组byte[]
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
