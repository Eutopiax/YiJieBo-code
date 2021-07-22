package com.example.rail.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.rail.MyDatabaseHelper;
import com.example.rail.R;
import com.example.rail.db.LoginUser;
import com.example.rail.ui.activity.PersonInfo;
import com.example.rail.ui.activity.Setting;
import com.example.rail.util.ImageUtils;
import com.example.rail.widget.RoundImageView;
import com.longsh.optionframelibrary.OptionBottomDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonPageFragment extends Fragment implements View.OnClickListener {
    private ImageView setting;
    private LinearLayout info;
    private LinearLayout shoucang;
    private LinearLayout mydonate;
    private LinearLayout donate;
    private LinearLayout location;
    private LinearLayout add;
    private TextView info_name,info_account;
    private RoundImageView portrait;
    private LoginUser loginUser = LoginUser.getInstance();
    private MyDatabaseHelper dbHelper;

    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private final static int CROP_IMAGE = 3;

    //imageUri照片真实路径
    private Uri imageUri;
    //照片存储
    File filePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment3, container, false);
        setting = (ImageView)view.findViewById(R.id.setting);
        info = (LinearLayout)view.findViewById(R.id.info);
        info_name = (TextView)view.findViewById(R.id.info_name);
        portrait = (RoundImageView)view.findViewById(R.id.portrait);
//        shoucang = (LinearLayout)view.findViewById(R.id.shoucang);
        mydonate = (LinearLayout)view.findViewById(R.id.mydonate);
//        donate = (LinearLayout)view.findViewById(R.id.donate);
        location = (LinearLayout)view.findViewById(R.id.location);
//        add = (LinearLayout)view.findViewById(R.id.add);
        info.setOnClickListener(this);
        setting.setOnClickListener(this);
//        shoucang.setOnClickListener(this);
        mydonate.setOnClickListener(this);
//        donate.setOnClickListener(this);
        location.setOnClickListener(this);
//        add.setOnClickListener(this);
        //登录则初始化用户的信息
        initInfo();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        //在onStart中init，修改信息后返回不会出现没有修改的情况
       // loginUser.reinit();
        initInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击设置按钮的逻辑
            case R.id.setting:
                Intent intent = new Intent(getActivity(), Setting.class);
                getActivity().startActivity(intent);
                break;
            case R.id.info:
                Intent intent1 = new Intent(getActivity(), PersonInfo.class);
                startActivity(intent1);
                break;
            case R.id.mydonate:
                Intent intent2 = new Intent(getActivity(), com.example.rail.ui.activity.mydonate.class);
                startActivity(intent2);
                break;
            case R.id.donate:
                Intent intent3 = new Intent(getActivity(), com.example.rail.ui.activity.donate.class);
                startActivity(intent3);
                break;
            case R.id.shoucang:
                Intent intent4 = new Intent(getActivity(), com.example.rail.ui.activity.guanzhu.class);
                startActivity(intent4);
                break;
            case R.id.location:
                Intent intent5 = new Intent(getActivity(), com.example.rail.ui.activity.location.class);
                startActivity(intent5);
                break;
            case R.id.add:
                show_popup_windows();
                break;
                default:
                break;
        }
    }

    //
    private void initInfo(){
        info_name.setText(loginUser.getName());
        String sql1="select * from user where id=? ";
        int id = LoginUser.login_user.getId();
        dbHelper=new MyDatabaseHelper(this.getContext());
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        Cursor cursor=sdb.rawQuery(sql1, new String[]{Integer.toString(id)});
        byte[] imgData;
        if(cursor.moveToNext()){
            //将Blob数据转化为字节数组
            imgData=cursor.getBlob(cursor.getColumnIndex("image"));
            if (imgData!=null){
                Bitmap imagebitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                //将位图显示为图片
                portrait.setImageBitmap(imagebitmap);
            }

        }
  //      portrait.setImwoageBitmap((new PhotoUtils()).byte2bitmap(loginUser.getPortrait()));
    }

    private void show_popup_windows() {
        List<String> stringList = new ArrayList<String>();
        stringList.add("拍照");
        stringList.add("从相册选择");
        final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(getContext(), stringList);
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
                        Intent intent_photo = new Intent("android.media.action.IMAGE_CAPTURE");
                        //putExtra()指定图片的输出地址，填入之前获得的Uri对象
                        imageUri = ImageUtils.getImageUri(getContext());
                        intent_photo.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        //startActivity( intent_photo );
                        startActivityForResult(intent_photo, TAKE_PHOTO);
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
    }

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
                //需要对拍摄的照片进行处理编辑
                //拍照成功的话，就调用BitmapFactory的decodeStream()方法把图片解析成Bitmap对象，然后显示
                Log.i( "Test", "onActivityResult TakePhoto : "+imageUri );
                //Bitmap bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageUri ) );
                //takephoto.setImageBitmap( bitmap );
                //设置照片存储文件及剪切图片
                File saveFile = ImageUtils.getTempFile();
                filePath = ImageUtils.getTempFile();
                startImageCrop( saveFile,imageUri );
                break;
            case CHOOSE_PHOTO:
                //选中相册照片显示
                Log.i( "Test", "onActivityResult: 执行到打开相册了" );
                try {
                    imageUri = data.getData(); //获取系统返回的照片的Uri
                    Log.i( "Test", "onActivityResult: uriImage is " +imageUri );
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(imageUri,
                            filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);  //获取照片路径
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    //photo_taken.setImageBitmap(bitmap);
                    //设置照片存储文件及剪切图片
                    File saveFile1 = ImageUtils.setTempFile(getContext());
                    filePath = ImageUtils.getTempFile();
                    startImageCrop( saveFile1,imageUri );
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CROP_IMAGE:
                Log.i( "Test", "onActivityResult: CROP_IMAGE" + "进入了CROP");
                // 通过图片URI拿到剪切图片
                //bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageUri ) );
                //通过FileName拿到图片
                Bitmap bitmap = BitmapFactory.decodeFile( filePath.toString() );
                Bitmap bitmap1 = BitmapFactory.decodeFile( filePath.toString() );
//                int id = LoginUser.login_user.getId();
//                Log.d("Test",Integer.toString(id));
//                String sql="update user set image = ? where id= ? ";
//                dbHelper=new MyDatabaseHelper(getContext());
//                SQLiteDatabase sdb=dbHelper.getWritableDatabase();
//                Object obj[]={bitmabToBytes(bitmap),id};
//                sdb.execSQL(sql,obj);
//                String sql1="select * from user where id=? ";
//                Cursor cursor=sdb.rawQuery(sql1, new String[]{Integer.toString(id)});
//                byte[] imgData=null;
//                if(cursor.moveToNext()){
//                    //将Blob数据转化为字节数组
//                    imgData=cursor.getBlob(cursor.getColumnIndex("image"));
//                    Bitmap imagebitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                    //将位图显示为图片
                    //把裁剪后的图片展示出来
//                    ri_portrati.setImageBitmap(bitmap1);
//                    ContentValues values = new ContentValues();
//                    values.put("id",id);
//                    sd b.update("user",values,"image=?",new String[]{String.valueOf(bitmabToBytes(bitmap))});
                    //ImageUtils.Compress( bitmap );
//                }
                break;
            default:
                break;
        }
    }
}

