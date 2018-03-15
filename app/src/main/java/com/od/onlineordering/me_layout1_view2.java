package com.od.onlineordering;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;

public class me_layout1_view2 extends Activity {
    private EditText mPrice;
    private String price = MainActivity.message;
    private Integer num= Integer.parseInt(ListFragment.price);
    private Integer total=Integer.parseInt(MainActivity.message);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_layout1_view2);

        initBomb();
        initView();
        initEvent();
    }

    private void initEvent() {

        BmobQuery<UserBean> query = new BmobQuery<UserBean>();
        UserBean userBeanObj = new UserBean();
//        String str = userBeanObj.getUserPhone();
//        query.addWhereEqualTo("userPhone",str);

        //query.getObject(MainActivity.message.toString(), new QueryListener<UserBean>() {
        //    @Override
         //   public void done(UserBean userBean, BmobException e) {
    //    Toast.makeText(me_layout1_view2.this,"信息:"+price,Toast.LENGTH_SHORT).show();
        //if (num==0) {
            mPrice.setText(price);
//        }else {
//            total = total-num;
//            mPrice.setText(total);
//        }
      //  });
//        query.findObjects(new FindListener<UserBean>() {
//            @Override
//            public void done(List<UserBean> list, BmobException e) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(me_layout1_view2.this);
//                builder.setTitle("Query");
//                String str = "";
//                for (UserBean userBean :list){
//                    str += userBean.getUserPhone()+":"+userBean.getPassWord()+":"+userBean.getPrice()+"\n";
//                }
//                builder.setMessage(str);
//                builder.create().show();
//            }
//        });
    }

    private void initBomb() {
        Bmob.initialize(this, "2ba4db9c29a59ac93fccb52ac83fa0b5");
    }

    private void initView() {
        mPrice = (EditText) findViewById(R.id.price);
    }
}
