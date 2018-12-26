package com.example.home.calculate;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
public class MainActivity extends AppCompatActivity {

        private static final String TAG = "ainActivity";

        // 第一个操作数
        private String firstNum;
        // 第二个操作数
        private String secNum;
        // 操作符
        private String opt;
        // 结果
        private String resNum;
        // 下一步是否需要计算结果
        private boolean isNeedResult;
        // 是否已经完成
        private boolean isFinish;

        // 是否已经输入小数点
        private boolean isDot;

        // ResultView
        private TextView resultView;

        // 功能区
        private boolean isFuncArea;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            clearData();
            this.resultView = (TextView) this.findViewById(R.id.resultView);
        }

        public void btnOnClick(View view){
            TextView textView = (TextView)view;

            String text = textView.getText().toString();





            if (text.equals("AC")){
                // 清空
                clearData();

                resultView.setText("0");
            }else if(text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/") || text.equals("%")){
                /**
                 * 操作符
                 */

                // 清除小数点记录
                isDot = false;
                if (isFinish){
                    // 已经计算过
                    String resNum = this.resNum;
                    clearData();
                    firstNum = resNum;
                    isNeedResult = true;

                }

                if (!isNeedResult){
                    // 第一次点击运算符
                    isNeedResult = true;
                    opt = text;
                    refreshResultView("\n" + text + " ");
                }else{
                    // 第二次点击
                    String res = getResult();
                    clearData();
                    opt = text;
                    firstNum = res;
                    isNeedResult = true;
                    resultView.setText(res + "\n" + text + " ");
                }

            }else if (text.equals("=")){
                // 等于
                if (!isFinish){
                    refreshResultView("\n= " + getResult());
                    isFinish = true;
                }
            }else if(text.equals("Change")){
                if (isFuncArea){
                    findViewById(R.id.num_area).setVisibility(View.VISIBLE);
                    findViewById(R.id.func_area).setVisibility(View.GONE);
                }else{
                    findViewById(R.id.num_area).setVisibility(View.GONE);
                    findViewById(R.id.func_area).setVisibility(View.VISIBLE);
                }

                this.isFuncArea = !isFuncArea;
            }else if(text.equals("tan")){
                refreshResultView("\n= " + Math.tan(Double.parseDouble(firstNum)));
            }else if(text.equals("cos")){
                refreshResultView("\n= " + Math.cos(Double.parseDouble(firstNum)));
            }else if(text.equals("sin")){
                refreshResultView("\n= " + Math.sin(Double.parseDouble(firstNum)));
            }else if(text.equals("log")){
                refreshResultView("\n= " + Math.log(Double.parseDouble(firstNum)));
            }else if(text.equals("exp")){
                refreshResultView("\n= " + Math.exp(Double.parseDouble(firstNum)));
            }else if(text.equals("sqrt")){
                refreshResultView("\n= " + Math.sqrt(Double.parseDouble(firstNum)));
            }else{
                /**
                 * 数字
                 */

                if (isFinish){
                    clearData();
                    resultView.setText("");
                }

                if (text.equals(".")){
                    if (isDot){
                        // 已经输入过小数点
                        return ;
                    }else{
                        isDot = true;
                    }
                }
                if (!isNeedResult){
                    // 第一个数
                    if (firstNum.length() > 10){
                        // 过长
                        return ;
                    }
                    firstNum += text;
                }else{
                    if (secNum.length() > 10){
                        return ;
                    }
                    secNum += text;
                }

                refreshResultView(text);
            }
        }

        // 清空数据
        public void clearData(){
            firstNum = "";
            secNum = "";
            opt = null;
            resNum = "";
            isNeedResult = false;
            isFinish = false;
            isDot = false;
        }

        // 刷新TextView
        public void refreshResultView(String addStr){
            String str = resultView.getText().toString();
            if (str.equals("0")){
                str = "";
            }

            str += addStr;

            resultView.setText(str);
        }

        // 计算结果
        public String getResult(){

            if (this.secNum.equals("")){
                return this.firstNum;
            }

            Log.d(TAG,this.firstNum + "::" + this.secNum);
            double firstNum = Double.parseDouble(this.firstNum);
            double secNum = Double.parseDouble(this.secNum);

            String res = "";

            Log.d(TAG,"==========:" + opt);

            switch (opt){
                case "+":
                    res += (firstNum + secNum);
                    break;
                case "-":
                    res += (firstNum - secNum);
                    break;

                case "*":
                    res += (firstNum * secNum);
                    break;

                case "/":
                    res += (secNum == 0 ? "Error" : firstNum / secNum);
                    break;

                case "%":
                    res += (firstNum % secNum);
                    break;

                default:
                    res += this.firstNum;
                    break;
            }

            this.resNum = (res.equals("Error") ? "0" : new BigDecimal(String.valueOf(res)).stripTrailingZeros().toPlainString());

            return (res.equals("Error") ? "Error" : resNum);
        }
}
